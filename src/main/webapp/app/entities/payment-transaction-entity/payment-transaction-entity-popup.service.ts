import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PaymentTransactionEntity } from './payment-transaction-entity.model';
import { PaymentTransactionEntityService } from './payment-transaction-entity.service';

@Injectable()
export class PaymentTransactionEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private paymentTransactionEntityService: PaymentTransactionEntityService

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
                this.paymentTransactionEntityService.find(id)
                    .subscribe((paymentTransactionEntityResponse: HttpResponse<PaymentTransactionEntity>) => {
                        const paymentTransactionEntity: PaymentTransactionEntity = paymentTransactionEntityResponse.body;
                        this.ngbModalRef = this.paymentTransactionEntityModalRef(component, paymentTransactionEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.paymentTransactionEntityModalRef(component, new PaymentTransactionEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    paymentTransactionEntityModalRef(component: Component, paymentTransactionEntity: PaymentTransactionEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paymentTransactionEntity = paymentTransactionEntity;
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
