import { Injectable } from '@nestjs/common';
import axios from 'axios';
import { ConvertRequestDto } from '../dto/convert-request.dto';
import { TransactionRepository } from '../repositories/transaction.repository';
import { CurrencyApiService } from '../../currency-api/services/currency-api.service';

@Injectable()
export class TransactionService {
  constructor(
    private readonly transactionRepo: TransactionRepository, 
    private readonly currencyApiService: CurrencyApiService
  ) {}

  async convertCurrency(dto: ConvertRequestDto) {
    const { userId, fromCurrency, toCurrency, fromValue } = dto;
    const rate = await this.currencyApiService.getConversionRate(fromCurrency, toCurrency);
    if (!rate) {
      throw new Error('Failed to fetch conversion rate.');
    }

    const toValue = Number((fromValue * rate).toFixed(4));
    const transaction = await this.transactionRepo.create({
      userId,
      fromCurrency,
      toCurrency,
      fromValue,
      toValue,
      rate,
    });

    return {
      transactionId: transaction.id,
      userId,
      fromCurrency,
      toCurrency,
      fromValue,
      toValue,
      rate,
      timestamp: transaction.timestamp.toISOString(),
    };
  }

  async getUserTransactions(userId: number) {
    return this.transactionRepo.findByUserId(userId);
  }
}
