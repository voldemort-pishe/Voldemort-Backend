import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyEntityComponent } from './company-entity.component';
import { CompanyEntityDetailComponent } from './company-entity-detail.component';
import { CompanyEntityPopupComponent } from './company-entity-dialog.component';
import { CompanyEntityDeletePopupComponent } from './company-entity-delete-dialog.component';

export const companyEntityRoute: Routes = [
    {
        path: 'company-entity',
        component: CompanyEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-entity/:id',
        component: CompanyEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyEntityPopupRoute: Routes = [
    {
        path: 'company-entity-new',
        component: CompanyEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-entity/:id/edit',
        component: CompanyEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-entity/:id/delete',
        component: CompanyEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
