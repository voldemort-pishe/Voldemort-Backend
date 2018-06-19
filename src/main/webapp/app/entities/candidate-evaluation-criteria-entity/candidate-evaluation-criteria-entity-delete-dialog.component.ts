import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateEvaluationCriteriaEntity } from './candidate-evaluation-criteria-entity.model';
import { CandidateEvaluationCriteriaEntityPopupService } from './candidate-evaluation-criteria-entity-popup.service';
import { CandidateEvaluationCriteriaEntityService } from './candidate-evaluation-criteria-entity.service';

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity-delete-dialog',
    templateUrl: './candidate-evaluation-criteria-entity-delete-dialog.component.html'
})
export class CandidateEvaluationCriteriaEntityDeleteDialogComponent {

    candidateEvaluationCriteriaEntity: CandidateEvaluationCriteriaEntity;

    constructor(
        private candidateEvaluationCriteriaEntityService: CandidateEvaluationCriteriaEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.candidateEvaluationCriteriaEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'candidateEvaluationCriteriaEntityListModification',
                content: 'Deleted an candidateEvaluationCriteriaEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-candidate-evaluation-criteria-entity-delete-popup',
    template: ''
})
export class CandidateEvaluationCriteriaEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidateEvaluationCriteriaEntityPopupService: CandidateEvaluationCriteriaEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.candidateEvaluationCriteriaEntityPopupService
                .open(CandidateEvaluationCriteriaEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
