import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CompanyPipelineEntityService,
    CompanyPipelineEntityPopupService,
    CompanyPipelineEntityComponent,
    CompanyPipelineEntityDetailComponent,
    CompanyPipelineEntityDialogComponent,
    CompanyPipelineEntityPopupComponent,
    CompanyPipelineEntityDeletePopupComponent,
    CompanyPipelineEntityDeleteDialogComponent,
    companyPipelineEntityRoute,
    companyPipelineEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyPipelineEntityRoute,
    ...companyPipelineEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyPipelineEntityComponent,
        CompanyPipelineEntityDetailComponent,
        CompanyPipelineEntityDialogComponent,
        CompanyPipelineEntityDeleteDialogComponent,
        CompanyPipelineEntityPopupComponent,
        CompanyPipelineEntityDeletePopupComponent,
    ],
    entryComponents: [
        CompanyPipelineEntityComponent,
        CompanyPipelineEntityDialogComponent,
        CompanyPipelineEntityPopupComponent,
        CompanyPipelineEntityDeleteDialogComponent,
        CompanyPipelineEntityDeletePopupComponent,
    ],
    providers: [
        CompanyPipelineEntityService,
        CompanyPipelineEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCompanyPipelineEntityModule {}
