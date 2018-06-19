import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorityEntity } from './authority-entity.model';
import { AuthorityEntityPopupService } from './authority-entity-popup.service';
import { AuthorityEntityService } from './authority-entity.service';

@Component({
    selector: 'jhi-authority-entity-delete-dialog',
    templateUrl: './authority-entity-delete-dialog.component.html'
})
export class AuthorityEntityDeleteDialogComponent {

    authorityEntity: AuthorityEntity;

    constructor(
        private authorityEntityService: AuthorityEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.authorityEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'authorityEntityListModification',
                content: 'Deleted an authorityEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-authority-entity-delete-popup',
    template: ''
})
export class AuthorityEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private authorityEntityPopupService: AuthorityEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.authorityEntityPopupService
                .open(AuthorityEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
