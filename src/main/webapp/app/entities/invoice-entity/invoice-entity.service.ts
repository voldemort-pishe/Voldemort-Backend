import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { InvoiceEntity } from './invoice-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<InvoiceEntity>;

@Injectable()
export class InvoiceEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/invoice-entities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(invoiceEntity: InvoiceEntity): Observable<EntityResponseType> {
        const copy = this.convert(invoiceEntity);
        return this.http.post<InvoiceEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(invoiceEntity: InvoiceEntity): Observable<EntityResponseType> {
        const copy = this.convert(invoiceEntity);
        return this.http.put<InvoiceEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InvoiceEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InvoiceEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<InvoiceEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InvoiceEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InvoiceEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InvoiceEntity[]>): HttpResponse<InvoiceEntity[]> {
        const jsonResponse: InvoiceEntity[] = res.body;
        const body: InvoiceEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InvoiceEntity.
     */
    private convertItemFromServer(invoiceEntity: InvoiceEntity): InvoiceEntity {
        const copy: InvoiceEntity = Object.assign({}, invoiceEntity);
        copy.paymentDate = this.dateUtils
            .convertDateTimeFromServer(invoiceEntity.paymentDate);
        return copy;
    }

    /**
     * Convert a InvoiceEntity to a JSON which can be sent to the server.
     */
    private convert(invoiceEntity: InvoiceEntity): InvoiceEntity {
        const copy: InvoiceEntity = Object.assign({}, invoiceEntity);

        copy.paymentDate = this.dateUtils.toDate(invoiceEntity.paymentDate);
        return copy;
    }
}
