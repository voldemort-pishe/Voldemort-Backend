import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateScheduleEntity } from './candidate-schedule-entity.model';
import { CandidateScheduleEntityPopupService } from './candidate-schedule-entity-popup.service';
import { CandidateScheduleEntityService } from './candidate-schedule-entity.service';

@Component({
    selector: 'jhi-candidate-schedule-entity-delete-dialog',
    templateUrl: './candidate-schedule-entity-delete-dialog.component.html'
})
export class CandidateScheduleEntityDeleteDialogComponent {

    candidateScheduleEntity: CandidateScheduleEntity;

    constructor(
        private candidateScheduleEntityService: CandidateScheduleEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidateScheduleEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidateScheduleEntityListModification',
                content: 'Deleted an candidateScheduleEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidate-schedule-entity-delete-popup',
    template: ''
})
export class CandidateScheduleEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateScheduleEntityPopupService: CandidateScheduleEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidateScheduleEntityPopupService
                .open(CandidateScheduleEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
