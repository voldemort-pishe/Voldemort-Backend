import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserAuthorityEntity } from './user-authority-entity.model';
import { UserAuthorityEntityPopupService } from './user-authority-entity-popup.service';
import { UserAuthorityEntityService } from './user-authority-entity.service';

@Component({
    selector: 'jhi-user-authority-entity-delete-dialog',
    templateUrl: './user-authority-entity-delete-dialog.component.html'
})
export class UserAuthorityEntityDeleteDialogComponent {

    userAuthorityEntity: UserAuthorityEntity;

    constructor(
        private userAuthorityEntityService: UserAuthorityEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAuthorityEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userAuthorityEntityListModification',
                content: 'Deleted an userAuthorityEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-authority-entity-delete-popup',
    template: ''
})
export class UserAuthorityEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAuthorityEntityPopupService: UserAuthorityEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userAuthorityEntityPopupService
                .open(UserAuthorityEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
