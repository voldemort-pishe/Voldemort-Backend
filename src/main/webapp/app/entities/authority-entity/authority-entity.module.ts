import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    AuthorityEntityService,
    AuthorityEntityPopupService,
    AuthorityEntityComponent,
    AuthorityEntityDetailComponent,
    AuthorityEntityDialogComponent,
    AuthorityEntityPopupComponent,
    AuthorityEntityDeletePopupComponent,
    AuthorityEntityDeleteDialogComponent,
    authorityEntityRoute,
    authorityEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...authorityEntityRoute,
    ...authorityEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AuthorityEntityComponent,
        AuthorityEntityDetailComponent,
        AuthorityEntityDialogComponent,
        AuthorityEntityDeleteDialogComponent,
        AuthorityEntityPopupComponent,
        AuthorityEntityDeletePopupComponent,
    ],
    entryComponents: [
        AuthorityEntityComponent,
        AuthorityEntityDialogComponent,
        AuthorityEntityPopupComponent,
        AuthorityEntityDeleteDialogComponent,
        AuthorityEntityDeletePopupComponent,
    ],
    providers: [
        AuthorityEntityService,
        AuthorityEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortAuthorityEntityModule {}
