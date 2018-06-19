import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { PaymentTransactionEntityService } from './payment-transaction-entity.service';

@Component({
    selector: 'jhi-payment-transaction-entity-detail',
    templateUrl: './payment-transaction-entity-detail.component.html'
})
export class PaymentTransactionEntityDetailComponent implements OnInit, OnDestroy {

    paymentTransactionEntity: PaymentTransactionEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private paymentTransactionEntityService: PaymentTransactionEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaymentTransactionEntities();
    }

    load(id) {
        this.paymentTransactionEntityService.find(id)
            .subscribe((paymentTransactionEntityResponse: HttpResponse<PaymentTransactionEntity>) => {
                this.paymentTransactionEntity = paymentTransactionEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPaymentTransactionEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paymentTransactionEntityListModification',
            (response) => this.load(this.paymentTransactionEntity.id)
        );
    }
}
