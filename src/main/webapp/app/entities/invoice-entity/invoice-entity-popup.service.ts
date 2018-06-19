import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { InvoiceEntity } from './invoice-entity.model';
import { InvoiceEntityService } from './invoice-entity.service';

@Injectable()
export class InvoiceEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private invoiceEntityService: InvoiceEntityService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.invoiceEntityService.find(id)
                    .subscribe((invoiceEntityResponse: HttpResponse<InvoiceEntity>) => {
                        const invoiceEntity: InvoiceEntity = invoiceEntityResponse.body;
                        invoiceEntity.paymentDate = this.datePipe
                            .transform(invoiceEntity.paymentDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.invoiceEntityModalRef(component, invoiceEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.invoiceEntityModalRef(component, new InvoiceEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    invoiceEntityModalRef(component: Component, invoiceEntity: InvoiceEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.invoiceEntity = invoiceEntity;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
