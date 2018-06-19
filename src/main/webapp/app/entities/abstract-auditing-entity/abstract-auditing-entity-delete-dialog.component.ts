import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { AbstractAuditingEntityPopupService } from './abstract-auditing-entity-popup.service';
import { AbstractAuditingEntityService } from './abstract-auditing-entity.service';

@Component({
    selector: 'jhi-abstract-auditing-entity-delete-dialog',
    templateUrl: './abstract-auditing-entity-delete-dialog.component.html'
})
export class AbstractAuditingEntityDeleteDialogComponent {

    abstractAuditingEntity: AbstractAuditingEntity;

    constructor(
        private abstractAuditingEntityService: AbstractAuditingEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.abstractAuditingEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'abstractAuditingEntityListModification',
                content: 'Deleted an abstractAuditingEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-abstract-auditing-entity-delete-popup',
    template: ''
})
export class AbstractAuditingEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private abstractAuditingEntityPopupService: AbstractAuditingEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.abstractAuditingEntityPopupService
                .open(AbstractAuditingEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
