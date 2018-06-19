import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TalentPoolEntity } from './talent-pool-entity.model';
import { TalentPoolEntityService } from './talent-pool-entity.service';

@Component({
    selector: 'jhi-talent-pool-entity-detail',
    templateUrl: './talent-pool-entity-detail.component.html'
})
export class TalentPoolEntityDetailComponent implements OnInit, OnDestroy {

    talentPoolEntity: TalentPoolEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private talentPoolEntityService: TalentPoolEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTalentPoolEntities();
    }

    load(id) {
        this.talentPoolEntityService.find(id)
            .subscribe((talentPoolEntityResponse: HttpResponse<TalentPoolEntity>) => {
                this.talentPoolEntity = talentPoolEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTalentPoolEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'talentPoolEntityListModification',
            (response) => this.load(this.talentPoolEntity.id)
        );
    }
}
