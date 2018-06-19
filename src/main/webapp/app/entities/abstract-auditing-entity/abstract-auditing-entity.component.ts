import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AbstractAuditingEntity } from './abstract-auditing-entity.model';
import { AbstractAuditingEntityService } from './abstract-auditing-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-abstract-auditing-entity',
    templateUrl: './abstract-auditing-entity.component.html'
})
export class AbstractAuditingEntityComponent implements OnInit, OnDestroy {
abstractAuditingEntities: AbstractAuditingEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private abstractAuditingEntityService: AbstractAuditingEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.abstractAuditingEntityService.query().subscribe(
            (res: HttpResponse<AbstractAuditingEntity[]>) => {
                this.abstractAuditingEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAbstractAuditingEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AbstractAuditingEntity) {
        return item.id;
    }
    registerChangeInAbstractAuditingEntities() {
        this.eventSubscriber = this.eventManager.subscribe('abstractAuditingEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
