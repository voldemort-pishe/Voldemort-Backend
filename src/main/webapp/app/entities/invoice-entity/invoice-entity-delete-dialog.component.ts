import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InvoiceEntity } from './invoice-entity.model';
import { InvoiceEntityPopupService } from './invoice-entity-popup.service';
import { InvoiceEntityService } from './invoice-entity.service';

@Component({
    selector: 'jhi-invoice-entity-delete-dialog',
    templateUrl: './invoice-entity-delete-dialog.component.html'
})
export class InvoiceEntityDeleteDialogComponent {

    invoiceEntity: InvoiceEntity;

    constructor(
        private invoiceEntityService: InvoiceEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.invoiceEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'invoiceEntityListModification',
                content: 'Deleted an invoiceEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invoice-entity-delete-popup',
    template: ''
})
export class InvoiceEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invoiceEntityPopupService: InvoiceEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invoiceEntityPopupService
                .open(InvoiceEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
