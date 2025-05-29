import { PrismaService } from '../../../shared/prisma/prisma.service';
import { Transaction } from '../../../../generated/prisma';
export declare class TransactionRepository {
    private readonly prisma;
    constructor(prisma: PrismaService);
    create(data: {
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        toValue: number;
        rate: number;
    }): Promise<Transaction>;
    findByUserId(userId: number): Promise<Transaction[]>;
}
