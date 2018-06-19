import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FeedbackEntity } from './feedback-entity.model';
import { FeedbackEntityPopupService } from './feedback-entity-popup.service';
import { FeedbackEntityService } from './feedback-entity.service';

@Component({
    selector: 'jhi-feedback-entity-delete-dialog',
    templateUrl: './feedback-entity-delete-dialog.component.html'
})
export class FeedbackEntityDeleteDialogComponent {

    feedbackEntity: FeedbackEntity;

    constructor(
        private feedbackEntityService: FeedbackEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.feedbackEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'feedbackEntityListModification',
                content: 'Deleted an feedbackEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-feedback-entity-delete-popup',
    template: ''
})
export class FeedbackEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private feedbackEntityPopupService: FeedbackEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.feedbackEntityPopupService
                .open(FeedbackEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
