import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserAuthorityEntity } from './user-authority-entity.model';
import { UserAuthorityEntityService } from './user-authority-entity.service';

@Component({
    selector: 'jhi-user-authority-entity-detail',
    templateUrl: './user-authority-entity-detail.component.html'
})
export class UserAuthorityEntityDetailComponent implements OnInit, OnDestroy {

    userAuthorityEntity: UserAuthorityEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userAuthorityEntityService: UserAuthorityEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserAuthorityEntities();
    }

    load(id) {
        this.userAuthorityEntityService.find(id)
            .subscribe((userAuthorityEntityResponse: HttpResponse<UserAuthorityEntity>) => {
                this.userAuthorityEntity = userAuthorityEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserAuthorityEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userAuthorityEntityListModification',
            (response) => this.load(this.userAuthorityEntity.id)
        );
    }
}
