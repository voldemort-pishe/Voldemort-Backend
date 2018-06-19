import { BaseEntity } from './../../shared';

export class CandidateScheduleEntity implements BaseEntity {
    constructor(
        public id?: number,
        public owner?: number,
        public scheduleDate?: any,
        public candidate?: BaseEntity,
    ) {
    }
}
