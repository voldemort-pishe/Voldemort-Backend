import { BaseEntity } from './../../shared';

export class CandidateEvaluationCriteriaEntity implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public userComment?: any,
        public evaluationCriteriaId?: number,
        public candidate?: BaseEntity,
    ) {
    }
}
