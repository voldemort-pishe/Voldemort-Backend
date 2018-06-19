import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PlanEntity } from './plan-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PlanEntity>;

@Injectable()
export class PlanEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/plan-entities';

    constructor(private http: HttpClient) { }

    create(planEntity: PlanEntity): Observable<EntityResponseType> {
        const copy = this.convert(planEntity);
        return this.http.post<PlanEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(planEntity: PlanEntity): Observable<EntityResponseType> {
        const copy = this.convert(planEntity);
        return this.http.put<PlanEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PlanEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PlanEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<PlanEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PlanEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PlanEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PlanEntity[]>): HttpResponse<PlanEntity[]> {
        const jsonResponse: PlanEntity[] = res.body;
        const body: PlanEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PlanEntity.
     */
    private convertItemFromServer(planEntity: PlanEntity): PlanEntity {
        const copy: PlanEntity = Object.assign({}, planEntity);
        return copy;
    }

    /**
     * Convert a PlanEntity to a JSON which can be sent to the server.
     */
    private convert(planEntity: PlanEntity): PlanEntity {
        const copy: PlanEntity = Object.assign({}, planEntity);
        return copy;
    }
}
