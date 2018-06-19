import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserEntity } from './user-entity.model';
import { UserEntityPopupService } from './user-entity-popup.service';
import { UserEntityService } from './user-entity.service';

@Component({
    selector: 'jhi-user-entity-dialog',
    templateUrl: './user-entity-dialog.component.html'
})
export class UserEntityDialogComponent implements OnInit {

    userEntity: UserEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private userEntityService: UserEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userEntityService.update(this.userEntity));
        } else {
            this.subscribeToSaveResponse(
                this.userEntityService.create(this.userEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserEntity>>) {
        result.subscribe((res: HttpResponse<UserEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserEntity) {
        this.eventManager.broadcast({ name: 'userEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-user-entity-popup',
    template: ''
})
export class UserEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userEntityPopupService: UserEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userEntityPopupService
                    .open(UserEntityDialogComponent as Component, params['id']);
            } else {
                this.userEntityPopupService
                    .open(UserEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
