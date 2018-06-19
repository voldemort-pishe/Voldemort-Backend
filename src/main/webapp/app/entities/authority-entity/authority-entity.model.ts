import { BaseEntity } from './../../shared';

export class AuthorityEntity implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
