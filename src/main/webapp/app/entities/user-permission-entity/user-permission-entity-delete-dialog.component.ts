import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserPermissionEntity } from './user-permission-entity.model';
import { UserPermissionEntityPopupService } from './user-permission-entity-popup.service';
import { UserPermissionEntityService } from './user-permission-entity.service';

@Component({
    selector: 'jhi-user-permission-entity-delete-dialog',
    templateUrl: './user-permission-entity-delete-dialog.component.html'
})
export class UserPermissionEntityDeleteDialogComponent {

    userPermissionEntity: UserPermissionEntity;

    constructor(
        private userPermissionEntityService: UserPermissionEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userPermissionEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userPermissionEntityListModification',
                content: 'Deleted an userPermissionEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-permission-entity-delete-popup',
    template: ''
})
export class UserPermissionEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userPermissionEntityPopupService: UserPermissionEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userPermissionEntityPopupService
                .open(UserPermissionEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
