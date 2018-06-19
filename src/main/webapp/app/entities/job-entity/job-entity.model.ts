import { BaseEntity } from './../../shared';

export class JobEntity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public candidate?: BaseEntity,
        public company?: BaseEntity,
    ) {
    }
}
