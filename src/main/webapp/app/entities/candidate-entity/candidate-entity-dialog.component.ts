import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateEntity } from './candidate-entity.model';
import { CandidateEntityPopupService } from './candidate-entity-popup.service';
import { CandidateEntityService } from './candidate-entity.service';
import { FileEntity, FileEntityService } from '../file-entity';
import { JobEntity, JobEntityService } from '../job-entity';

@Component({
    selector: 'jhi-candidate-entity-dialog',
    templateUrl: './candidate-entity-dialog.component.html'
})
export class CandidateEntityDialogComponent implements OnInit {

    candidateEntity: CandidateEntity;
    isSaving: boolean;

    files: FileEntity[];

    jobentities: JobEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candidateEntityService: CandidateEntityService,
        private fileEntityService: FileEntityService,
        private jobEntityService: JobEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.fileEntityService
            .query({filter: 'candidate-is-null'})
            .subscribe((res: HttpResponse<FileEntity[]>) => {
                if (!this.candidateEntity.file || !this.candidateEntity.file.id) {
                    this.files = res.body;
                } else {
                    this.fileEntityService
                        .find(this.candidateEntity.file.id)
                        .subscribe((subRes: HttpResponse<FileEntity>) => {
                            this.files = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.jobEntityService.query()
            .subscribe((res: HttpResponse<JobEntity[]>) => { this.jobentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candidateEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidateEntityService.update(this.candidateEntity));
        } else {
            this.subscribeToSaveResponse(
                this.candidateEntityService.create(this.candidateEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CandidateEntity>>) {
        result.subscribe((res: HttpResponse<CandidateEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CandidateEntity) {
        this.eventManager.broadcast({ name: 'candidateEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFileEntityById(index: number, item: FileEntity) {
        return item.id;
    }

    trackJobEntityById(index: number, item: JobEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-candidate-entity-popup',
    template: ''
})
export class CandidateEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateEntityPopupService: CandidateEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidateEntityPopupService
                    .open(CandidateEntityDialogComponent as Component, params['id']);
            } else {
                this.candidateEntityPopupService
                    .open(CandidateEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
