import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { EvaluationCriteriaEntityService } from './evaluation-criteria-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-evaluation-criteria-entity',
    templateUrl: './evaluation-criteria-entity.component.html'
})
export class EvaluationCriteriaEntityComponent implements OnInit, OnDestroy {
evaluationCriteriaEntities: EvaluationCriteriaEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private evaluationCriteriaEntityService: EvaluationCriteriaEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.evaluationCriteriaEntityService.query().subscribe(
            (res: HttpResponse<EvaluationCriteriaEntity[]>) => {
                this.evaluationCriteriaEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEvaluationCriteriaEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EvaluationCriteriaEntity) {
        return item.id;
    }
    registerChangeInEvaluationCriteriaEntities() {
        this.eventSubscriber = this.eventManager.subscribe('evaluationCriteriaEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
