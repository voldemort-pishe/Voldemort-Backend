import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { EvaluationCriteriaEntityPopupService } from './evaluation-criteria-entity-popup.service';
import { EvaluationCriteriaEntityService } from './evaluation-criteria-entity.service';
import { CompanyEntity, CompanyEntityService } from '../company-entity';

@Component({
    selector: 'jhi-evaluation-criteria-entity-dialog',
    templateUrl: './evaluation-criteria-entity-dialog.component.html'
})
export class EvaluationCriteriaEntityDialogComponent implements OnInit {

    evaluationCriteriaEntity: EvaluationCriteriaEntity;
    isSaving: boolean;

    companyentities: CompanyEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private evaluationCriteriaEntityService: EvaluationCriteriaEntityService,
        private companyEntityService: CompanyEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyEntityService.query()
            .subscribe((res: HttpResponse<CompanyEntity[]>) => { this.companyentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.evaluationCriteriaEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.evaluationCriteriaEntityService.update(this.evaluationCriteriaEntity));
        } else {
            this.subscribeToSaveResponse(
                this.evaluationCriteriaEntityService.create(this.evaluationCriteriaEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EvaluationCriteriaEntity>>) {
        result.subscribe((res: HttpResponse<EvaluationCriteriaEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EvaluationCriteriaEntity) {
        this.eventManager.broadcast({ name: 'evaluationCriteriaEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyEntityById(index: number, item: CompanyEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-evaluation-criteria-entity-popup',
    template: ''
})
export class EvaluationCriteriaEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private evaluationCriteriaEntityPopupService: EvaluationCriteriaEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.evaluationCriteriaEntityPopupService
                    .open(EvaluationCriteriaEntityDialogComponent as Component, params['id']);
            } else {
                this.evaluationCriteriaEntityPopupService
                    .open(EvaluationCriteriaEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
