import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyEntity } from './company-entity.model';
import { CompanyEntityService } from './company-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-company-entity',
    templateUrl: './company-entity.component.html'
})
export class CompanyEntityComponent implements OnInit, OnDestroy {
companyEntities: CompanyEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private companyEntityService: CompanyEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.companyEntityService.query().subscribe(
            (res: HttpResponse<CompanyEntity[]>) => {
                this.companyEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanyEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CompanyEntity) {
        return item.id;
    }
    registerChangeInCompanyEntities() {
        this.eventSubscriber = this.eventManager.subscribe('companyEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
