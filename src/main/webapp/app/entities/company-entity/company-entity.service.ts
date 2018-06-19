import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CompanyEntity } from './company-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CompanyEntity>;

@Injectable()
export class CompanyEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/company-entities';

    constructor(private http: HttpClient) { }

    create(companyEntity: CompanyEntity): Observable<EntityResponseType> {
        const copy = this.convert(companyEntity);
        return this.http.post<CompanyEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(companyEntity: CompanyEntity): Observable<EntityResponseType> {
        const copy = this.convert(companyEntity);
        return this.http.put<CompanyEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CompanyEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CompanyEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CompanyEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CompanyEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CompanyEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CompanyEntity[]>): HttpResponse<CompanyEntity[]> {
        const jsonResponse: CompanyEntity[] = res.body;
        const body: CompanyEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CompanyEntity.
     */
    private convertItemFromServer(companyEntity: CompanyEntity): CompanyEntity {
        const copy: CompanyEntity = Object.assign({}, companyEntity);
        return copy;
    }

    /**
     * Convert a CompanyEntity to a JSON which can be sent to the server.
     */
    private convert(companyEntity: CompanyEntity): CompanyEntity {
        const copy: CompanyEntity = Object.assign({}, companyEntity);
        return copy;
    }
}
