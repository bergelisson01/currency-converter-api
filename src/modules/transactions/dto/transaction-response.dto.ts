import { ApiProperty } from '@nestjs/swagger';

export class TransactionResponseDto {
  @ApiProperty({ example: 42 })
  transactionId: number;

  @ApiProperty({ example: 123 })
  userId: number;

  @ApiProperty({ example: 'USD' })
  fromCurrency: string;

  @ApiProperty({ example: 'BRL' })
  toCurrency: string;

  @ApiProperty({ example: 100 })
  fromValue: number;

  @ApiProperty({ example: 525.32 })
  toValue: number;

  @ApiProperty({ example: 5.2532 })
  rate: number;

  @ApiProperty({ example: '2024-05-19T18:00:00Z' })
  timestamp: string;
}