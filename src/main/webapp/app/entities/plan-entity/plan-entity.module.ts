import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    PlanEntityService,
    PlanEntityPopupService,
    PlanEntityComponent,
    PlanEntityDetailComponent,
    PlanEntityDialogComponent,
    PlanEntityPopupComponent,
    PlanEntityDeletePopupComponent,
    PlanEntityDeleteDialogComponent,
    planEntityRoute,
    planEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...planEntityRoute,
    ...planEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PlanEntityComponent,
        PlanEntityDetailComponent,
        PlanEntityDialogComponent,
        PlanEntityDeleteDialogComponent,
        PlanEntityPopupComponent,
        PlanEntityDeletePopupComponent,
    ],
    entryComponents: [
        PlanEntityComponent,
        PlanEntityDialogComponent,
        PlanEntityPopupComponent,
        PlanEntityDeleteDialogComponent,
        PlanEntityDeletePopupComponent,
    ],
    providers: [
        PlanEntityService,
        PlanEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortPlanEntityModule {}
