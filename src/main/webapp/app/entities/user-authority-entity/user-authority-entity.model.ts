import { BaseEntity } from './../../shared';

export class UserAuthorityEntity implements BaseEntity {
    constructor(
        public id?: number,
        public authorityName?: string,
        public userPermissions?: BaseEntity[],
        public user?: BaseEntity,
    ) {
    }
}
