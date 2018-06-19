import { BaseEntity } from './../../shared';

export class PaymentTransactionEntity implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public refrenceId?: number,
        public amount?: number,
        public invoice?: BaseEntity,
    ) {
    }
}
