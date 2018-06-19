import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { InvoiceEntity } from './invoice-entity.model';
import { InvoiceEntityService } from './invoice-entity.service';

@Component({
    selector: 'jhi-invoice-entity-detail',
    templateUrl: './invoice-entity-detail.component.html'
})
export class InvoiceEntityDetailComponent implements OnInit, OnDestroy {

    invoiceEntity: InvoiceEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private invoiceEntityService: InvoiceEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInvoiceEntities();
    }

    load(id) {
        this.invoiceEntityService.find(id)
            .subscribe((invoiceEntityResponse: HttpResponse<InvoiceEntity>) => {
                this.invoiceEntity = invoiceEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInvoiceEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'invoiceEntityListModification',
            (response) => this.load(this.invoiceEntity.id)
        );
    }
}
