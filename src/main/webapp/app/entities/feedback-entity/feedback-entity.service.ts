import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FeedbackEntity } from './feedback-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FeedbackEntity>;

@Injectable()
export class FeedbackEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/feedback-entities';

    constructor(private http: HttpClient) { }

    create(feedbackEntity: FeedbackEntity): Observable<EntityResponseType> {
        const copy = this.convert(feedbackEntity);
        return this.http.post<FeedbackEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(feedbackEntity: FeedbackEntity): Observable<EntityResponseType> {
        const copy = this.convert(feedbackEntity);
        return this.http.put<FeedbackEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FeedbackEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FeedbackEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<FeedbackEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FeedbackEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FeedbackEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FeedbackEntity[]>): HttpResponse<FeedbackEntity[]> {
        const jsonResponse: FeedbackEntity[] = res.body;
        const body: FeedbackEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FeedbackEntity.
     */
    private convertItemFromServer(feedbackEntity: FeedbackEntity): FeedbackEntity {
        const copy: FeedbackEntity = Object.assign({}, feedbackEntity);
        return copy;
    }

    /**
     * Convert a FeedbackEntity to a JSON which can be sent to the server.
     */
    private convert(feedbackEntity: FeedbackEntity): FeedbackEntity {
        const copy: FeedbackEntity = Object.assign({}, feedbackEntity);
        return copy;
    }
}
