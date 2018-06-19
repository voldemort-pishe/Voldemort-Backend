import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FileEntity } from './file-entity.model';
import { FileEntityPopupService } from './file-entity-popup.service';
import { FileEntityService } from './file-entity.service';

@Component({
    selector: 'jhi-file-entity-delete-dialog',
    templateUrl: './file-entity-delete-dialog.component.html'
})
export class FileEntityDeleteDialogComponent {

    fileEntity: FileEntity;

    constructor(
        private fileEntityService: FileEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fileEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fileEntityListModification',
                content: 'Deleted an fileEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-file-entity-delete-popup',
    template: ''
})
export class FileEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileEntityPopupService: FileEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fileEntityPopupService
                .open(FileEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
