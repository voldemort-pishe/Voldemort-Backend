import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TalentPoolEntity } from './talent-pool-entity.model';
import { TalentPoolEntityPopupService } from './talent-pool-entity-popup.service';
import { TalentPoolEntityService } from './talent-pool-entity.service';
import { UserEntity, UserEntityService } from '../user-entity';

@Component({
    selector: 'jhi-talent-pool-entity-dialog',
    templateUrl: './talent-pool-entity-dialog.component.html'
})
export class TalentPoolEntityDialogComponent implements OnInit {

    talentPoolEntity: TalentPoolEntity;
    isSaving: boolean;

    userentities: UserEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private talentPoolEntityService: TalentPoolEntityService,
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
        if (this.talentPoolEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.talentPoolEntityService.update(this.talentPoolEntity));
        } else {
            this.subscribeToSaveResponse(
                this.talentPoolEntityService.create(this.talentPoolEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TalentPoolEntity>>) {
        result.subscribe((res: HttpResponse<TalentPoolEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TalentPoolEntity) {
        this.eventManager.broadcast({ name: 'talentPoolEntityListModification', content: 'OK'});
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
    selector: 'jhi-talent-pool-entity-popup',
    template: ''
})
export class TalentPoolEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private talentPoolEntityPopupService: TalentPoolEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.talentPoolEntityPopupService
                    .open(TalentPoolEntityDialogComponent as Component, params['id']);
            } else {
                this.talentPoolEntityPopupService
                    .open(TalentPoolEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
