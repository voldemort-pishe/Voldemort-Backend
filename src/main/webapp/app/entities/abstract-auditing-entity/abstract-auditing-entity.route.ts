import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AbstractAuditingEntityComponent } from './abstract-auditing-entity.component';
import { AbstractAuditingEntityDetailComponent } from './abstract-auditing-entity-detail.component';
import { AbstractAuditingEntityPopupComponent } from './abstract-auditing-entity-dialog.component';
import { AbstractAuditingEntityDeletePopupComponent } from './abstract-auditing-entity-delete-dialog.component';

export const abstractAuditingEntityRoute: Routes = [
    {
        path: 'abstract-auditing-entity',
        component: AbstractAuditingEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AbstractAuditingEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'abstract-auditing-entity/:id',
        component: AbstractAuditingEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AbstractAuditingEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const abstractAuditingEntityPopupRoute: Routes = [
    {
        path: 'abstract-auditing-entity-new',
        component: AbstractAuditingEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AbstractAuditingEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'abstract-auditing-entity/:id/edit',
        component: AbstractAuditingEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AbstractAuditingEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'abstract-auditing-entity/:id/delete',
        component: AbstractAuditingEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AbstractAuditingEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
