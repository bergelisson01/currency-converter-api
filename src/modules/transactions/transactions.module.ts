import { Module } from '@nestjs/common';
import { TransactionController } from './controllers/transaction.controller';
import { TransactionService } from './services/transaction.services';
import { TransactionRepository } from './repositories/transaction.repository';
import { PrismaService } from '../../shared/prisma/prisma.service';
import { CurrencyApiModule } from '../currency-api/currency-api.module';
import { CurrencyApiService } from '../currency-api/services/currency-api.service';

@Module({
  imports: [CurrencyApiModule],
  controllers: [TransactionController],
  providers: [
    CurrencyApiService,
    TransactionService,
    TransactionRepository,
    PrismaService,
  ],
})
export class TransactionModule {}
