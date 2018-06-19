import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyEntity } from './company-entity.model';
import { CompanyEntityPopupService } from './company-entity-popup.service';
import { CompanyEntityService } from './company-entity.service';

@Component({
    selector: 'jhi-company-entity-delete-dialog',
    templateUrl: './company-entity-delete-dialog.component.html'
})
export class CompanyEntityDeleteDialogComponent {

    companyEntity: CompanyEntity;

    constructor(
        private companyEntityService: CompanyEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyEntityListModification',
                content: 'Deleted an companyEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-entity-delete-popup',
    template: ''
})
export class CompanyEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyEntityPopupService: CompanyEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyEntityPopupService
                .open(CompanyEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
