import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TalentPoolEntity } from './talent-pool-entity.model';
import { TalentPoolEntityService } from './talent-pool-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-talent-pool-entity',
    templateUrl: './talent-pool-entity.component.html'
})
export class TalentPoolEntityComponent implements OnInit, OnDestroy {
talentPoolEntities: TalentPoolEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private talentPoolEntityService: TalentPoolEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.talentPoolEntityService.query().subscribe(
            (res: HttpResponse<TalentPoolEntity[]>) => {
                this.talentPoolEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTalentPoolEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TalentPoolEntity) {
        return item.id;
    }
    registerChangeInTalentPoolEntities() {
        this.eventSubscriber = this.eventManager.subscribe('talentPoolEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
