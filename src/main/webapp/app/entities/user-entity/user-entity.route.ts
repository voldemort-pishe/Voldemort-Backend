import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserEntityComponent } from './user-entity.component';
import { UserEntityDetailComponent } from './user-entity-detail.component';
import { UserEntityPopupComponent } from './user-entity-dialog.component';
import { UserEntityDeletePopupComponent } from './user-entity-delete-dialog.component';

export const userEntityRoute: Routes = [
    {
        path: 'user-entity',
        component: UserEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-entity/:id',
        component: UserEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userEntityPopupRoute: Routes = [
    {
        path: 'user-entity-new',
        component: UserEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-entity/:id/edit',
        component: UserEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-entity/:id/delete',
        component: UserEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
