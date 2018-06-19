import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { AbstractAuditingEntityService } from './abstract-auditing-entity.service';

@Component({
    selector: 'jhi-abstract-auditing-entity-detail',
    templateUrl: './abstract-auditing-entity-detail.component.html'
})
export class AbstractAuditingEntityDetailComponent implements OnInit, OnDestroy {

    abstractAuditingEntity: AbstractAuditingEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private abstractAuditingEntityService: AbstractAuditingEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAbstractAuditingEntities();
    }

    load(id) {
        this.abstractAuditingEntityService.find(id)
            .subscribe((abstractAuditingEntityResponse: HttpResponse<AbstractAuditingEntity>) => {
                this.abstractAuditingEntity = abstractAuditingEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAbstractAuditingEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'abstractAuditingEntityListModification',
            (response) => this.load(this.abstractAuditingEntity.id)
        );
    }
}
