import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AuthorityEntityComponent } from './authority-entity.component';
import { AuthorityEntityDetailComponent } from './authority-entity-detail.component';
import { AuthorityEntityPopupComponent } from './authority-entity-dialog.component';
import { AuthorityEntityDeletePopupComponent } from './authority-entity-delete-dialog.component';

export const authorityEntityRoute: Routes = [
    {
        path: 'authority-entity',
        component: AuthorityEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorityEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'authority-entity/:id',
        component: AuthorityEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorityEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const authorityEntityPopupRoute: Routes = [
    {
        path: 'authority-entity-new',
        component: AuthorityEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authority-entity/:id/edit',
        component: AuthorityEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'authority-entity/:id/delete',
        component: AuthorityEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
