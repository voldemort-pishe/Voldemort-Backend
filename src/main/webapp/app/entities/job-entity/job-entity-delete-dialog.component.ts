import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobEntity } from './job-entity.model';
import { JobEntityPopupService } from './job-entity-popup.service';
import { JobEntityService } from './job-entity.service';

@Component({
    selector: 'jhi-job-entity-delete-dialog',
    templateUrl: './job-entity-delete-dialog.component.html'
})
export class JobEntityDeleteDialogComponent {

    jobEntity: JobEntity;

    constructor(
        private jobEntityService: JobEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobEntityListModification',
                content: 'Deleted an jobEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-entity-delete-popup',
    template: ''
})
export class JobEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobEntityPopupService: JobEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobEntityPopupService
                .open(JobEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
