import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { PaymentTransactionEntityPopupService } from './payment-transaction-entity-popup.service';
import { PaymentTransactionEntityService } from './payment-transaction-entity.service';

@Component({
    selector: 'jhi-payment-transaction-entity-delete-dialog',
    templateUrl: './payment-transaction-entity-delete-dialog.component.html'
})
export class PaymentTransactionEntityDeleteDialogComponent {

    paymentTransactionEntity: PaymentTransactionEntity;

    constructor(
        private paymentTransactionEntityService: PaymentTransactionEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentTransactionEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paymentTransactionEntityListModification',
                content: 'Deleted an paymentTransactionEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payment-transaction-entity-delete-popup',
    template: ''
})
export class PaymentTransactionEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentTransactionEntityPopupService: PaymentTransactionEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paymentTransactionEntityPopupService
                .open(PaymentTransactionEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
