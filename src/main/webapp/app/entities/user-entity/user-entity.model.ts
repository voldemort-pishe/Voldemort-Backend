import { BaseEntity } from './../../shared';

export class UserEntity implements BaseEntity {
    constructor(
        public id?: number,
        public login?: string,
        public passwordHash?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public activated?: boolean,
        public activationKey?: string,
        public resetKey?: string,
        public resetDate?: any,
        public userAuthorities?: BaseEntity[],
        public companies?: BaseEntity[],
        public talentPools?: BaseEntity[],
        public invoices?: BaseEntity[],
    ) {
        this.activated = false;
    }
}
