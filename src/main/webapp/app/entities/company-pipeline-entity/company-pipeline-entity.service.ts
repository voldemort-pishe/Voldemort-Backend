import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CompanyPipelineEntity } from './company-pipeline-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CompanyPipelineEntity>;

@Injectable()
export class CompanyPipelineEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/company-pipeline-entities';

    constructor(private http: HttpClient) { }

    create(companyPipelineEntity: CompanyPipelineEntity): Observable<EntityResponseType> {
        const copy = this.convert(companyPipelineEntity);
        return this.http.post<CompanyPipelineEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(companyPipelineEntity: CompanyPipelineEntity): Observable<EntityResponseType> {
        const copy = this.convert(companyPipelineEntity);
        return this.http.put<CompanyPipelineEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CompanyPipelineEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CompanyPipelineEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CompanyPipelineEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CompanyPipelineEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CompanyPipelineEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CompanyPipelineEntity[]>): HttpResponse<CompanyPipelineEntity[]> {
        const jsonResponse: CompanyPipelineEntity[] = res.body;
        const body: CompanyPipelineEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CompanyPipelineEntity.
     */
    private convertItemFromServer(companyPipelineEntity: CompanyPipelineEntity): CompanyPipelineEntity {
        const copy: CompanyPipelineEntity = Object.assign({}, companyPipelineEntity);
        return copy;
    }

    /**
     * Convert a CompanyPipelineEntity to a JSON which can be sent to the server.
     */
    private convert(companyPipelineEntity: CompanyPipelineEntity): CompanyPipelineEntity {
        const copy: CompanyPipelineEntity = Object.assign({}, companyPipelineEntity);
        return copy;
    }
}
