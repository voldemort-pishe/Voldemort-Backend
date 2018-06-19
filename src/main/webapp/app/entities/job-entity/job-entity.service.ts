import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JobEntity } from './job-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JobEntity>;

@Injectable()
export class JobEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/job-entities';

    constructor(private http: HttpClient) { }

    create(jobEntity: JobEntity): Observable<EntityResponseType> {
        const copy = this.convert(jobEntity);
        return this.http.post<JobEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(jobEntity: JobEntity): Observable<EntityResponseType> {
        const copy = this.convert(jobEntity);
        return this.http.put<JobEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JobEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JobEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<JobEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JobEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JobEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JobEntity[]>): HttpResponse<JobEntity[]> {
        const jsonResponse: JobEntity[] = res.body;
        const body: JobEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JobEntity.
     */
    private convertItemFromServer(jobEntity: JobEntity): JobEntity {
        const copy: JobEntity = Object.assign({}, jobEntity);
        return copy;
    }

    /**
     * Convert a JobEntity to a JSON which can be sent to the server.
     */
    private convert(jobEntity: JobEntity): JobEntity {
        const copy: JobEntity = Object.assign({}, jobEntity);
        return copy;
    }
}
