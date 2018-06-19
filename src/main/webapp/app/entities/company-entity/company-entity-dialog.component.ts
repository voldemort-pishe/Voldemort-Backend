import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyEntity } from './company-entity.model';
import { CompanyEntityPopupService } from './company-entity-popup.service';
import { CompanyEntityService } from './company-entity.service';
import { UserEntity, UserEntityService } from '../user-entity';

@Component({
    selector: 'jhi-company-entity-dialog',
    templateUrl: './company-entity-dialog.component.html'
})
export class CompanyEntityDialogComponent implements OnInit {

    companyEntity: CompanyEntity;
    isSaving: boolean;

    userentities: UserEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private companyEntityService: CompanyEntityService,
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
        if (this.companyEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyEntityService.update(this.companyEntity));
        } else {
            this.subscribeToSaveResponse(
                this.companyEntityService.create(this.companyEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CompanyEntity>>) {
        result.subscribe((res: HttpResponse<CompanyEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyEntity) {
        this.eventManager.broadcast({ name: 'companyEntityListModification', content: 'OK'});
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
    selector: 'jhi-company-entity-popup',
    template: ''
})
export class CompanyEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyEntityPopupService: CompanyEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyEntityPopupService
                    .open(CompanyEntityDialogComponent as Component, params['id']);
            } else {
                this.companyEntityPopupService
                    .open(CompanyEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
