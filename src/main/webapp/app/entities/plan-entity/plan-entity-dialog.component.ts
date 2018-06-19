import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlanEntity } from './plan-entity.model';
import { PlanEntityPopupService } from './plan-entity-popup.service';
import { PlanEntityService } from './plan-entity.service';

@Component({
    selector: 'jhi-plan-entity-dialog',
    templateUrl: './plan-entity-dialog.component.html'
})
export class PlanEntityDialogComponent implements OnInit {

    planEntity: PlanEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private planEntityService: PlanEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.planEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planEntityService.update(this.planEntity));
        } else {
            this.subscribeToSaveResponse(
                this.planEntityService.create(this.planEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PlanEntity>>) {
        result.subscribe((res: HttpResponse<PlanEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PlanEntity) {
        this.eventManager.broadcast({ name: 'planEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-plan-entity-popup',
    template: ''
})
export class PlanEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planEntityPopupService: PlanEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planEntityPopupService
                    .open(PlanEntityDialogComponent as Component, params['id']);
            } else {
                this.planEntityPopupService
                    .open(PlanEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
