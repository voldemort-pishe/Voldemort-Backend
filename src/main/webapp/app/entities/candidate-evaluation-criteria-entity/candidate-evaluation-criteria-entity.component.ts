import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { CandidateEvaluationCriteriaEntityService } from './candidate-evaluation-criteria-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity',
    templateUrl: './candidate-evaluation-criteria-entity.component.html'
})
export class CandidateEvaluationCriteriaEntityComponent implements OnInit, OnDestroy {
candidateEvaluationCriteriaEntities: CandidateEvaluationCriteriaEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private candidateEvaluationCriteriaEntityService: CandidateEvaluationCriteriaEntityService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.candidateEvaluationCriteriaEntityService.query().subscribe(
            (res: HttpResponse<CandidateEvaluationCriteriaEntity[]>) => {
                this.candidateEvaluationCriteriaEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCandidateEvaluationCriteriaEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CandidateEvaluationCriteriaEntity) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInCandidateEvaluationCriteriaEntities() {
        this.eventSubscriber = this.eventManager.subscribe('candidateEvaluationCriteriaEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
