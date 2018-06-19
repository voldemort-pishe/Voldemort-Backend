import { BaseEntity } from './../../shared';

export class CompanyEntity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public jobs?: BaseEntity[],
        public evaluationCriteria?: BaseEntity[],
        public companyPipelines?: BaseEntity[],
        public user?: BaseEntity,
    ) {
    }
}
