import { Injectable } from '@nestjs/common';
import { PrismaService } from '../../../shared/prisma/prisma.service';
import { Transaction } from '../../../../generated/prisma';

@Injectable()
export class TransactionRepository {
  constructor(private readonly prisma: PrismaService) {}

  async create(data: {
    userId: number;
    fromCurrency: string;
    toCurrency: string;
    fromValue: number;
    toValue: number;
    rate: number;
  }): Promise<Transaction> {
    return this.prisma.transaction.create({ data });
  }

  async findByUserId(userId: number): Promise<Transaction[]> {
    return this.prisma.transaction.findMany({
      where: { userId },
      orderBy: { timestamp: 'desc' },
    });
  }
}
