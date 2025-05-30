"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const app_module_1 = require("./app.module");
const http_exception_filter_1 = require("./common/filters/http-exception.filter");
const nestjs_pino_1 = require("nestjs-pino");
const core_1 = require("@nestjs/core");
const swagger_1 = require("@nestjs/swagger");
const common_1 = require("@nestjs/common");
async function bootstrap() {
    const app = await core_1.NestFactory.create(app_module_1.AppModule, {
        bufferLogs: true,
    });
    app.useLogger(app.get(nestjs_pino_1.Logger));
    app.useGlobalFilters(new http_exception_filter_1.GlobalExceptionFilter());
    app.useGlobalPipes(new common_1.ValidationPipe());
    const config = new swagger_1.DocumentBuilder()
        .setTitle('Currency Converter API')
        .setDescription('API for converting and persisting currency transactions.')
        .setVersion('1.0')
        .addTag('transactions')
        .build();
    const document = swagger_1.SwaggerModule.createDocument(app, config);
    swagger_1.SwaggerModule.setup('api', app, document);
    await app.listen(process.env.PORT ?? 3000);
}
bootstrap();
//# sourceMappingURL=main.js.map