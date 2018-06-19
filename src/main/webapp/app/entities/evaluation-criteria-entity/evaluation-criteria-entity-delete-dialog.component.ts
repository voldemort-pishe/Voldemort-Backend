import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EvaluationCriteriaEntity } from './evaluation-criteria-entity.model';
import { EvaluationCriteriaEntityPopupService } from './evaluation-criteria-entity-popup.service';
import { EvaluationCriteriaEntityService } from './evaluation-criteria-entity.service';

@Component({
    selector: 'jhi-evaluation-criteria-entity-delete-dialog',
    templateUrl: './evaluation-criteria-entity-delete-dialog.component.html'
})
export class EvaluationCriteriaEntityDeleteDialogComponent {

    evaluationCriteriaEntity: EvaluationCriteriaEntity;

    constructor(
        private evaluationCriteriaEntityService: EvaluationCriteriaEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.evaluationCriteriaEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'evaluationCriteriaEntityListModification',
                content: 'Deleted an evaluationCriteriaEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-evaluation-criteria-entity-delete-popup',
    template: ''
})
export class EvaluationCriteriaEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private evaluationCriteriaEntityPopupService: EvaluationCriteriaEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.evaluationCriteriaEntityPopupService
                .open(EvaluationCriteriaEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
