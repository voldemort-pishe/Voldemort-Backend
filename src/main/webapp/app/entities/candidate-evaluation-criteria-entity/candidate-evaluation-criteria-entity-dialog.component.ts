import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { CandidateEvaluationCriteriaEntityPopupService } from './candidate-evaluation-criteria-entity-popup.service';
import { CandidateEvaluationCriteriaEntityService } from './candidate-evaluation-criteria-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity-dialog',
    templateUrl: './candidate-evaluation-criteria-entity-dialog.component.html'
})
export class CandidateEvaluationCriteriaEntityDialogComponent implements OnInit {

    candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity;
    isSaving: boolean;

    candidateentities: CandidateEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private candidateEvaluationCriteriaEntityService: CandidateEvaluationCriteriaEntityService,
        private candidateEntityService: CandidateEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidateEntityService.query()
            .subscribe((res: HttpResponse<CandidateEntity[]>) => { this.candidateentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candidateEvaluationCriteriaEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidateEvaluationCriteriaEntityService.update(this.candidateEvaluationCriteriaEntity));
        } else {
            this.subscribeToSaveResponse(
                this.candidateEvaluationCriteriaEntityService.create(this.candidateEvaluationCriteriaEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CandidateEvaluationCriteriaEntity>>) {
        result.subscribe((res: HttpResponse<CandidateEvaluationCriteriaEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CandidateEvaluationCriteriaEntity) {
        this.eventManager.broadcast({ name: 'candidateEvaluationCriteriaEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCandidateEntityById(index: number, item: CandidateEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity-popup',
    template: ''
})
export class CandidateEvaluationCriteriaEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateEvaluationCriteriaEntityPopupService: CandidateEvaluationCriteriaEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidateEvaluationCriteriaEntityPopupService
                    .open(CandidateEvaluationCriteriaEntityDialogComponent as Component, params['id']);
            } else {
                this.candidateEvaluationCriteriaEntityPopupService
                    .open(CandidateEvaluationCriteriaEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
