import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyPipelineEntity } from './company-pipeline-entity.model';
import { CompanyPipelineEntityService } from './company-pipeline-entity.service';

@Component({
    selector: 'jhi-company-pipeline-entity-detail',
    templateUrl: './company-pipeline-entity-detail.component.html'
})
export class CompanyPipelineEntityDetailComponent implements OnInit, OnDestroy {

    companyPipelineEntity: CompanyPipelineEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyPipelineEntityService: CompanyPipelineEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyPipelineEntities();
    }

    load(id) {
        this.companyPipelineEntityService.find(id)
            .subscribe((companyPipelineEntityResponse: HttpResponse<CompanyPipelineEntity>) => {
                this.companyPipelineEntity = companyPipelineEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyPipelineEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyPipelineEntityListModification',
            (response) => this.load(this.companyPipelineEntity.id)
        );
    }
}
