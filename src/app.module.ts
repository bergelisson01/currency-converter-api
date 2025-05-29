import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PrismaService } from './shared/prisma/prisma.service';
import { TransactionModule } from './modules/transactions/transactions.module';

@Module({
  imports: [TransactionModule],
  controllers: [AppController],
  providers: [AppService, PrismaService],
  exports: [PrismaService],
})
export class AppModule {}
