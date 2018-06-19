import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { FeedbackEntity } from './feedback-entity.model';
import { FeedbackEntityService } from './feedback-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-feedback-entity',
    templateUrl: './feedback-entity.component.html'
})
export class FeedbackEntityComponent implements OnInit, OnDestroy {
feedbackEntities: FeedbackEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private feedbackEntityService: FeedbackEntityService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.feedbackEntityService.query().subscribe(
            (res: HttpResponse<FeedbackEntity[]>) => {
                this.feedbackEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFeedbackEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FeedbackEntity) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInFeedbackEntities() {
        this.eventSubscriber = this.eventManager.subscribe('feedbackEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
