import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidateScheduleEntityComponent } from './candidate-schedule-entity.component';
import { CandidateScheduleEntityDetailComponent } from './candidate-schedule-entity-detail.component';
import { CandidateScheduleEntityPopupComponent } from './candidate-schedule-entity-dialog.component';
import { CandidateScheduleEntityDeletePopupComponent } from './candidate-schedule-entity-delete-dialog.component';

export const candidateScheduleEntityRoute: Routes = [
    {
        path: 'candidate-schedule-entity',
        component: CandidateScheduleEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateScheduleEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidate-schedule-entity/:id',
        component: CandidateScheduleEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateScheduleEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidateScheduleEntityPopupRoute: Routes = [
    {
        path: 'candidate-schedule-entity-new',
        component: CandidateScheduleEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateScheduleEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-schedule-entity/:id/edit',
        component: CandidateScheduleEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateScheduleEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-schedule-entity/:id/delete',
        component: CandidateScheduleEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateScheduleEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
