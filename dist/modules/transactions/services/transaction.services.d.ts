import { ConvertRequestDto } from '../dto/convert-request.dto';
import { TransactionRepository } from '../repositories/transaction.repository';
import { CurrencyApiService } from '../../currency-api/services/currency-api.service';
export declare class TransactionService {
    private readonly transactionRepo;
    private readonly currencyApiService;
    constructor(transactionRepo: TransactionRepository, currencyApiService: CurrencyApiService);
    convertCurrency(dto: ConvertRequestDto): Promise<{
        transactionId: number;
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        toValue: number;
        rate: number;
        timestamp: string;
    }>;
    getUserTransactions(userId: number): Promise<{
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        id: number;
        toValue: number;
        rate: number;
        timestamp: Date;
    }[]>;
}
