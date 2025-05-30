import { Test, TestingModule } from '@nestjs/testing';
import { TransactionService } from './transaction.services';
import { TransactionRepository } from '../repositories/transaction.repository';
import { CurrencyApiService } from '../../currency-api/services/currency-api.service';

describe('TransactionService', () => {
  let service: TransactionService;

  const mockTransactionRepository = {
    create: jest.fn(),
    findByUserId: jest.fn(),
  };
  
  const mockCurrencyApiService = {
    getConversionRate: jest.fn(),
  };

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        TransactionService,
        { provide: TransactionRepository, useValue: mockTransactionRepository },
        { provide: CurrencyApiService, useValue: mockCurrencyApiService }
      ],
    }).compile();

    service = module.get<TransactionService>(TransactionService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  it('should convert currency and save transaction', async () => {
    mockCurrencyApiService.getConversionRate.mockResolvedValue(5.25);
    mockTransactionRepository.create.mockResolvedValue({
      id: 1,
      userId: 123,
      fromCurrency: 'USD',
      toCurrency: 'BRL',
      fromValue: 100,
      toValue: 525,
      rate: 5.25,
      timestamp: new Date(),
    });

    const result = await service.convertCurrency({
      userId: 123,
      fromCurrency: 'USD',
      toCurrency: 'BRL',
      fromValue: 100,
    });

    expect(mockTransactionRepository.create).toHaveBeenCalled();
    expect(result.toValue).toBe(525);
  });
});
