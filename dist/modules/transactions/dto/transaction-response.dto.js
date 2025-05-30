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
exports.TransactionResponseDto = void 0;
const swagger_1 = require("@nestjs/swagger");
class TransactionResponseDto {
    transactionId;
    userId;
    fromCurrency;
    toCurrency;
    fromValue;
    toValue;
    rate;
    timestamp;
}
exports.TransactionResponseDto = TransactionResponseDto;
__decorate([
    (0, swagger_1.ApiProperty)({ example: 42 }),
    __metadata("design:type", Number)
], TransactionResponseDto.prototype, "transactionId", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 123 }),
    __metadata("design:type", Number)
], TransactionResponseDto.prototype, "userId", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 'USD' }),
    __metadata("design:type", String)
], TransactionResponseDto.prototype, "fromCurrency", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 'BRL' }),
    __metadata("design:type", String)
], TransactionResponseDto.prototype, "toCurrency", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 100 }),
    __metadata("design:type", Number)
], TransactionResponseDto.prototype, "fromValue", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 525.32 }),
    __metadata("design:type", Number)
], TransactionResponseDto.prototype, "toValue", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: 5.2532 }),
    __metadata("design:type", Number)
], TransactionResponseDto.prototype, "rate", void 0);
__decorate([
    (0, swagger_1.ApiProperty)({ example: '2024-05-19T18:00:00Z' }),
    __metadata("design:type", String)
], TransactionResponseDto.prototype, "timestamp", void 0);
//# sourceMappingURL=transaction-response.dto.js.map