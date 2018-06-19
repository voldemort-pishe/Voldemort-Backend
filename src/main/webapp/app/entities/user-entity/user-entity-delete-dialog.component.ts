import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserEntity } from './user-entity.model';
import { UserEntityPopupService } from './user-entity-popup.service';
import { UserEntityService } from './user-entity.service';

@Component({
    selector: 'jhi-user-entity-delete-dialog',
    templateUrl: './user-entity-delete-dialog.component.html'
})
export class UserEntityDeleteDialogComponent {

    userEntity: UserEntity;

    constructor(
        private userEntityService: UserEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userEntityListModification',
                content: 'Deleted an userEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-entity-delete-popup',
    template: ''
})
export class UserEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userEntityPopupService: UserEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userEntityPopupService
                .open(UserEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
