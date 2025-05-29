import {
  Controller,
  Get,
  Post,
  Query,
  Body,
  HttpException,
  HttpStatus,
} from '@nestjs/common';
import { TransactionService } from '../services/transaction.services';
import { ConvertRequestDto } from '../dto/convert-request.dto';

@Controller('transactions')
export class TransactionController {
  constructor(private readonly transactionService: TransactionService) {}

  @Post('/convert')
  async convert(@Body() body: ConvertRequestDto) {
    try {
      return await this.transactionService.convertCurrency(body);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.BAD_REQUEST);
    }
  }

  @Get()
  async findAll(@Query('userId') userId: string) {
    const id = Number(userId);
    if (isNaN(id)) {
      throw new HttpException('Invalid userId', HttpStatus.BAD_REQUEST);
    }
    return this.transactionService.getUserTransactions(id);
  }
}
