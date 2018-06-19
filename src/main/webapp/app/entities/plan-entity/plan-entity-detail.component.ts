import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PlanEntity } from './plan-entity.model';
import { PlanEntityService } from './plan-entity.service';

@Component({
    selector: 'jhi-plan-entity-detail',
    templateUrl: './plan-entity-detail.component.html'
})
export class PlanEntityDetailComponent implements OnInit, OnDestroy {

    planEntity: PlanEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private planEntityService: PlanEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlanEntities();
    }

    load(id) {
        this.planEntityService.find(id)
            .subscribe((planEntityResponse: HttpResponse<PlanEntity>) => {
                this.planEntity = planEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlanEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'planEntityListModification',
            (response) => this.load(this.planEntity.id)
        );
    }
}
