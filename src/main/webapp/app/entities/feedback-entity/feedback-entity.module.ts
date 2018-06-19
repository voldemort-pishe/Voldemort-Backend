import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    FeedbackEntityService,
    FeedbackEntityPopupService,
    FeedbackEntityComponent,
    FeedbackEntityDetailComponent,
    FeedbackEntityDialogComponent,
    FeedbackEntityPopupComponent,
    FeedbackEntityDeletePopupComponent,
    FeedbackEntityDeleteDialogComponent,
    feedbackEntityRoute,
    feedbackEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...feedbackEntityRoute,
    ...feedbackEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FeedbackEntityComponent,
        FeedbackEntityDetailComponent,
        FeedbackEntityDialogComponent,
        FeedbackEntityDeleteDialogComponent,
        FeedbackEntityPopupComponent,
        FeedbackEntityDeletePopupComponent,
    ],
    entryComponents: [
        FeedbackEntityComponent,
        FeedbackEntityDialogComponent,
        FeedbackEntityPopupComponent,
        FeedbackEntityDeleteDialogComponent,
        FeedbackEntityDeletePopupComponent,
    ],
    providers: [
        FeedbackEntityService,
        FeedbackEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortFeedbackEntityModule {}
