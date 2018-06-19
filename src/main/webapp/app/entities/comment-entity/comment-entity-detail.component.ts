import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { CommentEntity } from './comment-entity.model';
import { CommentEntityService } from './comment-entity.service';

@Component({
    selector: 'jhi-comment-entity-detail',
    templateUrl: './comment-entity-detail.component.html'
})
export class CommentEntityDetailComponent implements OnInit, OnDestroy {

    commentEntity: CommentEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private commentEntityService: CommentEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommentEntities();
    }

    load(id) {
        this.commentEntityService.find(id)
            .subscribe((commentEntityResponse: HttpResponse<CommentEntity>) => {
                this.commentEntity = commentEntityResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommentEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commentEntityListModification',
            (response) => this.load(this.commentEntity.id)
        );
    }
}
