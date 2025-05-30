describe('Currency Conversion API', () => {
  const baseUrl = '/transactions';

  it('should convert currency and return transaction', () => {
    cy.request('POST', `${baseUrl}/convert`, {
      userId: 123,
      fromCurrency: 'USD',
      toCurrency: 'BRL',
      fromValue: 100,
    }).then((response) => {
      expect(response.status).to.eq(201);
      expect(response.body).to.have.property('transactionId');
      expect(response.body).to.have.property('rate');
      expect(response.body.toValue).to.be.greaterThan(0);
    });
  });

  it('should retrieve transactions for a user', () => {
    cy.request('GET', `${baseUrl}?userId=123`).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
    });
  });

  it('should return 400 for invalid fromCurrency', () => {
    cy.request({
      method: 'POST',
      url: `${baseUrl}/convert`,
      failOnStatusCode: false,
      body: {
        userId: 123,
        fromCurrency: 'XXX',
        toCurrency: 'BRL',
        fromValue: 100,
      },
    }).then((response) => {
      expect(response.status).to.be.oneOf([400, 422]);
      expect(response.body.message).to.exist;
    });
  });

  it('should return 400 for invalid toCurrency', () => {
    cy.request({
      method: 'POST',
      url: `${baseUrl}/convert`,
      failOnStatusCode: false,
      body: {
        userId: 123,
        fromCurrency: 'BRL',
        toCurrency: 'XXX',
        fromValue: 100,
      },
    }).then((response) => {
      expect(response.status).to.be.oneOf([400, 422]);
      expect(response.body.message).to.exist;
    });
  });
});
