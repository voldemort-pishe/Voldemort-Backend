import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    UserAuthorityEntityService,
    UserAuthorityEntityPopupService,
    UserAuthorityEntityComponent,
    UserAuthorityEntityDetailComponent,
    UserAuthorityEntityDialogComponent,
    UserAuthorityEntityPopupComponent,
    UserAuthorityEntityDeletePopupComponent,
    UserAuthorityEntityDeleteDialogComponent,
    userAuthorityEntityRoute,
    userAuthorityEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userAuthorityEntityRoute,
    ...userAuthorityEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserAuthorityEntityComponent,
        UserAuthorityEntityDetailComponent,
        UserAuthorityEntityDialogComponent,
        UserAuthorityEntityDeleteDialogComponent,
        UserAuthorityEntityPopupComponent,
        UserAuthorityEntityDeletePopupComponent,
    ],
    entryComponents: [
        UserAuthorityEntityComponent,
        UserAuthorityEntityDialogComponent,
        UserAuthorityEntityPopupComponent,
        UserAuthorityEntityDeleteDialogComponent,
        UserAuthorityEntityDeletePopupComponent,
    ],
    providers: [
        UserAuthorityEntityService,
        UserAuthorityEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortUserAuthorityEntityModule {}
