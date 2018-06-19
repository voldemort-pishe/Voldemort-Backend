import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserAuthorityEntity } from './user-authority-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserAuthorityEntity>;

@Injectable()
export class UserAuthorityEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/user-authority-entities';

    constructor(private http: HttpClient) { }

    create(userAuthorityEntity: UserAuthorityEntity): Observable<EntityResponseType> {
        const copy = this.convert(userAuthorityEntity);
        return this.http.post<UserAuthorityEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userAuthorityEntity: UserAuthorityEntity): Observable<EntityResponseType> {
        const copy = this.convert(userAuthorityEntity);
        return this.http.put<UserAuthorityEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserAuthorityEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserAuthorityEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserAuthorityEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserAuthorityEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserAuthorityEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserAuthorityEntity[]>): HttpResponse<UserAuthorityEntity[]> {
        const jsonResponse: UserAuthorityEntity[] = res.body;
        const body: UserAuthorityEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserAuthorityEntity.
     */
    private convertItemFromServer(userAuthorityEntity: UserAuthorityEntity): UserAuthorityEntity {
        const copy: UserAuthorityEntity = Object.assign({}, userAuthorityEntity);
        return copy;
    }

    /**
     * Convert a UserAuthorityEntity to a JSON which can be sent to the server.
     */
    private convert(userAuthorityEntity: UserAuthorityEntity): UserAuthorityEntity {
        const copy: UserAuthorityEntity = Object.assign({}, userAuthorityEntity);
        return copy;
    }
}
