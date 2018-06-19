import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CandidateEntity } from './candidate-entity.model';
import { CandidateEntityService } from './candidate-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-candidate-entity',
    templateUrl: './candidate-entity.component.html'
})
export class CandidateEntityComponent implements OnInit, OnDestroy {
candidateEntities: CandidateEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private candidateEntityService: CandidateEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.candidateEntityService.query().subscribe(
            (res: HttpResponse<CandidateEntity[]>) => {
                this.candidateEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCandidateEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CandidateEntity) {
        return item.id;
    }
    registerChangeInCandidateEntities() {
        this.eventSubscriber = this.eventManager.subscribe('candidateEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
