import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InvoiceEntity } from './invoice-entity.model';
import { InvoiceEntityService } from './invoice-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-invoice-entity',
    templateUrl: './invoice-entity.component.html'
})
export class InvoiceEntityComponent implements OnInit, OnDestroy {
invoiceEntities: InvoiceEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private invoiceEntityService: InvoiceEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.invoiceEntityService.query().subscribe(
            (res: HttpResponse<InvoiceEntity[]>) => {
                this.invoiceEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInvoiceEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InvoiceEntity) {
        return item.id;
    }
    registerChangeInInvoiceEntities() {
        this.eventSubscriber = this.eventManager.subscribe('invoiceEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
