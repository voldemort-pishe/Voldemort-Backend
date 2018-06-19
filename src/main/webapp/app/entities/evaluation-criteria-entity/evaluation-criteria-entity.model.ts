import { BaseEntity } from './../../shared';

export class EvaluationCriteriaEntity implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public company?: BaseEntity,
    ) {
    }
}
