import { BaseEntity } from './../../shared';

export class FileEntity implements BaseEntity {
    constructor(
        public id?: number,
        public filename?: string,
        public filetype?: string,
        public candidate?: BaseEntity,
    ) {
    }
}
