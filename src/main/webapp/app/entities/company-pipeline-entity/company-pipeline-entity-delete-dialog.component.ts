import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyPipelineEntity } from './company-pipeline-entity.model';
import { CompanyPipelineEntityPopupService } from './company-pipeline-entity-popup.service';
import { CompanyPipelineEntityService } from './company-pipeline-entity.service';

@Component({
    selector: 'jhi-company-pipeline-entity-delete-dialog',
    templateUrl: './company-pipeline-entity-delete-dialog.component.html'
})
export class CompanyPipelineEntityDeleteDialogComponent {

    companyPipelineEntity: CompanyPipelineEntity;

    constructor(
        private companyPipelineEntityService: CompanyPipelineEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyPipelineEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyPipelineEntityListModification',
                content: 'Deleted an companyPipelineEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-pipeline-entity-delete-popup',
    template: ''
})
export class CompanyPipelineEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPipelineEntityPopupService: CompanyPipelineEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyPipelineEntityPopupService
                .open(CompanyPipelineEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
