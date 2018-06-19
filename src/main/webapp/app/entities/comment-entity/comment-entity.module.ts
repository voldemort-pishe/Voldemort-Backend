import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoldemortSharedModule } from '../../shared';
import {
    CommentEntityService,
    CommentEntityPopupService,
    CommentEntityComponent,
    CommentEntityDetailComponent,
    CommentEntityDialogComponent,
    CommentEntityPopupComponent,
    CommentEntityDeletePopupComponent,
    CommentEntityDeleteDialogComponent,
    commentEntityRoute,
    commentEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...commentEntityRoute,
    ...commentEntityPopupRoute,
];

@NgModule({
    imports: [
        VoldemortSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CommentEntityComponent,
        CommentEntityDetailComponent,
        CommentEntityDialogComponent,
        CommentEntityDeleteDialogComponent,
        CommentEntityPopupComponent,
        CommentEntityDeletePopupComponent,
    ],
    entryComponents: [
        CommentEntityComponent,
        CommentEntityDialogComponent,
        CommentEntityPopupComponent,
        CommentEntityDeleteDialogComponent,
        CommentEntityDeletePopupComponent,
    ],
    providers: [
        CommentEntityService,
        CommentEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortCommentEntityModule {}
