import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    InvoiceEntityService,
    InvoiceEntityPopupService,
    InvoiceEntityComponent,
    InvoiceEntityDetailComponent,
    InvoiceEntityDialogComponent,
    InvoiceEntityPopupComponent,
    InvoiceEntityDeletePopupComponent,
    InvoiceEntityDeleteDialogComponent,
    invoiceEntityRoute,
    invoiceEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...invoiceEntityRoute,
    ...invoiceEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InvoiceEntityComponent,
        InvoiceEntityDetailComponent,
        InvoiceEntityDialogComponent,
        InvoiceEntityDeleteDialogComponent,
        InvoiceEntityPopupComponent,
        InvoiceEntityDeletePopupComponent,
    ],
    entryComponents: [
        InvoiceEntityComponent,
        InvoiceEntityDialogComponent,
        InvoiceEntityPopupComponent,
        InvoiceEntityDeleteDialogComponent,
        InvoiceEntityDeletePopupComponent,
    ],
    providers: [
        InvoiceEntityService,
        InvoiceEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortInvoiceEntityModule {}
