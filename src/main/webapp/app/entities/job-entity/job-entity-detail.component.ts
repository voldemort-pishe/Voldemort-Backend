import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JobEntity } from './job-entity.model';
import { JobEntityService } from './job-entity.service';

@Component({
    selector: 'jhi-job-entity-detail',
    templateUrl: './job-entity-detail.component.html'
})
export class JobEntityDetailComponent implements OnInit, OnDestroy {

    jobEntity: JobEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobEntityService: JobEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobEntities();
    }

    load(id) {
        this.jobEntityService.find(id)
            .subscribe((jobEntityResponse: HttpResponse<JobEntity>) => {
                this.jobEntity = jobEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobEntityListModification',
            (response) => this.load(this.jobEntity.id)
        );
    }
}
