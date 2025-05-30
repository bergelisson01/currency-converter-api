import { ConvertRequestDto } from '../dto/convert-request.dto';
import { TransactionRepository } from '../repositories/transaction.repository';
export declare class TransactionService {
    private readonly transactionRepo;
    constructor(transactionRepo: TransactionRepository);
    convertCurrency(dto: ConvertRequestDto): Promise<{
        transactionId: number;
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        toValue: number;
        rate: any;
        timestamp: string;
    }>;
    getUserTransactions(userId: number): Promise<{
        id: number;
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        toValue: number;
        rate: number;
        timestamp: Date;
    }[]>;
}
