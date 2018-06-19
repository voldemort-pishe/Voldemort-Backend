import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CandidateEntityService,
    CandidateEntityPopupService,
    CandidateEntityComponent,
    CandidateEntityDetailComponent,
    CandidateEntityDialogComponent,
    CandidateEntityPopupComponent,
    CandidateEntityDeletePopupComponent,
    CandidateEntityDeleteDialogComponent,
    candidateEntityRoute,
    candidateEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candidateEntityRoute,
    ...candidateEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidateEntityComponent,
        CandidateEntityDetailComponent,
        CandidateEntityDialogComponent,
        CandidateEntityDeleteDialogComponent,
        CandidateEntityPopupComponent,
        CandidateEntityDeletePopupComponent,
    ],
    entryComponents: [
        CandidateEntityComponent,
        CandidateEntityDialogComponent,
        CandidateEntityPopupComponent,
        CandidateEntityDeleteDialogComponent,
        CandidateEntityDeletePopupComponent,
    ],
    providers: [
        CandidateEntityService,
        CandidateEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCandidateEntityModule {}
