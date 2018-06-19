import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { InvoiceEntityComponent } from './invoice-entity.component';
import { InvoiceEntityDetailComponent } from './invoice-entity-detail.component';
import { InvoiceEntityPopupComponent } from './invoice-entity-dialog.component';
import { InvoiceEntityDeletePopupComponent } from './invoice-entity-delete-dialog.component';

export const invoiceEntityRoute: Routes = [
    {
        path: 'invoice-entity',
        component: InvoiceEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvoiceEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'invoice-entity/:id',
        component: InvoiceEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvoiceEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invoiceEntityPopupRoute: Routes = [
    {
        path: 'invoice-entity-new',
        component: InvoiceEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvoiceEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoice-entity/:id/edit',
        component: InvoiceEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvoiceEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'invoice-entity/:id/delete',
        component: InvoiceEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InvoiceEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
