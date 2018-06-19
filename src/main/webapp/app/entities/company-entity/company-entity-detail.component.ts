import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyEntity } from './company-entity.model';
import { CompanyEntityService } from './company-entity.service';

@Component({
    selector: 'jhi-company-entity-detail',
    templateUrl: './company-entity-detail.component.html'
})
export class CompanyEntityDetailComponent implements OnInit, OnDestroy {

    companyEntity: CompanyEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyEntityService: CompanyEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyEntities();
    }

    load(id) {
        this.companyEntityService.find(id)
            .subscribe((companyEntityResponse: HttpResponse<CompanyEntity>) => {
                this.companyEntity = companyEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyEntityListModification',
            (response) => this.load(this.companyEntity.id)
        );
    }
}
