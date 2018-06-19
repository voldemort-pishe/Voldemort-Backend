import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CandidateEvaluationCriteriaEntityComponent } from './candidate-evaluation-criteria-entity.component';
import { CandidateEvaluationCriteriaEntityDetailComponent } from './candidate-evaluation-criteria-entity-detail.component';
import { CandidateEvaluationCriteriaEntityPopupComponent } from './candidate-evaluation-criteria-entity-dialog.component';
import {
    CandidateEvaluationCriteriaEntityDeletePopupComponent
} from './candidate-evaluation-criteria-entity-delete-dialog.component';

export const candidateEvaluationCriteriaEntityRoute: Routes = [
    {
        path: 'candidate-evaluation-criteria-entity',
        component: CandidateEvaluationCriteriaEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'candidate-evaluation-criteria-entity/:id',
        component: CandidateEvaluationCriteriaEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidateEvaluationCriteriaEntityPopupRoute: Routes = [
    {
        path: 'candidate-evaluation-criteria-entity-new',
        component: CandidateEvaluationCriteriaEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-evaluation-criteria-entity/:id/edit',
        component: CandidateEvaluationCriteriaEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'candidate-evaluation-criteria-entity/:id/delete',
        component: CandidateEvaluationCriteriaEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CandidateEvaluationCriteriaEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
