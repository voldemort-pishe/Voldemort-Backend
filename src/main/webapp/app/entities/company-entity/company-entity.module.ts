import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CompanyEntityService,
    CompanyEntityPopupService,
    CompanyEntityComponent,
    CompanyEntityDetailComponent,
    CompanyEntityDialogComponent,
    CompanyEntityPopupComponent,
    CompanyEntityDeletePopupComponent,
    CompanyEntityDeleteDialogComponent,
    companyEntityRoute,
    companyEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyEntityRoute,
    ...companyEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CompanyEntityComponent,
        CompanyEntityDetailComponent,
        CompanyEntityDialogComponent,
        CompanyEntityDeleteDialogComponent,
        CompanyEntityPopupComponent,
        CompanyEntityDeletePopupComponent,
    ],
    entryComponents: [
        CompanyEntityComponent,
        CompanyEntityDialogComponent,
        CompanyEntityPopupComponent,
        CompanyEntityDeleteDialogComponent,
        CompanyEntityDeletePopupComponent,
    ],
    providers: [
        CompanyEntityService,
        CompanyEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCompanyEntityModule {}
