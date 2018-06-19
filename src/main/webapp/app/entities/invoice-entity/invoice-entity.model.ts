import { BaseEntity } from './../../shared';

export const enum SubscribeState {
    'MONTHLY',
    'ANNUALLY',
    'EXPIRED'
}

export const enum PaymentType {
    'PASARGARD',
    'SAMAN'
}

export const enum InvoiceStatus {
    'SUCCESS',
    'FAILED'
}

export class InvoiceEntity implements BaseEntity {
    constructor(
        public id?: number,
        public subscription?: SubscribeState,
        public paymentType?: PaymentType,
        public paymentDate?: any,
        public amount?: number,
        public amountWithTax?: number,
        public status?: InvoiceStatus,
        public paymentTransactions?: BaseEntity[],
        public user?: BaseEntity,
    ) {
    }
}
