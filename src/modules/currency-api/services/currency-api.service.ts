import { Injectable, HttpException, HttpStatus } from '@nestjs/common';
import axios from 'axios';

@Injectable()
export class CurrencyApiService {
  async getConversionRate(fromCurrency: string, toCurrency: string): Promise<number> {
    try {
      const response = await axios.get('https://api.currencyapi.com/v3/latest', {
        params: {
          base_currency: fromCurrency,
          currencies: toCurrency,
        },
        headers: {
          apikey: process.env.CURRENCY_API_KEY,
        },
      });

      const rate = response.data?.data?.[toCurrency]?.value;

      if (!rate) {
        throw new HttpException('Conversion rate not found', HttpStatus.BAD_REQUEST);
      }

      return rate;
    } catch (error) {
      throw new HttpException(
        error.response?.data?.message || 'Currency API error',
        error.response?.status || HttpStatus.BAD_GATEWAY,
      );
    }
  }
}
