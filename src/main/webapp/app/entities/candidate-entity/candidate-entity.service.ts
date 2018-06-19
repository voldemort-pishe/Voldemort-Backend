import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CandidateEntity } from './candidate-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CandidateEntity>;

@Injectable()
export class CandidateEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/candidate-entities';

    constructor(private http: HttpClient) { }

    create(candidateEntity: CandidateEntity): Observable<EntityResponseType> {
        const copy = this.convert(candidateEntity);
        return this.http.post<CandidateEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidateEntity: CandidateEntity): Observable<EntityResponseType> {
        const copy = this.convert(candidateEntity);
        return this.http.put<CandidateEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CandidateEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CandidateEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CandidateEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CandidateEntity[]>): HttpResponse<CandidateEntity[]> {
        const jsonResponse: CandidateEntity[] = res.body;
        const body: CandidateEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CandidateEntity.
     */
    private convertItemFromServer(candidateEntity: CandidateEntity): CandidateEntity {
        const copy: CandidateEntity = Object.assign({}, candidateEntity);
        return copy;
    }

    /**
     * Convert a CandidateEntity to a JSON which can be sent to the server.
     */
    private convert(candidateEntity: CandidateEntity): CandidateEntity {
        const copy: CandidateEntity = Object.assign({}, candidateEntity);
        return copy;
    }
}
