import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EvaluationCriteriaEntity>;

@Injectable()
export class EvaluationCriteriaEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/evaluation-criteria-entities';

    constructor(private http: HttpClient) { }

    create(evaluationCriteriaEntity: EvaluationCriteriaEntity): Observable<EntityResponseType> {
        const copy = this.convert(evaluationCriteriaEntity);
        return this.http.post<EvaluationCriteriaEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(evaluationCriteriaEntity: EvaluationCriteriaEntity): Observable<EntityResponseType> {
        const copy = this.convert(evaluationCriteriaEntity);
        return this.http.put<EvaluationCriteriaEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EvaluationCriteriaEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EvaluationCriteriaEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<EvaluationCriteriaEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EvaluationCriteriaEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EvaluationCriteriaEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EvaluationCriteriaEntity[]>): HttpResponse<EvaluationCriteriaEntity[]> {
        const jsonResponse: EvaluationCriteriaEntity[] = res.body;
        const body: EvaluationCriteriaEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EvaluationCriteriaEntity.
     */
    private convertItemFromServer(evaluationCriteriaEntity: EvaluationCriteriaEntity): EvaluationCriteriaEntity {
        const copy: EvaluationCriteriaEntity = Object.assign({}, evaluationCriteriaEntity);
        return copy;
    }

    /**
     * Convert a EvaluationCriteriaEntity to a JSON which can be sent to the server.
     */
    private convert(evaluationCriteriaEntity: EvaluationCriteriaEntity): EvaluationCriteriaEntity {
        const copy: EvaluationCriteriaEntity = Object.assign({}, evaluationCriteriaEntity);
        return copy;
    }
}
