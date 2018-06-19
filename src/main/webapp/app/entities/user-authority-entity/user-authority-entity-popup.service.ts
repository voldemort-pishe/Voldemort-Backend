import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { UserAuthorityEntity } from './user-authority-entity.model';
import { UserAuthorityEntityService } from './user-authority-entity.service';

@Injectable()
export class UserAuthorityEntityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userAuthorityEntityService: UserAuthorityEntityService

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
                this.userAuthorityEntityService.find(id)
                    .subscribe((userAuthorityEntityResponse: HttpResponse<UserAuthorityEntity>) => {
                        const userAuthorityEntity: UserAuthorityEntity = userAuthorityEntityResponse.body;
                        this.ngbModalRef = this.userAuthorityEntityModalRef(component, userAuthorityEntity);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userAuthorityEntityModalRef(component, new UserAuthorityEntity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userAuthorityEntityModalRef(component: Component, userAuthorityEntity: UserAuthorityEntity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userAuthorityEntity = userAuthorityEntity;
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
