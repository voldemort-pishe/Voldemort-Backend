import { BaseEntity } from './../../shared';

export const enum CandidateState {
    'ACCEPTED',
    'REJECTED'
}

export class CandidateEntity implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public fileId?: number,
        public state?: CandidateState,
        public cellphone?: string,
        public email?: string,
        public candidatePipeline?: number,
        public feedbacks?: BaseEntity[],
        public comments?: BaseEntity[],
        public candidateSchedules?: BaseEntity[],
        public candidateEvaluationCriteria?: BaseEntity[],
        public file?: BaseEntity,
        public job?: BaseEntity,
    ) {
    }
}
