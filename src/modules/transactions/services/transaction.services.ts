import { Injectable } from '@nestjs/common';
import axios from 'axios';
import { ConvertRequestDto } from '../dto/convert-request.dto';
import { TransactionRepository } from '../repositories/transaction.repository';

@Injectable()
export class TransactionService {
  constructor(private readonly transactionRepo: TransactionRepository) {}

  async convertCurrency(dto: ConvertRequestDto) {
    const { userId, fromCurrency, toCurrency, fromValue } = dto;
    const response = await axios.get('https://api.currencyapi.com/v3/latest', {
      params: {
        base_currency: fromCurrency,
        currencies: toCurrency,
      },
      headers: {
        apikey: process.env.CURRENCY_API_KEY,
      },
    });

    const rate = response.data.data[toCurrency]?.value;
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
