import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateScheduleEntity } from './candidate-schedule-entity.model';
import { CandidateScheduleEntityPopupService } from './candidate-schedule-entity-popup.service';
import { CandidateScheduleEntityService } from './candidate-schedule-entity.service';
import { CandidateEntity, CandidateEntityService } from '../candidate-entity';

@Component({
    selector: 'jhi-candidate-schedule-entity-dialog',
    templateUrl: './candidate-schedule-entity-dialog.component.html'
})
export class CandidateScheduleEntityDialogComponent implements OnInit {

    candidateScheduleEntity: CandidateScheduleEntity;
    isSaving: boolean;

    candidateentities: CandidateEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private candidateScheduleEntityService: CandidateScheduleEntityService,
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
        if (this.candidateScheduleEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidateScheduleEntityService.update(this.candidateScheduleEntity));
        } else {
            this.subscribeToSaveResponse(
                this.candidateScheduleEntityService.create(this.candidateScheduleEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CandidateScheduleEntity>>) {
        result.subscribe((res: HttpResponse<CandidateScheduleEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CandidateScheduleEntity) {
        this.eventManager.broadcast({ name: 'candidateScheduleEntityListModification', content: 'OK'});
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
    selector: 'jhi-candidate-schedule-entity-popup',
    template: ''
})
export class CandidateScheduleEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateScheduleEntityPopupService: CandidateScheduleEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidateScheduleEntityPopupService
                    .open(CandidateScheduleEntityDialogComponent as Component, params['id']);
            } else {
                this.candidateScheduleEntityPopupService
                    .open(CandidateScheduleEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
