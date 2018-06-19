import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { EvaluationCriteriaEntityService } from './evaluation-criteria-entity.service';

@Component({
    selector: 'jhi-evaluation-criteria-entity-detail',
    templateUrl: './evaluation-criteria-entity-detail.component.html'
})
export class EvaluationCriteriaEntityDetailComponent implements OnInit, OnDestroy {

    evaluationCriteriaEntity: EvaluationCriteriaEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private evaluationCriteriaEntityService: EvaluationCriteriaEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEvaluationCriteriaEntities();
    }

    load(id) {
        this.evaluationCriteriaEntityService.find(id)
            .subscribe((evaluationCriteriaEntityResponse: HttpResponse<EvaluationCriteriaEntity>) => {
                this.evaluationCriteriaEntity = evaluationCriteriaEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEvaluationCriteriaEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'evaluationCriteriaEntityListModification',
            (response) => this.load(this.evaluationCriteriaEntity.id)
        );
    }
}
