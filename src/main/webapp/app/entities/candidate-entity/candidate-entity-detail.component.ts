import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CandidateEntity } from './candidate-entity.model';
import { CandidateEntityService } from './candidate-entity.service';

@Component({
    selector: 'jhi-candidate-entity-detail',
    templateUrl: './candidate-entity-detail.component.html'
})
export class CandidateEntityDetailComponent implements OnInit, OnDestroy {

    candidateEntity: CandidateEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidateEntityService: CandidateEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidateEntities();
    }

    load(id) {
        this.candidateEntityService.find(id)
            .subscribe((candidateEntityResponse: HttpResponse<CandidateEntity>) => {
                this.candidateEntity = candidateEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidateEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidateEntityListModification',
            (response) => this.load(this.candidateEntity.id)
        );
    }
}
