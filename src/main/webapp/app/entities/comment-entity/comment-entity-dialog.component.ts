import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CommentEntity } from './comment-entity.model';
import { CommentEntityPopupService } from './comment-entity-popup.service';
import { CommentEntityService } from './comment-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';

@Component({
    selector: 'jhi-comment-entity-dialog',
    templateUrl: './comment-entity-dialog.component.html'
})
export class CommentEntityDialogComponent implements OnInit {

    commentEntity: CommentEntity;
    isSaving: boolean;

    candidateentities: CandidateEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private commentEntityService: CommentEntityService,
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
        if (this.commentEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commentEntityService.update(this.commentEntity));
        } else {
            this.subscribeToSaveResponse(
                this.commentEntityService.create(this.commentEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CommentEntity>>) {
        result.subscribe((res: HttpResponse<CommentEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CommentEntity) {
        this.eventManager.broadcast({ name: 'commentEntityListModification', content: 'OK'});
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
    selector: 'jhi-comment-entity-popup',
    template: ''
})
export class CommentEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentEntityPopupService: CommentEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commentEntityPopupService
                    .open(CommentEntityDialogComponent as Component, params['id']);
            } else {
                this.commentEntityPopupService
                    .open(CommentEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
