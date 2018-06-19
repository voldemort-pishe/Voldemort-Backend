import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { CandidateEvaluationCriteriaEntityService } from './candidate-evaluation-criteria-entity.service';

@Injectable()
export class CandidateEvaluationCriteriaEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private candidateEvaluationCriteriaEntityService: CandidateEvaluationCriteriaEntityService

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
                this.candidateEvaluationCriteriaEntityService.find(id)
                    .subscribe((candidateEvaluationCriteriaEntityResponse: HttpResponse<CandidateEvaluationCriteriaEntity>) => {
                        const candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntityResponse.body;
                        this.ngbModalRef = this.candidateEvaluationCriteriaEntityModalRef(component, candidateEvaluationCriteriaEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.candidateEvaluationCriteriaEntityModalRef(component, new CandidateEvaluationCriteriaEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    candidateEvaluationCriteriaEntityModalRef(component: Component,
        candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.candidateEvaluationCriteriaEntity = candidateEvaluationCriteriaEntity;
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
