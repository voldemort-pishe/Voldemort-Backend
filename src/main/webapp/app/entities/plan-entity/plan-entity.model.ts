import { BaseEntity } from './../../shared';

export class PlanEntity implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public amount?: number,
    ) {
    }
}
