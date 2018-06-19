import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateScheduleEntity } from './candidate-schedule-entity.model';
import { CandidateScheduleEntityService } from './candidate-schedule-entity.service';

@Component({
    selector: 'jhi-candidate-schedule-entity-detail',
    templateUrl: './candidate-schedule-entity-detail.component.html'
})
export class CandidateScheduleEntityDetailComponent implements OnInit, OnDestroy {

    candidateScheduleEntity: CandidateScheduleEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidateScheduleEntityService: CandidateScheduleEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidateScheduleEntities();
    }

    load(id) {
        this.candidateScheduleEntityService.find(id)
            .subscribe((candidateScheduleEntityResponse: HttpResponse<CandidateScheduleEntity>) => {
                this.candidateScheduleEntity = candidateScheduleEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidateScheduleEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidateScheduleEntityListModification',
            (response) => this.load(this.candidateScheduleEntity.id)
        );
    }
}
