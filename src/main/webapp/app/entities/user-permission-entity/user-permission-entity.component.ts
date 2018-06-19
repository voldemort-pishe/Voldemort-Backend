import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserPermissionEntity } from './user-permission-entity.model';
import { UserPermissionEntityService } from './user-permission-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-permission-entity',
    templateUrl: './user-permission-entity.component.html'
})
export class UserPermissionEntityComponent implements OnInit, OnDestroy {
userPermissionEntities: UserPermissionEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userPermissionEntityService: UserPermissionEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userPermissionEntityService.query().subscribe(
            (res: HttpResponse<UserPermissionEntity[]>) => {
                this.userPermissionEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserPermissionEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserPermissionEntity) {
        return item.id;
    }
    registerChangeInUserPermissionEntities() {
        this.eventSubscriber = this.eventManager.subscribe('userPermissionEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
