import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FeedbackEntity } from './feedback-entity.model';
import { FeedbackEntityService } from './feedback-entity.service';

@Injectable()
export class FeedbackEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private feedbackEntityService: FeedbackEntityService

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
                this.feedbackEntityService.find(id)
                    .subscribe((feedbackEntityResponse: HttpResponse<FeedbackEntity>) => {
                        const feedbackEntity: FeedbackEntity = feedbackEntityResponse.body;
                        this.ngbModalRef = this.feedbackEntityModalRef(component, feedbackEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.feedbackEntityModalRef(component, new FeedbackEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    feedbackEntityModalRef(component: Component, feedbackEntity: FeedbackEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.feedbackEntity = feedbackEntity;
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
