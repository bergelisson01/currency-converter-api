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
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger';
import { TransactionResponseDto } from '../dto/transaction-response.dto';

@ApiTags('transactions')
@Controller('transactions')
export class TransactionController {
  constructor(private readonly transactionService: TransactionService) {}

  @Post('/convert')
  @ApiOperation({ summary: 'Convert currency and save transaction' })
  @ApiResponse({
    status: 201,
    description: 'Conversion successful',
    type: TransactionResponseDto,
  })
  async convert(@Body() body: ConvertRequestDto) {
    try {
      return await this.transactionService.convertCurrency(body);
    } catch (error) {
      throw new HttpException(error.message, HttpStatus.BAD_REQUEST);
    }
  }

  @Get()
  @ApiOperation({ summary: 'Get transactions by user ID' })
  async findAll(@Query('userId') userId: string) {
    const id = Number(userId);
    if (isNaN(id)) {
      throw new HttpException('Invalid userId', HttpStatus.BAD_REQUEST);
    }
    return this.transactionService.getUserTransactions(id);
  }
}
