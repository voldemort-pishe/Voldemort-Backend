import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VoldemortUserEntityModule } from './user-entity/user-entity.module';
import { VoldemortUserAuthorityEntityModule } from './user-authority-entity/user-authority-entity.module';
import { VoldemortAuthorityEntityModule } from './authority-entity/authority-entity.module';
import { VoldemortCandidateEntityModule } from './candidate-entity/candidate-entity.module';
import { VoldemortJobEntityModule } from './job-entity/job-entity.module';
import { VoldemortCompanyEntityModule } from './company-entity/company-entity.module';
import { VoldemortInvoiceEntityModule } from './invoice-entity/invoice-entity.module';
import { VoldemortPlanEntityModule } from './plan-entity/plan-entity.module';
import { VoldemortAbstractAuditingEntityModule } from './abstract-auditing-entity/abstract-auditing-entity.module';
import { VoldemortFileEntityModule } from './file-entity/file-entity.module';
import { VoldemortCommentEntityModule } from './comment-entity/comment-entity.module';
import { VoldemortFeedbackEntityModule } from './feedback-entity/feedback-entity.module';
import { VoldemortUserPermissionEntityModule } from './user-permission-entity/user-permission-entity.module';
import { VoldemortCandidateScheduleEntityModule } from './candidate-schedule-entity/candidate-schedule-entity.module';
import { VoldemortPaymentTransactionEntityModule } from './payment-transaction-entity/payment-transaction-entity.module';
import { VoldemortTalentPoolEntityModule } from './talent-pool-entity/talent-pool-entity.module';
import { VoldemortEvaluationCriteriaEntityModule } from './evaluation-criteria-entity/evaluation-criteria-entity.module';
import { VoldemortCompanyPipelineEntityModule } from './company-pipeline-entity/company-pipeline-entity.module';
import { VoldemortCandidateEvaluationCriteriaEntityModule } from './candidate-evaluation-criteria-entity/candidate-evaluation-criteria-entity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VoldemortUserEntityModule,
        VoldemortUserAuthorityEntityModule,
        VoldemortAuthorityEntityModule,
        VoldemortCandidateEntityModule,
        VoldemortJobEntityModule,
        VoldemortCompanyEntityModule,
        VoldemortInvoiceEntityModule,
        VoldemortPlanEntityModule,
        VoldemortAbstractAuditingEntityModule,
        VoldemortFileEntityModule,
        VoldemortCommentEntityModule,
        VoldemortFeedbackEntityModule,
        VoldemortUserPermissionEntityModule,
        VoldemortCandidateScheduleEntityModule,
        VoldemortPaymentTransactionEntityModule,
        VoldemortTalentPoolEntityModule,
        VoldemortEvaluationCriteriaEntityModule,
        VoldemortCompanyPipelineEntityModule,
        VoldemortCandidateEvaluationCriteriaEntityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoldemortEntityModule {}
