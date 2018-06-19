import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { CommentEntity } from './comment-entity.model';
import { CommentEntityService } from './comment-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-comment-entity',
    templateUrl: './comment-entity.component.html'
})
export class CommentEntityComponent implements OnInit, OnDestroy {
commentEntities: CommentEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commentEntityService: CommentEntityService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commentEntityService.query().subscribe(
            (res: HttpResponse<CommentEntity[]>) => {
                this.commentEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommentEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CommentEntity) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInCommentEntities() {
        this.eventSubscriber = this.eventManager.subscribe('commentEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
