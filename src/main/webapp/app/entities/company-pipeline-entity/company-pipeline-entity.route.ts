import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CompanyPipelineEntityComponent } from './company-pipeline-entity.component';
import { CompanyPipelineEntityDetailComponent } from './company-pipeline-entity-detail.component';
import { CompanyPipelineEntityPopupComponent } from './company-pipeline-entity-dialog.component';
import { CompanyPipelineEntityDeletePopupComponent } from './company-pipeline-entity-delete-dialog.component';

export const companyPipelineEntityRoute: Routes = [
    {
        path: 'company-pipeline-entity',
        component: CompanyPipelineEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyPipelineEntities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-pipeline-entity/:id',
        component: CompanyPipelineEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyPipelineEntities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyPipelineEntityPopupRoute: Routes = [
    {
        path: 'company-pipeline-entity-new',
        component: CompanyPipelineEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyPipelineEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-pipeline-entity/:id/edit',
        component: CompanyPipelineEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyPipelineEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-pipeline-entity/:id/delete',
        component: CompanyPipelineEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CompanyPipelineEntities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
