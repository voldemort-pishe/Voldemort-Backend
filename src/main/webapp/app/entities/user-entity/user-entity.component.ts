import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserEntity } from './user-entity.model';
import { UserEntityService } from './user-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-entity',
    templateUrl: './user-entity.component.html'
})
export class UserEntityComponent implements OnInit, OnDestroy {
userEntities: UserEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userEntityService: UserEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userEntityService.query().subscribe(
            (res: HttpResponse<UserEntity[]>) => {
                this.userEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserEntity) {
        return item.id;
    }
    registerChangeInUserEntities() {
        this.eventSubscriber = this.eventManager.subscribe('userEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
