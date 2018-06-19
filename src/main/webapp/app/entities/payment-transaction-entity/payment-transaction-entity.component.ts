import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { PaymentTransactionEntityService } from './payment-transaction-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-payment-transaction-entity',
    templateUrl: './payment-transaction-entity.component.html'
})
export class PaymentTransactionEntityComponent implements OnInit, OnDestroy {
paymentTransactionEntities: PaymentTransactionEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentTransactionEntityService: PaymentTransactionEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.paymentTransactionEntityService.query().subscribe(
            (res: HttpResponse<PaymentTransactionEntity[]>) => {
                this.paymentTransactionEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentTransactionEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PaymentTransactionEntity) {
        return item.id;
    }
    registerChangeInPaymentTransactionEntities() {
        this.eventSubscriber = this.eventManager.subscribe('paymentTransactionEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
