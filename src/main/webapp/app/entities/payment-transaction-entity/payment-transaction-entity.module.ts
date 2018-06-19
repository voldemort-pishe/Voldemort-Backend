import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    PaymentTransactionEntityService,
    PaymentTransactionEntityPopupService,
    PaymentTransactionEntityComponent,
    PaymentTransactionEntityDetailComponent,
    PaymentTransactionEntityDialogComponent,
    PaymentTransactionEntityPopupComponent,
    PaymentTransactionEntityDeletePopupComponent,
    PaymentTransactionEntityDeleteDialogComponent,
    paymentTransactionEntityRoute,
    paymentTransactionEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paymentTransactionEntityRoute,
    ...paymentTransactionEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PaymentTransactionEntityComponent,
        PaymentTransactionEntityDetailComponent,
        PaymentTransactionEntityDialogComponent,
        PaymentTransactionEntityDeleteDialogComponent,
        PaymentTransactionEntityPopupComponent,
        PaymentTransactionEntityDeletePopupComponent,
    ],
    entryComponents: [
        PaymentTransactionEntityComponent,
        PaymentTransactionEntityDialogComponent,
        PaymentTransactionEntityPopupComponent,
        PaymentTransactionEntityDeleteDialogComponent,
        PaymentTransactionEntityDeletePopupComponent,
    ],
    providers: [
        PaymentTransactionEntityService,
        PaymentTransactionEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortPaymentTransactionEntityModule {}
