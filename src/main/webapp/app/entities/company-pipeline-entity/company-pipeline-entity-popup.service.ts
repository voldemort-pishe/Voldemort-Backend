import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CompanyPipelineEntity } from './company-pipeline-entity.model';
import { CompanyPipelineEntityService } from './company-pipeline-entity.service';

@Injectable()
export class CompanyPipelineEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyPipelineEntityService: CompanyPipelineEntityService

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
                this.companyPipelineEntityService.find(id)
                    .subscribe((companyPipelineEntityResponse: HttpResponse<CompanyPipelineEntity>) => {
                        const companyPipelineEntity: CompanyPipelineEntity = companyPipelineEntityResponse.body;
                        this.ngbModalRef = this.companyPipelineEntityModalRef(component, companyPipelineEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.companyPipelineEntityModalRef(component, new CompanyPipelineEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    companyPipelineEntityModalRef(component: Component, companyPipelineEntity: CompanyPipelineEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyPipelineEntity = companyPipelineEntity;
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
