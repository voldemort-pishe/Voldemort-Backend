import { BaseEntity } from './../../shared';

export class CompanyPipelineEntity implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public weight?: number,
        public company?: BaseEntity,
    ) {
    }
}
