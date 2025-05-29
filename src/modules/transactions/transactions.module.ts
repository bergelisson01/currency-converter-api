import { Module } from '@nestjs/common';
import { TransactionController } from './controllers/transaction.controller';
import { TransactionService } from './services/transaction.services';
import { TransactionRepository } from './repositories/transaction.repository';
import { PrismaService } from '../../shared/prisma/prisma.service';

@Module({
  controllers: [TransactionController],
  providers: [
    TransactionService,
    TransactionRepository,
    PrismaService,
  ],
})
export class TransactionModule {}
