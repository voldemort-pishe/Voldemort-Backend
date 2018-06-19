import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { PaymentTransactionEntityPopupService } from './payment-transaction-entity-popup.service';
import { PaymentTransactionEntityService } from './payment-transaction-entity.service';
import { InvoiceEntity, InvoiceEntityService } from '../invoice-entity';

@Component({
    selector: 'jhi-payment-transaction-entity-dialog',
    templateUrl: './payment-transaction-entity-dialog.component.html'
})
export class PaymentTransactionEntityDialogComponent implements OnInit {

    paymentTransactionEntity: PaymentTransactionEntity;
    isSaving: boolean;

    invoiceentities: InvoiceEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paymentTransactionEntityService: PaymentTransactionEntityService,
        private invoiceEntityService: InvoiceEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.invoiceEntityService.query()
            .subscribe((res: HttpResponse<InvoiceEntity[]>) => { this.invoiceentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paymentTransactionEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentTransactionEntityService.update(this.paymentTransactionEntity));
        } else {
            this.subscribeToSaveResponse(
                this.paymentTransactionEntityService.create(this.paymentTransactionEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PaymentTransactionEntity>>) {
        result.subscribe((res: HttpResponse<PaymentTransactionEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PaymentTransactionEntity) {
        this.eventManager.broadcast({ name: 'paymentTransactionEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInvoiceEntityById(index: number, item: InvoiceEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-payment-transaction-entity-popup',
    template: ''
})
export class PaymentTransactionEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentTransactionEntityPopupService: PaymentTransactionEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paymentTransactionEntityPopupService
                    .open(PaymentTransactionEntityDialogComponent as Component, params['id']);
            } else {
                this.paymentTransactionEntityPopupService
                    .open(PaymentTransactionEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
