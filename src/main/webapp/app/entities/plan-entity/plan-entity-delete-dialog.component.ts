import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlanEntity } from './plan-entity.model';
import { PlanEntityPopupService } from './plan-entity-popup.service';
import { PlanEntityService } from './plan-entity.service';

@Component({
    selector: 'jhi-plan-entity-delete-dialog',
    templateUrl: './plan-entity-delete-dialog.component.html'
})
export class PlanEntityDeleteDialogComponent {

    planEntity: PlanEntity;

    constructor(
        private planEntityService: PlanEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planEntityListModification',
                content: 'Deleted an planEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plan-entity-delete-popup',
    template: ''
})
export class PlanEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planEntityPopupService: PlanEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planEntityPopupService
                .open(PlanEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
