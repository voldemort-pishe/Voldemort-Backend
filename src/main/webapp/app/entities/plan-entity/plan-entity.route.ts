import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PlanEntityComponent } from './plan-entity.component';
import { PlanEntityDetailComponent } from './plan-entity-detail.component';
import { PlanEntityPopupComponent } from './plan-entity-dialog.component';
import { PlanEntityDeletePopupComponent } from './plan-entity-delete-dialog.component';

export const planEntityRoute: Routes = [
    {
        path: 'plan-entity',
        component: PlanEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'plan-entity/:id',
        component: PlanEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planEntityPopupRoute: Routes = [
    {
        path: 'plan-entity-new',
        component: PlanEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plan-entity/:id/edit',
        component: PlanEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'plan-entity/:id/delete',
        component: PlanEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PlanEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
