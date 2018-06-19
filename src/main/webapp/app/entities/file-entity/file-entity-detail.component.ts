import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FileEntity } from './file-entity.model';
import { FileEntityService } from './file-entity.service';

@Component({
    selector: 'jhi-file-entity-detail',
    templateUrl: './file-entity-detail.component.html'
})
export class FileEntityDetailComponent implements OnInit, OnDestroy {

    fileEntity: FileEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fileEntityService: FileEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFileEntities();
    }

    load(id) {
        this.fileEntityService.find(id)
            .subscribe((fileEntityResponse: HttpResponse<FileEntity>) => {
                this.fileEntity = fileEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFileEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fileEntityListModification',
            (response) => this.load(this.fileEntity.id)
        );
    }
}
