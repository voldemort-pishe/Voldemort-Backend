import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserPermissionEntity } from './user-permission-entity.model';
import { UserPermissionEntityService } from './user-permission-entity.service';

@Component({
    selector: 'jhi-user-permission-entity-detail',
    templateUrl: './user-permission-entity-detail.component.html'
})
export class UserPermissionEntityDetailComponent implements OnInit, OnDestroy {

    userPermissionEntity: UserPermissionEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userPermissionEntityService: UserPermissionEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserPermissionEntities();
    }

    load(id) {
        this.userPermissionEntityService.find(id)
            .subscribe((userPermissionEntityResponse: HttpResponse<UserPermissionEntity>) => {
                this.userPermissionEntity = userPermissionEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserPermissionEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userPermissionEntityListModification',
            (response) => this.load(this.userPermissionEntity.id)
        );
    }
}
