import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { FeedbackEntity } from './feedback-entity.model';
import { FeedbackEntityPopupService } from './feedback-entity-popup.service';
import { FeedbackEntityService } from './feedback-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';

@Component({
    selector: 'jhi-feedback-entity-dialog',
    templateUrl: './feedback-entity-dialog.component.html'
})
export class FeedbackEntityDialogComponent implements OnInit {

    feedbackEntity: FeedbackEntity;
    isSaving: boolean;

    candidateentities: CandidateEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private feedbackEntityService: FeedbackEntityService,
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
        if (this.feedbackEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.feedbackEntityService.update(this.feedbackEntity));
        } else {
            this.subscribeToSaveResponse(
                this.feedbackEntityService.create(this.feedbackEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FeedbackEntity>>) {
        result.subscribe((res: HttpResponse<FeedbackEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FeedbackEntity) {
        this.eventManager.broadcast({ name: 'feedbackEntityListModification', content: 'OK'});
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
    selector: 'jhi-feedback-entity-popup',
    template: ''
})
export class FeedbackEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private feedbackEntityPopupService: FeedbackEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.feedbackEntityPopupService
                    .open(FeedbackEntityDialogComponent as Component, params['id']);
            } else {
                this.feedbackEntityPopupService
                    .open(FeedbackEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
