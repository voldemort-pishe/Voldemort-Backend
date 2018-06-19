import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobEntity } from './job-entity.model';
import { JobEntityService } from './job-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-job-entity',
    templateUrl: './job-entity.component.html'
})
export class JobEntityComponent implements OnInit, OnDestroy {
jobEntities: JobEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobEntityService: JobEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.jobEntityService.query().subscribe(
            (res: HttpResponse<JobEntity[]>) => {
                this.jobEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInJobEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: JobEntity) {
        return item.id;
    }
    registerChangeInJobEntities() {
        this.eventSubscriber = this.eventManager.subscribe('jobEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
