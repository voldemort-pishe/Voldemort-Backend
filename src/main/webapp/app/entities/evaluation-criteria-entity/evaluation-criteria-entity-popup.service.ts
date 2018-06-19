import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { EvaluationCriteriaEntityService } from './evaluation-criteria-entity.service';

@Injectable()
export class EvaluationCriteriaEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private evaluationCriteriaEntityService: EvaluationCriteriaEntityService

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
                this.evaluationCriteriaEntityService.find(id)
                    .subscribe((evaluationCriteriaEntityResponse: HttpResponse<EvaluationCriteriaEntity>) => {
                        const evaluationCriteriaEntity: EvaluationCriteriaEntity = evaluationCriteriaEntityResponse.body;
                        this.ngbModalRef = this.evaluationCriteriaEntityModalRef(component, evaluationCriteriaEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.evaluationCriteriaEntityModalRef(component, new EvaluationCriteriaEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    evaluationCriteriaEntityModalRef(component: Component, evaluationCriteriaEntity: EvaluationCriteriaEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.evaluationCriteriaEntity = evaluationCriteriaEntity;
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
