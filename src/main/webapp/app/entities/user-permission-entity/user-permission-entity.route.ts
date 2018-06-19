import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserPermissionEntityComponent } from './user-permission-entity.component';
import { UserPermissionEntityDetailComponent } from './user-permission-entity-detail.component';
import { UserPermissionEntityPopupComponent } from './user-permission-entity-dialog.component';
import { UserPermissionEntityDeletePopupComponent } from './user-permission-entity-delete-dialog.component';

export const userPermissionEntityRoute: Routes = [
    {
        path: 'user-permission-entity',
        component: UserPermissionEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPermissionEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-permission-entity/:id',
        component: UserPermissionEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPermissionEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPermissionEntityPopupRoute: Routes = [
    {
        path: 'user-permission-entity-new',
        component: UserPermissionEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPermissionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-permission-entity/:id/edit',
        component: UserPermissionEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPermissionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-permission-entity/:id/delete',
        component: UserPermissionEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserPermissionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
