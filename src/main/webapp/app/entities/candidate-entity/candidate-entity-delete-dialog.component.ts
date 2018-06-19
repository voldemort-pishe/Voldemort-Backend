import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateEntity } from './candidate-entity.model';
import { CandidateEntityPopupService } from './candidate-entity-popup.service';
import { CandidateEntityService } from './candidate-entity.service';

@Component({
    selector: 'jhi-candidate-entity-delete-dialog',
    templateUrl: './candidate-entity-delete-dialog.component.html'
})
export class CandidateEntityDeleteDialogComponent {

    candidateEntity: CandidateEntity;

    constructor(
        private candidateEntityService: CandidateEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidateEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidateEntityListModification',
                content: 'Deleted an candidateEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidate-entity-delete-popup',
    template: ''
})
export class CandidateEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateEntityPopupService: CandidateEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidateEntityPopupService
                .open(CandidateEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
