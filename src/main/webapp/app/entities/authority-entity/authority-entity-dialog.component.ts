import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorityEntity } from './authority-entity.model';
import { AuthorityEntityPopupService } from './authority-entity-popup.service';
import { AuthorityEntityService } from './authority-entity.service';

@Component({
    selector: 'jhi-authority-entity-dialog',
    templateUrl: './authority-entity-dialog.component.html'
})
export class AuthorityEntityDialogComponent implements OnInit {

    authorityEntity: AuthorityEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private authorityEntityService: AuthorityEntityService,
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
        if (this.authorityEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.authorityEntityService.update(this.authorityEntity));
        } else {
            this.subscribeToSaveResponse(
                this.authorityEntityService.create(this.authorityEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuthorityEntity>>) {
        result.subscribe((res: HttpResponse<AuthorityEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AuthorityEntity) {
        this.eventManager.broadcast({ name: 'authorityEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-authority-entity-popup',
    template: ''
})
export class AuthorityEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorityEntityPopupService: AuthorityEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.authorityEntityPopupService
                    .open(AuthorityEntityDialogComponent as Component, params['id']);
            } else {
                this.authorityEntityPopupService
                    .open(AuthorityEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
