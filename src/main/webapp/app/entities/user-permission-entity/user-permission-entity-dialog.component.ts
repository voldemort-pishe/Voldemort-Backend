import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserPermissionEntity } from './user-permission-entity.model';
import { UserPermissionEntityPopupService } from './user-permission-entity-popup.service';
import { UserPermissionEntityService } from './user-permission-entity.service';
import { UserAuthorityEntity, UserAuthorityEntityService } from '../user-authority-entity';

@Component({
    selector: 'jhi-user-permission-entity-dialog',
    templateUrl: './user-permission-entity-dialog.component.html'
})
export class UserPermissionEntityDialogComponent implements OnInit {

    userPermissionEntity: UserPermissionEntity;
    isSaving: boolean;

    userauthorityentities: UserAuthorityEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userPermissionEntityService: UserPermissionEntityService,
        private userAuthorityEntityService: UserAuthorityEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userAuthorityEntityService.query()
            .subscribe((res: HttpResponse<UserAuthorityEntity[]>) => { this.userauthorityentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userPermissionEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userPermissionEntityService.update(this.userPermissionEntity));
        } else {
            this.subscribeToSaveResponse(
                this.userPermissionEntityService.create(this.userPermissionEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserPermissionEntity>>) {
        result.subscribe((res: HttpResponse<UserPermissionEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserPermissionEntity) {
        this.eventManager.broadcast({ name: 'userPermissionEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserAuthorityEntityById(index: number, item: UserAuthorityEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-permission-entity-popup',
    template: ''
})
export class UserPermissionEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPermissionEntityPopupService: UserPermissionEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userPermissionEntityPopupService
                    .open(UserPermissionEntityDialogComponent as Component, params['id']);
            } else {
                this.userPermissionEntityPopupService
                    .open(UserPermissionEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
