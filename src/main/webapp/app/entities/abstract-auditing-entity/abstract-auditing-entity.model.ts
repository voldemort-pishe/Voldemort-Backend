import { BaseEntity } from './../../shared';

export class AbstractAuditingEntity implements BaseEntity {
    constructor(
        public id?: number,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
    ) {
    }
}
