import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { AbstractAuditingEntityService } from './abstract-auditing-entity.service';

@Injectable()
export class AbstractAuditingEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private abstractAuditingEntityService: AbstractAuditingEntityService

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
                this.abstractAuditingEntityService.find(id)
                    .subscribe((abstractAuditingEntityResponse: HttpResponse<AbstractAuditingEntity>) => {
                        const abstractAuditingEntity: AbstractAuditingEntity = abstractAuditingEntityResponse.body;
                        abstractAuditingEntity.createdDate = this.datePipe
                            .transform(abstractAuditingEntity.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        abstractAuditingEntity.lastModifiedDate = this.datePipe
                            .transform(abstractAuditingEntity.lastModifiedDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.abstractAuditingEntityModalRef(component, abstractAuditingEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.abstractAuditingEntityModalRef(component, new AbstractAuditingEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    abstractAuditingEntityModalRef(component: Component, abstractAuditingEntity: AbstractAuditingEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.abstractAuditingEntity = abstractAuditingEntity;
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
