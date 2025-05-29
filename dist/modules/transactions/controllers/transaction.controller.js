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
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.TransactionController = void 0;
const common_1 = require("@nestjs/common");
const transaction_services_1 = require("../services/transaction.services");
const convert_request_dto_1 = require("../dto/convert-request.dto");
let TransactionController = class TransactionController {
    transactionService;
    constructor(transactionService) {
        this.transactionService = transactionService;
    }
    async convert(body) {
        try {
            return await this.transactionService.convertCurrency(body);
        }
        catch (error) {
            throw new common_1.HttpException(error.message, common_1.HttpStatus.BAD_REQUEST);
        }
    }
    async findAll(userId) {
        const id = Number(userId);
        if (isNaN(id)) {
            throw new common_1.HttpException('Invalid userId', common_1.HttpStatus.BAD_REQUEST);
        }
        return this.transactionService.getUserTransactions(id);
    }
};
exports.TransactionController = TransactionController;
__decorate([
    (0, common_1.Post)('/convert'),
    __param(0, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [convert_request_dto_1.ConvertRequestDto]),
    __metadata("design:returntype", Promise)
], TransactionController.prototype, "convert", null);
__decorate([
    (0, common_1.Get)(),
    __param(0, (0, common_1.Query)('userId')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String]),
    __metadata("design:returntype", Promise)
], TransactionController.prototype, "findAll", null);
exports.TransactionController = TransactionController = __decorate([
    (0, common_1.Controller)('transactions'),
    __metadata("design:paramtypes", [transaction_services_1.TransactionService])
], TransactionController);
//# sourceMappingURL=transaction.controller.js.map