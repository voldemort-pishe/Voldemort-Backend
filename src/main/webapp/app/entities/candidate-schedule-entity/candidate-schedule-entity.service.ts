import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CandidateScheduleEntity } from './candidate-schedule-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CandidateScheduleEntity>;

@Injectable()
export class CandidateScheduleEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/candidate-schedule-entities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(candidateScheduleEntity: CandidateScheduleEntity): Observable<EntityResponseType> {
        const copy = this.convert(candidateScheduleEntity);
        return this.http.post<CandidateScheduleEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(candidateScheduleEntity: CandidateScheduleEntity): Observable<EntityResponseType> {
        const copy = this.convert(candidateScheduleEntity);
        return this.http.put<CandidateScheduleEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CandidateScheduleEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CandidateScheduleEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CandidateScheduleEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CandidateScheduleEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CandidateScheduleEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CandidateScheduleEntity[]>): HttpResponse<CandidateScheduleEntity[]> {
        const jsonResponse: CandidateScheduleEntity[] = res.body;
        const body: CandidateScheduleEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CandidateScheduleEntity.
     */
    private convertItemFromServer(candidateScheduleEntity: CandidateScheduleEntity): CandidateScheduleEntity {
        const copy: CandidateScheduleEntity = Object.assign({}, candidateScheduleEntity);
        copy.scheduleDate = this.dateUtils
            .convertDateTimeFromServer(candidateScheduleEntity.scheduleDate);
        return copy;
    }

    /**
     * Convert a CandidateScheduleEntity to a JSON which can be sent to the server.
     */
    private convert(candidateScheduleEntity: CandidateScheduleEntity): CandidateScheduleEntity {
        const copy: CandidateScheduleEntity = Object.assign({}, candidateScheduleEntity);

        copy.scheduleDate = this.dateUtils.toDate(candidateScheduleEntity.scheduleDate);
        return copy;
    }
}
