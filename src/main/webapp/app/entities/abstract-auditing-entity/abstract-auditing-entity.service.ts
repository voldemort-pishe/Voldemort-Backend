import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AbstractAuditingEntity>;

@Injectable()
export class AbstractAuditingEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/abstract-auditing-entities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(abstractAuditingEntity: AbstractAuditingEntity): Observable<EntityResponseType> {
        const copy = this.convert(abstractAuditingEntity);
        return this.http.post<AbstractAuditingEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(abstractAuditingEntity: AbstractAuditingEntity): Observable<EntityResponseType> {
        const copy = this.convert(abstractAuditingEntity);
        return this.http.put<AbstractAuditingEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AbstractAuditingEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AbstractAuditingEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<AbstractAuditingEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AbstractAuditingEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AbstractAuditingEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AbstractAuditingEntity[]>): HttpResponse<AbstractAuditingEntity[]> {
        const jsonResponse: AbstractAuditingEntity[] = res.body;
        const body: AbstractAuditingEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AbstractAuditingEntity.
     */
    private convertItemFromServer(abstractAuditingEntity: AbstractAuditingEntity): AbstractAuditingEntity {
        const copy: AbstractAuditingEntity = Object.assign({}, abstractAuditingEntity);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(abstractAuditingEntity.createdDate);
        copy.lastModifiedDate = this.dateUtils
            .convertDateTimeFromServer(abstractAuditingEntity.lastModifiedDate);
        return copy;
    }

    /**
     * Convert a AbstractAuditingEntity to a JSON which can be sent to the server.
     */
    private convert(abstractAuditingEntity: AbstractAuditingEntity): AbstractAuditingEntity {
        const copy: AbstractAuditingEntity = Object.assign({}, abstractAuditingEntity);

        copy.createdDate = this.dateUtils.toDate(abstractAuditingEntity.createdDate);

        copy.lastModifiedDate = this.dateUtils.toDate(abstractAuditingEntity.lastModifiedDate);
        return copy;
    }
}
