import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    JobEntityService,
    JobEntityPopupService,
    JobEntityComponent,
    JobEntityDetailComponent,
    JobEntityDialogComponent,
    JobEntityPopupComponent,
    JobEntityDeletePopupComponent,
    JobEntityDeleteDialogComponent,
    jobEntityRoute,
    jobEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobEntityRoute,
    ...jobEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobEntityComponent,
        JobEntityDetailComponent,
        JobEntityDialogComponent,
        JobEntityDeleteDialogComponent,
        JobEntityPopupComponent,
        JobEntityDeletePopupComponent,
    ],
    entryComponents: [
        JobEntityComponent,
        JobEntityDialogComponent,
        JobEntityPopupComponent,
        JobEntityDeleteDialogComponent,
        JobEntityDeletePopupComponent,
    ],
    providers: [
        JobEntityService,
        JobEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortJobEntityModule {}
