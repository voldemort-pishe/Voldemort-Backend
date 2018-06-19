import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { CandidateEvaluationCriteriaEntityService } from './candidate-evaluation-criteria-entity.service';

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity-detail',
    templateUrl: './candidate-evaluation-criteria-entity-detail.component.html'
})
export class CandidateEvaluationCriteriaEntityDetailComponent implements OnInit, OnDestroy {

    candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private candidateEvaluationCriteriaEntityService: CandidateEvaluationCriteriaEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidateEvaluationCriteriaEntities();
    }

    load(id) {
        this.candidateEvaluationCriteriaEntityService.find(id)
            .subscribe((candidateEvaluationCriteriaEntityResponse: HttpResponse<CandidateEvaluationCriteriaEntity>) => {
                this.candidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntityResponse.body;
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

    registerChangeInCandidateEvaluationCriteriaEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidateEvaluationCriteriaEntityListModification',
            (response) => this.load(this.candidateEvaluationCriteriaEntity.id)
        );
    }
}
