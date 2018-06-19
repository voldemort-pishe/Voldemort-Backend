import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    EvaluationCriteriaEntityService,
    EvaluationCriteriaEntityPopupService,
    EvaluationCriteriaEntityComponent,
    EvaluationCriteriaEntityDetailComponent,
    EvaluationCriteriaEntityDialogComponent,
    EvaluationCriteriaEntityPopupComponent,
    EvaluationCriteriaEntityDeletePopupComponent,
    EvaluationCriteriaEntityDeleteDialogComponent,
    evaluationCriteriaEntityRoute,
    evaluationCriteriaEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...evaluationCriteriaEntityRoute,
    ...evaluationCriteriaEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EvaluationCriteriaEntityComponent,
        EvaluationCriteriaEntityDetailComponent,
        EvaluationCriteriaEntityDialogComponent,
        EvaluationCriteriaEntityDeleteDialogComponent,
        EvaluationCriteriaEntityPopupComponent,
        EvaluationCriteriaEntityDeletePopupComponent,
    ],
    entryComponents: [
        EvaluationCriteriaEntityComponent,
        EvaluationCriteriaEntityDialogComponent,
        EvaluationCriteriaEntityPopupComponent,
        EvaluationCriteriaEntityDeleteDialogComponent,
        EvaluationCriteriaEntityDeletePopupComponent,
    ],
    providers: [
        EvaluationCriteriaEntityService,
        EvaluationCriteriaEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortEvaluationCriteriaEntityModule {}
