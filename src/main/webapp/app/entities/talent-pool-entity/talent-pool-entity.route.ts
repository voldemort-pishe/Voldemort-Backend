import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TalentPoolEntityComponent } from './talent-pool-entity.component';
import { TalentPoolEntityDetailComponent } from './talent-pool-entity-detail.component';
import { TalentPoolEntityPopupComponent } from './talent-pool-entity-dialog.component';
import { TalentPoolEntityDeletePopupComponent } from './talent-pool-entity-delete-dialog.component';

export const talentPoolEntityRoute: Routes = [
    {
        path: 'talent-pool-entity',
        component: TalentPoolEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TalentPoolEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'talent-pool-entity/:id',
        component: TalentPoolEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TalentPoolEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const talentPoolEntityPopupRoute: Routes = [
    {
        path: 'talent-pool-entity-new',
        component: TalentPoolEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TalentPoolEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'talent-pool-entity/:id/edit',
        component: TalentPoolEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TalentPoolEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'talent-pool-entity/:id/delete',
        component: TalentPoolEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TalentPoolEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
