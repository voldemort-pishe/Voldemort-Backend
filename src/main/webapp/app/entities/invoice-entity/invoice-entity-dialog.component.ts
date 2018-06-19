import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InvoiceEntity } from './invoice-entity.model';
import { InvoiceEntityPopupService } from './invoice-entity-popup.service';
import { InvoiceEntityService } from './invoice-entity.service';
import { UserEntity, UserEntityService } from '../user-entity';

@Component({
    selector: 'jhi-invoice-entity-dialog',
    templateUrl: './invoice-entity-dialog.component.html'
})
export class InvoiceEntityDialogComponent implements OnInit {

    invoiceEntity: InvoiceEntity;
    isSaving: boolean;

    userentities: UserEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private invoiceEntityService: InvoiceEntityService,
        private userEntityService: UserEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userEntityService.query()
            .subscribe((res: HttpResponse<UserEntity[]>) => { this.userentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.invoiceEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.invoiceEntityService.update(this.invoiceEntity));
        } else {
            this.subscribeToSaveResponse(
                this.invoiceEntityService.create(this.invoiceEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InvoiceEntity>>) {
        result.subscribe((res: HttpResponse<InvoiceEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InvoiceEntity) {
        this.eventManager.broadcast({ name: 'invoiceEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserEntityById(index: number, item: UserEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-invoice-entity-popup',
    template: ''
})
export class InvoiceEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoiceEntityPopupService: InvoiceEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.invoiceEntityPopupService
                    .open(InvoiceEntityDialogComponent as Component, params['id']);
            } else {
                this.invoiceEntityPopupService
                    .open(InvoiceEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
