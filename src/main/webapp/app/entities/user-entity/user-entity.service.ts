import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { UserEntity } from './user-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserEntity>;

@Injectable()
export class UserEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/user-entities';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(userEntity: UserEntity): Observable<EntityResponseType> {
        const copy = this.convert(userEntity);
        return this.http.post<UserEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userEntity: UserEntity): Observable<EntityResponseType> {
        const copy = this.convert(userEntity);
        return this.http.put<UserEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserEntity[]>): HttpResponse<UserEntity[]> {
        const jsonResponse: UserEntity[] = res.body;
        const body: UserEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserEntity.
     */
    private convertItemFromServer(userEntity: UserEntity): UserEntity {
        const copy: UserEntity = Object.assign({}, userEntity);
        copy.resetDate = this.dateUtils
            .convertDateTimeFromServer(userEntity.resetDate);
        return copy;
    }

    /**
     * Convert a UserEntity to a JSON which can be sent to the server.
     */
    private convert(userEntity: UserEntity): UserEntity {
        const copy: UserEntity = Object.assign({}, userEntity);

        copy.resetDate = this.dateUtils.toDate(userEntity.resetDate);
        return copy;
    }
}
