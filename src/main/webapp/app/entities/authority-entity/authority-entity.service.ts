import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { AuthorityEntity } from './authority-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AuthorityEntity>;

@Injectable()
export class AuthorityEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/authority-entities';

    constructor(private http: HttpClient) { }

    create(authorityEntity: AuthorityEntity): Observable<EntityResponseType> {
        const copy = this.convert(authorityEntity);
        return this.http.post<AuthorityEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(authorityEntity: AuthorityEntity): Observable<EntityResponseType> {
        const copy = this.convert(authorityEntity);
        return this.http.put<AuthorityEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AuthorityEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuthorityEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<AuthorityEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuthorityEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuthorityEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AuthorityEntity[]>): HttpResponse<AuthorityEntity[]> {
        const jsonResponse: AuthorityEntity[] = res.body;
        const body: AuthorityEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AuthorityEntity.
     */
    private convertItemFromServer(authorityEntity: AuthorityEntity): AuthorityEntity {
        const copy: AuthorityEntity = Object.assign({}, authorityEntity);
        return copy;
    }

    /**
     * Convert a AuthorityEntity to a JSON which can be sent to the server.
     */
    private convert(authorityEntity: AuthorityEntity): AuthorityEntity {
        const copy: AuthorityEntity = Object.assign({}, authorityEntity);
        return copy;
    }
}
