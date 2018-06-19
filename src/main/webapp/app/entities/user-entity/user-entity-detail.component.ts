import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserEntity } from './user-entity.model';
import { UserEntityService } from './user-entity.service';

@Component({
    selector: 'jhi-user-entity-detail',
    templateUrl: './user-entity-detail.component.html'
})
export class UserEntityDetailComponent implements OnInit, OnDestroy {

    userEntity: UserEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userEntityService: UserEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserEntities();
    }

    load(id) {
        this.userEntityService.find(id)
            .subscribe((userEntityResponse: HttpResponse<UserEntity>) => {
                this.userEntity = userEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userEntityListModification',
            (response) => this.load(this.userEntity.id)
        );
    }
}
