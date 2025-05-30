"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.TransactionService = void 0;
const common_1 = require("@nestjs/common");
const transaction_repository_1 = require("../repositories/transaction.repository");
const currency_api_service_1 = require("../../currency-api/services/currency-api.service");
let TransactionService = class TransactionService {
    transactionRepo;
    currencyApiService;
    constructor(transactionRepo, currencyApiService) {
        this.transactionRepo = transactionRepo;
        this.currencyApiService = currencyApiService;
    }
    async convertCurrency(dto) {
        const { userId, fromCurrency, toCurrency, fromValue } = dto;
        const rate = await this.currencyApiService.getConversionRate(fromCurrency, toCurrency);
        if (!rate) {
            throw new Error('Failed to fetch conversion rate.');
        }
        const toValue = Number((fromValue * rate).toFixed(4));
        const transaction = await this.transactionRepo.create({
            userId,
            fromCurrency,
            toCurrency,
            fromValue,
            toValue,
            rate,
        });
        return {
            transactionId: transaction.id,
            userId,
            fromCurrency,
            toCurrency,
            fromValue,
            toValue,
            rate,
            timestamp: transaction.timestamp.toISOString(),
        };
    }
    async getUserTransactions(userId) {
        return this.transactionRepo.findByUserId(userId);
    }
};
exports.TransactionService = TransactionService;
exports.TransactionService = TransactionService = __decorate([
    (0, common_1.Injectable)(),
    __metadata("design:paramtypes", [transaction_repository_1.TransactionRepository,
        currency_api_service_1.CurrencyApiService])
], TransactionService);
//# sourceMappingURL=transaction.services.js.map