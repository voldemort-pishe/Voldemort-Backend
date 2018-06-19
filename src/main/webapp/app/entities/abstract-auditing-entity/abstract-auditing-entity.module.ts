import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    AbstractAuditingEntityService,
    AbstractAuditingEntityPopupService,
    AbstractAuditingEntityComponent,
    AbstractAuditingEntityDetailComponent,
    AbstractAuditingEntityDialogComponent,
    AbstractAuditingEntityPopupComponent,
    AbstractAuditingEntityDeletePopupComponent,
    AbstractAuditingEntityDeleteDialogComponent,
    abstractAuditingEntityRoute,
    abstractAuditingEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...abstractAuditingEntityRoute,
    ...abstractAuditingEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AbstractAuditingEntityComponent,
        AbstractAuditingEntityDetailComponent,
        AbstractAuditingEntityDialogComponent,
        AbstractAuditingEntityDeleteDialogComponent,
        AbstractAuditingEntityPopupComponent,
        AbstractAuditingEntityDeletePopupComponent,
    ],
    entryComponents: [
        AbstractAuditingEntityComponent,
        AbstractAuditingEntityDialogComponent,
        AbstractAuditingEntityPopupComponent,
        AbstractAuditingEntityDeleteDialogComponent,
        AbstractAuditingEntityDeletePopupComponent,
    ],
    providers: [
        AbstractAuditingEntityService,
        AbstractAuditingEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortAbstractAuditingEntityModule {}
