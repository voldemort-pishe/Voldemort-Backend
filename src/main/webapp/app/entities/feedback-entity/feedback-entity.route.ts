import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FeedbackEntityComponent } from './feedback-entity.component';
import { FeedbackEntityDetailComponent } from './feedback-entity-detail.component';
import { FeedbackEntityPopupComponent } from './feedback-entity-dialog.component';
import { FeedbackEntityDeletePopupComponent } from './feedback-entity-delete-dialog.component';

export const feedbackEntityRoute: Routes = [
    {
        path: 'feedback-entity',
        component: FeedbackEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FeedbackEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'feedback-entity/:id',
        component: FeedbackEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FeedbackEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feedbackEntityPopupRoute: Routes = [
    {
        path: 'feedback-entity-new',
        component: FeedbackEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FeedbackEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'feedback-entity/:id/edit',
        component: FeedbackEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FeedbackEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'feedback-entity/:id/delete',
        component: FeedbackEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FeedbackEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
