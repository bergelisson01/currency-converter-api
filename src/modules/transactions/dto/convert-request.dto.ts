import { IsNotEmpty, IsNumber, IsString } from 'class-validator';

export class ConvertRequestDto {
  @IsNumber()
  userId: number;

  @IsString()
  @IsNotEmpty()
  fromCurrency: string;

  @IsString()
  @IsNotEmpty()
  toCurrency: string;

  @IsNumber()
  fromValue: number;
}
