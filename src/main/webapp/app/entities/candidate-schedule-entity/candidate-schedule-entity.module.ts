import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CandidateScheduleEntityService,
    CandidateScheduleEntityPopupService,
    CandidateScheduleEntityComponent,
    CandidateScheduleEntityDetailComponent,
    CandidateScheduleEntityDialogComponent,
    CandidateScheduleEntityPopupComponent,
    CandidateScheduleEntityDeletePopupComponent,
    CandidateScheduleEntityDeleteDialogComponent,
    candidateScheduleEntityRoute,
    candidateScheduleEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candidateScheduleEntityRoute,
    ...candidateScheduleEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidateScheduleEntityComponent,
        CandidateScheduleEntityDetailComponent,
        CandidateScheduleEntityDialogComponent,
        CandidateScheduleEntityDeleteDialogComponent,
        CandidateScheduleEntityPopupComponent,
        CandidateScheduleEntityDeletePopupComponent,
    ],
    entryComponents: [
        CandidateScheduleEntityComponent,
        CandidateScheduleEntityDialogComponent,
        CandidateScheduleEntityPopupComponent,
        CandidateScheduleEntityDeleteDialogComponent,
        CandidateScheduleEntityDeletePopupComponent,
    ],
    providers: [
        CandidateScheduleEntityService,
        CandidateScheduleEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCandidateScheduleEntityModule {}
