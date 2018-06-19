import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserAuthorityEntityComponent } from './user-authority-entity.component';
import { UserAuthorityEntityDetailComponent } from './user-authority-entity-detail.component';
import { UserAuthorityEntityPopupComponent } from './user-authority-entity-dialog.component';
import { UserAuthorityEntityDeletePopupComponent } from './user-authority-entity-delete-dialog.component';

export const userAuthorityEntityRoute: Routes = [
    {
        path: 'user-authority-entity',
        component: UserAuthorityEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAuthorityEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-authority-entity/:id',
        component: UserAuthorityEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAuthorityEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAuthorityEntityPopupRoute: Routes = [
    {
        path: 'user-authority-entity-new',
        component: UserAuthorityEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-authority-entity/:id/edit',
        component: UserAuthorityEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-authority-entity/:id/delete',
        component: UserAuthorityEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserAuthorityEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
