import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FileEntity } from './file-entity.model';
import { FileEntityPopupService } from './file-entity-popup.service';
import { FileEntityService } from './file-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';

@Component({
    selector: 'jhi-file-entity-dialog',
    templateUrl: './file-entity-dialog.component.html'
})
export class FileEntityDialogComponent implements OnInit {

    fileEntity: FileEntity;
    isSaving: boolean;

    candidateentities: CandidateEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fileEntityService: FileEntityService,
        private candidateEntityService: CandidateEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidateEntityService.query()
            .subscribe((res: HttpResponse<CandidateEntity[]>) => { this.candidateentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fileEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fileEntityService.update(this.fileEntity));
        } else {
            this.subscribeToSaveResponse(
                this.fileEntityService.create(this.fileEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FileEntity>>) {
        result.subscribe((res: HttpResponse<FileEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FileEntity) {
        this.eventManager.broadcast({ name: 'fileEntityListModification', content: 'OK'});
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
    selector: 'jhi-file-entity-popup',
    template: ''
})
export class FileEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileEntityPopupService: FileEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fileEntityPopupService
                    .open(FileEntityDialogComponent as Component, params['id']);
            } else {
                this.fileEntityPopupService
                    .open(FileEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
