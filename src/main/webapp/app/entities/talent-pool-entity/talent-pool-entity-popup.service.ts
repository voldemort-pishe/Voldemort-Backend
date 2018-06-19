import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TalentPoolEntity } from './talent-pool-entity.model';
import { TalentPoolEntityService } from './talent-pool-entity.service';

@Injectable()
export class TalentPoolEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private talentPoolEntityService: TalentPoolEntityService

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
                this.talentPoolEntityService.find(id)
                    .subscribe((talentPoolEntityResponse: HttpResponse<TalentPoolEntity>) => {
                        const talentPoolEntity: TalentPoolEntity = talentPoolEntityResponse.body;
                        this.ngbModalRef = this.talentPoolEntityModalRef(component, talentPoolEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.talentPoolEntityModalRef(component, new TalentPoolEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    talentPoolEntityModalRef(component: Component, talentPoolEntity: TalentPoolEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.talentPoolEntity = talentPoolEntity;
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
