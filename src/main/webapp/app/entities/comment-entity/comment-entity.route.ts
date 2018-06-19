import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CommentEntityComponent } from './comment-entity.component';
import { CommentEntityDetailComponent } from './comment-entity-detail.component';
import { CommentEntityPopupComponent } from './comment-entity-dialog.component';
import { CommentEntityDeletePopupComponent } from './comment-entity-delete-dialog.component';

export const commentEntityRoute: Routes = [
    {
        path: 'comment-entity',
        component: CommentEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comment-entity/:id',
        component: CommentEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentEntityPopupRoute: Routes = [
    {
        path: 'comment-entity-new',
        component: CommentEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-entity/:id/edit',
        component: CommentEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comment-entity/:id/delete',
        component: CommentEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommentEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
