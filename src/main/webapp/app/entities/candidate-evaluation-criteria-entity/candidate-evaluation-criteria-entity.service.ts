import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CandidateEvaluationCriteriaEntity>;

@Injectable()
export class CandidateEvaluationCriteriaEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/candidate-evaluation-criteria-entities';

    constructor(private http: HttpClient) { }

    create(candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity):
        Observable<EntityResponseType> {
        const copy = this.convert(candidateEvaluationCriteriaEntity);
        return this.http.post<CandidateEvaluationCriteriaEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity):
        Observable<EntityResponseType> {
        const copy = this.convert(candidateEvaluationCriteriaEntity);
        return this.http.put<CandidateEvaluationCriteriaEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CandidateEvaluationCriteriaEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CandidateEvaluationCriteriaEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateEvaluationCriteriaEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateEvaluationCriteriaEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CandidateEvaluationCriteriaEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CandidateEvaluationCriteriaEntity[]>): HttpResponse<CandidateEvaluationCriteriaEntity[]> {
        const jsonResponse: CandidateEvaluationCriteriaEntity[] = res.body;
        const body: CandidateEvaluationCriteriaEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CandidateEvaluationCriteriaEntity.
     */
    private convertItemFromServer(candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity): CandidateEvaluationCriteriaEntity {
        const copy: CandidateEvaluationCriteriaEntity = Object.assign({}, candidateEvaluationCriteriaEntity);
        return copy;
    }

    /**
     * Convert a CandidateEvaluationCriteriaEntity to a JSON which can be sent to the server.
     */
    private convert(candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity): CandidateEvaluationCriteriaEntity {
        const copy: CandidateEvaluationCriteriaEntity = Object.assign({}, candidateEvaluationCriteriaEntity);
        return copy;
    }
}
