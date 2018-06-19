import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommentEntity } from './comment-entity.model';
import { CommentEntityPopupService } from './comment-entity-popup.service';
import { CommentEntityService } from './comment-entity.service';

@Component({
    selector: 'jhi-comment-entity-delete-dialog',
    templateUrl: './comment-entity-delete-dialog.component.html'
})
export class CommentEntityDeleteDialogComponent {

    commentEntity: CommentEntity;

    constructor(
        private commentEntityService: CommentEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commentEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commentEntityListModification',
                content: 'Deleted an commentEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comment-entity-delete-popup',
    template: ''
})
export class CommentEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commentEntityPopupService: CommentEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commentEntityPopupService
                .open(CommentEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
