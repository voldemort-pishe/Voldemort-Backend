import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    UserEntityService,
    UserEntityPopupService,
    UserEntityComponent,
    UserEntityDetailComponent,
    UserEntityDialogComponent,
    UserEntityPopupComponent,
    UserEntityDeletePopupComponent,
    UserEntityDeleteDialogComponent,
    userEntityRoute,
    userEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userEntityRoute,
    ...userEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserEntityComponent,
        UserEntityDetailComponent,
        UserEntityDialogComponent,
        UserEntityDeleteDialogComponent,
        UserEntityPopupComponent,
        UserEntityDeletePopupComponent,
    ],
    entryComponents: [
        UserEntityComponent,
        UserEntityDialogComponent,
        UserEntityPopupComponent,
        UserEntityDeleteDialogComponent,
        UserEntityDeletePopupComponent,
    ],
    providers: [
        UserEntityService,
        UserEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortUserEntityModule {}
