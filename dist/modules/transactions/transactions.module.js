"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.TransactionModule = void 0;
const common_1 = require("@nestjs/common");
const transaction_controller_1 = require("./controllers/transaction.controller");
const transaction_services_1 = require("./services/transaction.services");
const transaction_repository_1 = require("./repositories/transaction.repository");
const prisma_service_1 = require("../../shared/prisma/prisma.service");
const currency_api_module_1 = require("../currency-api/currency-api.module");
const currency_api_service_1 = require("../currency-api/services/currency-api.service");
let TransactionModule = class TransactionModule {
};
exports.TransactionModule = TransactionModule;
exports.TransactionModule = TransactionModule = __decorate([
    (0, common_1.Module)({
        imports: [currency_api_module_1.CurrencyApiModule],
        controllers: [transaction_controller_1.TransactionController],
        providers: [
            currency_api_service_1.CurrencyApiService,
            transaction_services_1.TransactionService,
            transaction_repository_1.TransactionRepository,
            prisma_service_1.PrismaService,
        ],
    })
], TransactionModule);
//# sourceMappingURL=transactions.module.js.map