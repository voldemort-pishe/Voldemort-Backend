import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAuthorityEntity } from './user-authority-entity.model';
import { UserAuthorityEntityService } from './user-authority-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-user-authority-entity',
    templateUrl: './user-authority-entity.component.html'
})
export class UserAuthorityEntityComponent implements OnInit, OnDestroy {
userAuthorityEntities: UserAuthorityEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userAuthorityEntityService: UserAuthorityEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userAuthorityEntityService.query().subscribe(
            (res: HttpResponse<UserAuthorityEntity[]>) => {
                this.userAuthorityEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserAuthorityEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserAuthorityEntity) {
        return item.id;
    }
    registerChangeInUserAuthorityEntities() {
        this.eventSubscriber = this.eventManager.subscribe('userAuthorityEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
