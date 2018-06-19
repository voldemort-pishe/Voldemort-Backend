import { BaseEntity } from './../../shared';

export const enum CandidateState {
    'ACCEPTED',
    'REJECTED'
}

export class TalentPoolEntity implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public fileId?: number,
        public state?: CandidateState,
        public cellphone?: string,
        public email?: string,
        public user?: BaseEntity,
    ) {
    }
}
