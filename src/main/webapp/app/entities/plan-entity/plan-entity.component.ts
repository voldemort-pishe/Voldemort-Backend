import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlanEntity } from './plan-entity.model';
import { PlanEntityService } from './plan-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-plan-entity',
    templateUrl: './plan-entity.component.html'
})
export class PlanEntityComponent implements OnInit, OnDestroy {
planEntities: PlanEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private planEntityService: PlanEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.planEntityService.query().subscribe(
            (res: HttpResponse<PlanEntity[]>) => {
                this.planEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlanEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlanEntity) {
        return item.id;
    }
    registerChangeInPlanEntities() {
        this.eventSubscriber = this.eventManager.subscribe('planEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
