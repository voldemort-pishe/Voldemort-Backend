import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobEntity } from './job-entity.model';
import { JobEntityPopupService } from './job-entity-popup.service';
import { JobEntityService } from './job-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';
import { CompanyEntity, CompanyEntityService } from '../company-entity';

@Component({
    selector: 'jhi-job-entity-dialog',
    templateUrl: './job-entity-dialog.component.html'
})
export class JobEntityDialogComponent implements OnInit {

    jobEntity: JobEntity;
    isSaving: boolean;

    candidates: CandidateEntity[];

    companyentities: CompanyEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobEntityService: JobEntityService,
        private candidateEntityService: CandidateEntityService,
        private companyEntityService: CompanyEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidateEntityService
            .query({filter: 'job-is-null'})
            .subscribe((res: HttpResponse<CandidateEntity[]>) => {
                if (!this.jobEntity.candidate || !this.jobEntity.candidate.id) {
                    this.candidates = res.body;
                } else {
                    this.candidateEntityService
                        .find(this.jobEntity.candidate.id)
                        .subscribe((subRes: HttpResponse<CandidateEntity>) => {
                            this.candidates = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.companyEntityService.query()
            .subscribe((res: HttpResponse<CompanyEntity[]>) => { this.companyentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobEntityService.update(this.jobEntity));
        } else {
            this.subscribeToSaveResponse(
                this.jobEntityService.create(this.jobEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JobEntity>>) {
        result.subscribe((res: HttpResponse<JobEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JobEntity) {
        this.eventManager.broadcast({ name: 'jobEntityListModification', content: 'OK'});
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

    trackCompanyEntityById(index: number, item: CompanyEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-job-entity-popup',
    template: ''
})
export class JobEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobEntityPopupService: JobEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobEntityPopupService
                    .open(JobEntityDialogComponent as Component, params['id']);
            } else {
                this.jobEntityPopupService
                    .open(JobEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
