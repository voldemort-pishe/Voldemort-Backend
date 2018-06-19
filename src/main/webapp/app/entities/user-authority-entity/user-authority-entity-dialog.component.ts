import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAuthorityEntity } from './user-authority-entity.model';
import { UserAuthorityEntityPopupService } from './user-authority-entity-popup.service';
import { UserAuthorityEntityService } from './user-authority-entity.service';
import { UserEntity, UserEntityService } from '../user-entity';

@Component({
    selector: 'jhi-user-authority-entity-dialog',
    templateUrl: './user-authority-entity-dialog.component.html'
})
export class UserAuthorityEntityDialogComponent implements OnInit {

    userAuthorityEntity: UserAuthorityEntity;
    isSaving: boolean;

    userentities: UserEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userAuthorityEntityService: UserAuthorityEntityService,
        private userEntityService: UserEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userEntityService.query()
            .subscribe((res: HttpResponse<UserEntity[]>) => { this.userentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userAuthorityEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userAuthorityEntityService.update(this.userAuthorityEntity));
        } else {
            this.subscribeToSaveResponse(
                this.userAuthorityEntityService.create(this.userAuthorityEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserAuthorityEntity>>) {
        result.subscribe((res: HttpResponse<UserAuthorityEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserAuthorityEntity) {
        this.eventManager.broadcast({ name: 'userAuthorityEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserEntityById(index: number, item: UserEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-authority-entity-popup',
    template: ''
})
export class UserAuthorityEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAuthorityEntityPopupService: UserAuthorityEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userAuthorityEntityPopupService
                    .open(UserAuthorityEntityDialogComponent as Component, params['id']);
            } else {
                this.userAuthorityEntityPopupService
                    .open(UserAuthorityEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
