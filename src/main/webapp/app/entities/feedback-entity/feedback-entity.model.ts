import { BaseEntity } from './../../shared';

export const enum FeedbackRate {
    'STRONG_NEGETIVE',
    'NEGETIVE',
    'POSITIVE',
    'STRONG_POSITIVE'
}

export class FeedbackEntity implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public feedbackText?: any,
        public rating?: FeedbackRate,
        public candidate?: BaseEntity,
    ) {
    }
}
