import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FileEntity } from './file-entity.model';
import { FileEntityService } from './file-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-file-entity',
    templateUrl: './file-entity.component.html'
})
export class FileEntityComponent implements OnInit, OnDestroy {
fileEntities: FileEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fileEntityService: FileEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fileEntityService.query().subscribe(
            (res: HttpResponse<FileEntity[]>) => {
                this.fileEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFileEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FileEntity) {
        return item.id;
    }
    registerChangeInFileEntities() {
        this.eventSubscriber = this.eventManager.subscribe('fileEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
