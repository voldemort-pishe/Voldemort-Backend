import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EvaluationCriteriaEntityComponent } from './evaluation-criteria-entity.component';
import { EvaluationCriteriaEntityDetailComponent } from './evaluation-criteria-entity-detail.component';
import { EvaluationCriteriaEntityPopupComponent } from './evaluation-criteria-entity-dialog.component';
import { EvaluationCriteriaEntityDeletePopupComponent } from './evaluation-criteria-entity-delete-dialog.component';

export const evaluationCriteriaEntityRoute: Routes = [
    {
        path: 'evaluation-criteria-entity',
        component: EvaluationCriteriaEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'evaluation-criteria-entity/:id',
        component: EvaluationCriteriaEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const evaluationCriteriaEntityPopupRoute: Routes = [
    {
        path: 'evaluation-criteria-entity-new',
        component: EvaluationCriteriaEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'evaluation-criteria-entity/:id/edit',
        component: EvaluationCriteriaEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'evaluation-criteria-entity/:id/delete',
        component: EvaluationCriteriaEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
