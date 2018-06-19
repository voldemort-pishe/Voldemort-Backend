import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaymentTransactionEntityComponent } from './payment-transaction-entity.component';
import { PaymentTransactionEntityDetailComponent } from './payment-transaction-entity-detail.component';
import { PaymentTransactionEntityPopupComponent } from './payment-transaction-entity-dialog.component';
import { PaymentTransactionEntityDeletePopupComponent } from './payment-transaction-entity-delete-dialog.component';

export const paymentTransactionEntityRoute: Routes = [
    {
        path: 'payment-transaction-entity',
        component: PaymentTransactionEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTransactionEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payment-transaction-entity/:id',
        component: PaymentTransactionEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTransactionEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentTransactionEntityPopupRoute: Routes = [
    {
        path: 'payment-transaction-entity-new',
        component: PaymentTransactionEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTransactionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-transaction-entity/:id/edit',
        component: PaymentTransactionEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTransactionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-transaction-entity/:id/delete',
        component: PaymentTransactionEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentTransactionEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
