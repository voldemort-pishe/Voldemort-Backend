import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { FeedbackEntity } from './feedback-entity.model';
import { FeedbackEntityService } from './feedback-entity.service';

@Component({
    selector: 'jhi-feedback-entity-detail',
    templateUrl: './feedback-entity-detail.component.html'
})
export class FeedbackEntityDetailComponent implements OnInit, OnDestroy {

    feedbackEntity: FeedbackEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private feedbackEntityService: FeedbackEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFeedbackEntities();
    }

    load(id) {
        this.feedbackEntityService.find(id)
            .subscribe((feedbackEntityResponse: HttpResponse<FeedbackEntity>) => {
                this.feedbackEntity = feedbackEntityResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFeedbackEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'feedbackEntityListModification',
            (response) => this.load(this.feedbackEntity.id)
        );
    }
}
