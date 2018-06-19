import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TalentPoolEntity } from './talent-pool-entity.model';
import { TalentPoolEntityPopupService } from './talent-pool-entity-popup.service';
import { TalentPoolEntityService } from './talent-pool-entity.service';

@Component({
    selector: 'jhi-talent-pool-entity-delete-dialog',
    templateUrl: './talent-pool-entity-delete-dialog.component.html'
})
export class TalentPoolEntityDeleteDialogComponent {

    talentPoolEntity: TalentPoolEntity;

    constructor(
        private talentPoolEntityService: TalentPoolEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.talentPoolEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'talentPoolEntityListModification',
                content: 'Deleted an talentPoolEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-talent-pool-entity-delete-popup',
    template: ''
})
export class TalentPoolEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private talentPoolEntityPopupService: TalentPoolEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.talentPoolEntityPopupService
                .open(TalentPoolEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
