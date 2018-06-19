import { BaseEntity } from './../../shared';

export class CommentEntity implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public commentText?: any,
        public status?: boolean,
        public candidate?: BaseEntity,
    ) {
        this.status = false;
    }
}
