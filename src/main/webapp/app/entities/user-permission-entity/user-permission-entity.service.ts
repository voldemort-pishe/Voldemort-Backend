import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserPermissionEntity } from './user-permission-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserPermissionEntity>;

@Injectable()
export class UserPermissionEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/user-permission-entities';

    constructor(private http: HttpClient) { }

    create(userPermissionEntity: UserPermissionEntity): Observable<EntityResponseType> {
        const copy = this.convert(userPermissionEntity);
        return this.http.post<UserPermissionEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userPermissionEntity: UserPermissionEntity): Observable<EntityResponseType> {
        const copy = this.convert(userPermissionEntity);
        return this.http.put<UserPermissionEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserPermissionEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserPermissionEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserPermissionEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserPermissionEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserPermissionEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserPermissionEntity[]>): HttpResponse<UserPermissionEntity[]> {
        const jsonResponse: UserPermissionEntity[] = res.body;
        const body: UserPermissionEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserPermissionEntity.
     */
    private convertItemFromServer(userPermissionEntity: UserPermissionEntity): UserPermissionEntity {
        const copy: UserPermissionEntity = Object.assign({}, userPermissionEntity);
        return copy;
    }

    /**
     * Convert a UserPermissionEntity to a JSON which can be sent to the server.
     */
    private convert(userPermissionEntity: UserPermissionEntity): UserPermissionEntity {
        const copy: UserPermissionEntity = Object.assign({}, userPermissionEntity);
        return copy;
    }
}
