import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidateEntityComponent } from './candidate-entity.component';
import { CandidateEntityDetailComponent } from './candidate-entity-detail.component';
import { CandidateEntityPopupComponent } from './candidate-entity-dialog.component';
import { CandidateEntityDeletePopupComponent } from './candidate-entity-delete-dialog.component';

export const candidateEntityRoute: Routes = [
    {
        path: 'candidate-entity',
        component: CandidateEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidate-entity/:id',
        component: CandidateEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidateEntityPopupRoute: Routes = [
    {
        path: 'candidate-entity-new',
        component: CandidateEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-entity/:id/edit',
        component: CandidateEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-entity/:id/delete',
        component: CandidateEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
