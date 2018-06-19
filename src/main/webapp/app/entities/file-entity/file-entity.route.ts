import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FileEntityComponent } from './file-entity.component';
import { FileEntityDetailComponent } from './file-entity-detail.component';
import { FileEntityPopupComponent } from './file-entity-dialog.component';
import { FileEntityDeletePopupComponent } from './file-entity-delete-dialog.component';

export const fileEntityRoute: Routes = [
    {
        path: 'file-entity',
        component: FileEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'file-entity/:id',
        component: FileEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fileEntityPopupRoute: Routes = [
    {
        path: 'file-entity-new',
        component: FileEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'file-entity/:id/edit',
        component: FileEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'file-entity/:id/delete',
        component: FileEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FileEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
