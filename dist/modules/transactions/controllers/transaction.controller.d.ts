import { TransactionService } from '../services/transaction.services';
import { ConvertRequestDto } from '../dto/convert-request.dto';
export declare class TransactionController {
    private readonly transactionService;
    constructor(transactionService: TransactionService);
    convert(body: ConvertRequestDto): Promise<{
        transactionId: number;
        userId: number;
        fromCurrency: string;
        toCurrency: string;
        fromValue: number;
        toValue: number;
        rate: number;
        timestamp: string;
    }>;
    findAll(userId: string): Promise<{
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
