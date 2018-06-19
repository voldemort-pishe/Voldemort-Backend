import { BaseEntity } from './../../shared';

export const enum PermissionAction {
    'READ',
    'WRITE',
    'DELETE',
    'FULL'
}

export class UserPermissionEntity implements BaseEntity {
    constructor(
        public id?: number,
        public action?: PermissionAction,
        public userAuthority?: BaseEntity,
    ) {
    }
}
