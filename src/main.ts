import { AppModule } from './app.module';
import { GlobalExceptionFilter } from './shared/exceptions/http-exception.filter';
import { Logger } from 'nestjs-pino';
import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { ValidationPipe } from '@nestjs/common';

async function bootstrap() {
  const app = await NestFactory.create(AppModule, {
    bufferLogs: true,
  });
  app.useLogger(app.get(Logger));
  app.useGlobalFilters(new GlobalExceptionFilter());
  app.useGlobalPipes(new ValidationPipe());
  app.enableCors({
    origin: '*',
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
    preflightContinue: false
  });

  const config = new DocumentBuilder()
    .setTitle('Currency Converter API')
    .setDescription('API for converting and persisting currency transactions.')
    .setVersion('1.0')
    .addTag('transactions')
    .build();

  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('api', app, document);

  await app.listen(process.env.PORT ?? 3000);
}
bootstrap();
