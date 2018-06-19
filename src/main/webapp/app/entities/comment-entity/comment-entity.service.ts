import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CommentEntity } from './comment-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CommentEntity>;

@Injectable()
export class CommentEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/comment-entities';

    constructor(private http: HttpClient) { }

    create(commentEntity: CommentEntity): Observable<EntityResponseType> {
        const copy = this.convert(commentEntity);
        return this.http.post<CommentEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(commentEntity: CommentEntity): Observable<EntityResponseType> {
        const copy = this.convert(commentEntity);
        return this.http.put<CommentEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CommentEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CommentEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<CommentEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CommentEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CommentEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CommentEntity[]>): HttpResponse<CommentEntity[]> {
        const jsonResponse: CommentEntity[] = res.body;
        const body: CommentEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CommentEntity.
     */
    private convertItemFromServer(commentEntity: CommentEntity): CommentEntity {
        const copy: CommentEntity = Object.assign({}, commentEntity);
        return copy;
    }

    /**
     * Convert a CommentEntity to a JSON which can be sent to the server.
     */
    private convert(commentEntity: CommentEntity): CommentEntity {
        const copy: CommentEntity = Object.assign({}, commentEntity);
        return copy;
    }
}
