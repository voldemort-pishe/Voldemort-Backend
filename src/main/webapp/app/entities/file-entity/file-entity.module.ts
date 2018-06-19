import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    FileEntityService,
    FileEntityPopupService,
    FileEntityComponent,
    FileEntityDetailComponent,
    FileEntityDialogComponent,
    FileEntityPopupComponent,
    FileEntityDeletePopupComponent,
    FileEntityDeleteDialogComponent,
    fileEntityRoute,
    fileEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...fileEntityRoute,
    ...fileEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FileEntityComponent,
        FileEntityDetailComponent,
        FileEntityDialogComponent,
        FileEntityDeleteDialogComponent,
        FileEntityPopupComponent,
        FileEntityDeletePopupComponent,
    ],
    entryComponents: [
        FileEntityComponent,
        FileEntityDialogComponent,
        FileEntityPopupComponent,
        FileEntityDeleteDialogComponent,
        FileEntityDeletePopupComponent,
    ],
    providers: [
        FileEntityService,
        FileEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortFileEntityModule {}
