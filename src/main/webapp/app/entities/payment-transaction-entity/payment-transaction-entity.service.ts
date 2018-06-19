import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PaymentTransactionEntity>;

@Injectable()
export class PaymentTransactionEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/payment-transaction-entities';

    constructor(private http: HttpClient) { }

    create(paymentTransactionEntity: PaymentTransactionEntity): Observable<EntityResponseType> {
        const copy = this.convert(paymentTransactionEntity);
        return this.http.post<PaymentTransactionEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(paymentTransactionEntity: PaymentTransactionEntity): Observable<EntityResponseType> {
        const copy = this.convert(paymentTransactionEntity);
        return this.http.put<PaymentTransactionEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PaymentTransactionEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PaymentTransactionEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<PaymentTransactionEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PaymentTransactionEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PaymentTransactionEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PaymentTransactionEntity[]>): HttpResponse<PaymentTransactionEntity[]> {
        const jsonResponse: PaymentTransactionEntity[] = res.body;
        const body: PaymentTransactionEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PaymentTransactionEntity.
     */
    private convertItemFromServer(paymentTransactionEntity: PaymentTransactionEntity): PaymentTransactionEntity {
        const copy: PaymentTransactionEntity = Object.assign({}, paymentTransactionEntity);
        return copy;
    }

    /**
     * Convert a PaymentTransactionEntity to a JSON which can be sent to the server.
     */
    private convert(paymentTransactionEntity: PaymentTransactionEntity): PaymentTransactionEntity {
        const copy: PaymentTransactionEntity = Object.assign({}, paymentTransactionEntity);
        return copy;
    }
}
