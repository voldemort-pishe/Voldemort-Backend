import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TalentPoolEntity } from './talent-pool-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TalentPoolEntity>;

@Injectable()
export class TalentPoolEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/talent-pool-entities';

    constructor(private http: HttpClient) { }

    create(talentPoolEntity: TalentPoolEntity): Observable<EntityResponseType> {
        const copy = this.convert(talentPoolEntity);
        return this.http.post<TalentPoolEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(talentPoolEntity: TalentPoolEntity): Observable<EntityResponseType> {
        const copy = this.convert(talentPoolEntity);
        return this.http.put<TalentPoolEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TalentPoolEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TalentPoolEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<TalentPoolEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TalentPoolEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TalentPoolEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TalentPoolEntity[]>): HttpResponse<TalentPoolEntity[]> {
        const jsonResponse: TalentPoolEntity[] = res.body;
        const body: TalentPoolEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TalentPoolEntity.
     */
    private convertItemFromServer(talentPoolEntity: TalentPoolEntity): TalentPoolEntity {
        const copy: TalentPoolEntity = Object.assign({}, talentPoolEntity);
        return copy;
    }

    /**
     * Convert a TalentPoolEntity to a JSON which can be sent to the server.
     */
    private convert(talentPoolEntity: TalentPoolEntity): TalentPoolEntity {
        const copy: TalentPoolEntity = Object.assign({}, talentPoolEntity);
        return copy;
    }
}
