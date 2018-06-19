import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { CandidateScheduleEntity } from './candidate-schedule-entity.model';
import { CandidateScheduleEntityService } from './candidate-schedule-entity.service';

@Injectable()
export class CandidateScheduleEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private candidateScheduleEntityService: CandidateScheduleEntityService

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
                this.candidateScheduleEntityService.find(id)
                    .subscribe((candidateScheduleEntityResponse: HttpResponse<CandidateScheduleEntity>) => {
                        const candidateScheduleEntity: CandidateScheduleEntity = candidateScheduleEntityResponse.body;
                        candidateScheduleEntity.scheduleDate = this.datePipe
                            .transform(candidateScheduleEntity.scheduleDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.candidateScheduleEntityModalRef(component, candidateScheduleEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.candidateScheduleEntityModalRef(component, new CandidateScheduleEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    candidateScheduleEntityModalRef(component: Component, candidateScheduleEntity: CandidateScheduleEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.candidateScheduleEntity = candidateScheduleEntity;
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
