import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CandidateEvaluationCriteriaEntityService,
    CandidateEvaluationCriteriaEntityPopupService,
    CandidateEvaluationCriteriaEntityComponent,
    CandidateEvaluationCriteriaEntityDetailComponent,
    CandidateEvaluationCriteriaEntityDialogComponent,
    CandidateEvaluationCriteriaEntityPopupComponent,
    CandidateEvaluationCriteriaEntityDeletePopupComponent,
    CandidateEvaluationCriteriaEntityDeleteDialogComponent,
    candidateEvaluationCriteriaEntityRoute,
    candidateEvaluationCriteriaEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...candidateEvaluationCriteriaEntityRoute,
    ...candidateEvaluationCriteriaEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CandidateEvaluationCriteriaEntityComponent,
        CandidateEvaluationCriteriaEntityDetailComponent,
        CandidateEvaluationCriteriaEntityDialogComponent,
        CandidateEvaluationCriteriaEntityDeleteDialogComponent,
        CandidateEvaluationCriteriaEntityPopupComponent,
        CandidateEvaluationCriteriaEntityDeletePopupComponent,
    ],
    entryComponents: [
        CandidateEvaluationCriteriaEntityComponent,
        CandidateEvaluationCriteriaEntityDialogComponent,
        CandidateEvaluationCriteriaEntityPopupComponent,
        CandidateEvaluationCriteriaEntityDeleteDialogComponent,
        CandidateEvaluationCriteriaEntityDeletePopupComponent,
    ],
    providers: [
        CandidateEvaluationCriteriaEntityService,
        CandidateEvaluationCriteriaEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCandidateEvaluationCriteriaEntityModule {}
