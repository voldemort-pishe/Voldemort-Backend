import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    UserPermissionEntityService,
    UserPermissionEntityPopupService,
    UserPermissionEntityComponent,
    UserPermissionEntityDetailComponent,
    UserPermissionEntityDialogComponent,
    UserPermissionEntityPopupComponent,
    UserPermissionEntityDeletePopupComponent,
    UserPermissionEntityDeleteDialogComponent,
    userPermissionEntityRoute,
    userPermissionEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userPermissionEntityRoute,
    ...userPermissionEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserPermissionEntityComponent,
        UserPermissionEntityDetailComponent,
        UserPermissionEntityDialogComponent,
        UserPermissionEntityDeleteDialogComponent,
        UserPermissionEntityPopupComponent,
        UserPermissionEntityDeletePopupComponent,
    ],
    entryComponents: [
        UserPermissionEntityComponent,
        UserPermissionEntityDialogComponent,
        UserPermissionEntityPopupComponent,
        UserPermissionEntityDeleteDialogComponent,
        UserPermissionEntityDeletePopupComponent,
    ],
    providers: [
        UserPermissionEntityService,
        UserPermissionEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortUserPermissionEntityModule {}
