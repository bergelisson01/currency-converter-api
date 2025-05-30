import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class ConvertRequestDto {
  @ApiProperty({ example: 123, description: 'User ID' })
  @IsNumber()
  userId: number;

  @ApiProperty({ example: 'USD', description: 'Currency to convert from' })
  @IsString()
  @IsNotEmpty()
  fromCurrency: string;

  @ApiProperty({ example: 'BRL', description: 'Currency to convert to' })
  @IsString()
  @IsNotEmpty()
  toCurrency: string;

  @ApiProperty({ example: 100, description: 'Amount to convert' })
  @IsNumber()
  fromValue: number;
}
