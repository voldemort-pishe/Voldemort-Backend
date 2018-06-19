import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { AbstractAuditingEntityPopupService } from './abstract-auditing-entity-popup.service';
import { AbstractAuditingEntityService } from './abstract-auditing-entity.service';

@Component({
    selector: 'jhi-abstract-auditing-entity-dialog',
    templateUrl: './abstract-auditing-entity-dialog.component.html'
})
export class AbstractAuditingEntityDialogComponent implements OnInit {

    abstractAuditingEntity: AbstractAuditingEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private abstractAuditingEntityService: AbstractAuditingEntityService,
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
        if (this.abstractAuditingEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.abstractAuditingEntityService.update(this.abstractAuditingEntity));
        } else {
            this.subscribeToSaveResponse(
                this.abstractAuditingEntityService.create(this.abstractAuditingEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AbstractAuditingEntity>>) {
        result.subscribe((res: HttpResponse<AbstractAuditingEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AbstractAuditingEntity) {
        this.eventManager.broadcast({ name: 'abstractAuditingEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-abstract-auditing-entity-popup',
    template: ''
})
export class AbstractAuditingEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private abstractAuditingEntityPopupService: AbstractAuditingEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.abstractAuditingEntityPopupService
                    .open(AbstractAuditingEntityDialogComponent as Component, params['id']);
            } else {
                this.abstractAuditingEntityPopupService
                    .open(AbstractAuditingEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
