import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AuthorityEntity } from './authority-entity.model';
import { AuthorityEntityService } from './authority-entity.service';

@Component({
    selector: 'jhi-authority-entity-detail',
    templateUrl: './authority-entity-detail.component.html'
})
export class AuthorityEntityDetailComponent implements OnInit, OnDestroy {

    authorityEntity: AuthorityEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private authorityEntityService: AuthorityEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAuthorityEntities();
    }

    load(id) {
        this.authorityEntityService.find(id)
            .subscribe((authorityEntityResponse: HttpResponse<AuthorityEntity>) => {
                this.authorityEntity = authorityEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuthorityEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'authorityEntityListModification',
            (response) => this.load(this.authorityEntity.id)
        );
    }
}
