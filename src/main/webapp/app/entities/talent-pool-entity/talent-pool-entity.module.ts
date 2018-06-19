import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    TalentPoolEntityService,
    TalentPoolEntityPopupService,
    TalentPoolEntityComponent,
    TalentPoolEntityDetailComponent,
    TalentPoolEntityDialogComponent,
    TalentPoolEntityPopupComponent,
    TalentPoolEntityDeletePopupComponent,
    TalentPoolEntityDeleteDialogComponent,
    talentPoolEntityRoute,
    talentPoolEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...talentPoolEntityRoute,
    ...talentPoolEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TalentPoolEntityComponent,
        TalentPoolEntityDetailComponent,
        TalentPoolEntityDialogComponent,
        TalentPoolEntityDeleteDialogComponent,
        TalentPoolEntityPopupComponent,
        TalentPoolEntityDeletePopupComponent,
    ],
    entryComponents: [
        TalentPoolEntityComponent,
        TalentPoolEntityDialogComponent,
        TalentPoolEntityPopupComponent,
        TalentPoolEntityDeleteDialogComponent,
        TalentPoolEntityDeletePopupComponent,
    ],
    providers: [
        TalentPoolEntityService,
        TalentPoolEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortTalentPoolEntityModule {}
