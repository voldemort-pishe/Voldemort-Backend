import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyPipelineEntity } from './company-pipeline-entity.model';
import { CompanyPipelineEntityPopupService } from './company-pipeline-entity-popup.service';
import { CompanyPipelineEntityService } from './company-pipeline-entity.service';
import { CompanyEntity, CompanyEntityService } from '../company-entity';

@Component({
    selector: 'jhi-company-pipeline-entity-dialog',
    templateUrl: './company-pipeline-entity-dialog.component.html'
})
export class CompanyPipelineEntityDialogComponent implements OnInit {

    companyPipelineEntity: CompanyPipelineEntity;
    isSaving: boolean;

    companyentities: CompanyEntity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private companyPipelineEntityService: CompanyPipelineEntityService,
        private companyEntityService: CompanyEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyEntityService.query()
            .subscribe((res: HttpResponse<CompanyEntity[]>) => { this.companyentities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyPipelineEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyPipelineEntityService.update(this.companyPipelineEntity));
        } else {
            this.subscribeToSaveResponse(
                this.companyPipelineEntityService.create(this.companyPipelineEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CompanyPipelineEntity>>) {
        result.subscribe((res: HttpResponse<CompanyPipelineEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyPipelineEntity) {
        this.eventManager.broadcast({ name: 'companyPipelineEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCompanyEntityById(index: number, item: CompanyEntity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-company-pipeline-entity-popup',
    template: ''
})
export class CompanyPipelineEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPipelineEntityPopupService: CompanyPipelineEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyPipelineEntityPopupService
                    .open(CompanyPipelineEntityDialogComponent as Component, params['id']);
            } else {
                this.companyPipelineEntityPopupService
                    .open(CompanyPipelineEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
