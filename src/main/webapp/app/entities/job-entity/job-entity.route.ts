import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JobEntityComponent } from './job-entity.component';
import { JobEntityDetailComponent } from './job-entity-detail.component';
import { JobEntityPopupComponent } from './job-entity-dialog.component';
import { JobEntityDeletePopupComponent } from './job-entity-delete-dialog.component';

export const jobEntityRoute: Routes = [
    {
        path: 'job-entity',
        component: JobEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-entity/:id',
        component: JobEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobEntityPopupRoute: Routes = [
    {
        path: 'job-entity-new',
        component: JobEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-entity/:id/edit',
        component: JobEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-entity/:id/delete',
        component: JobEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
