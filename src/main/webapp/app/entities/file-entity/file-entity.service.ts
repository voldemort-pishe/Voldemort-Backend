import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FileEntity } from './file-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FileEntity>;

@Injectable()
export class FileEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/file-entities';

    constructor(private http: HttpClient) { }

    create(fileEntity: FileEntity): Observable<EntityResponseType> {
        const copy = this.convert(fileEntity);
        return this.http.post<FileEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(fileEntity: FileEntity): Observable<EntityResponseType> {
        const copy = this.convert(fileEntity);
        return this.http.put<FileEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FileEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FileEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<FileEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FileEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FileEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FileEntity[]>): HttpResponse<FileEntity[]> {
        const jsonResponse: FileEntity[] = res.body;
        const body: FileEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FileEntity.
     */
    private convertItemFromServer(fileEntity: FileEntity): FileEntity {
        const copy: FileEntity = Object.assign({}, fileEntity);
        return copy;
    }

    /**
     * Convert a FileEntity to a JSON which can be sent to the server.
     */
    private convert(fileEntity: FileEntity): FileEntity {
        const copy: FileEntity = Object.assign({}, fileEntity);
        return copy;
    }
}
