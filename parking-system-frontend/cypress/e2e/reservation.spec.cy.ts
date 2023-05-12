import { Chance } from 'chance';

describe('Navigation Test', () => {
    it('Visits the initial project page', () => {
        cy.visit('/');
        cy.contains('arking System');
    });

    it('Can click reservation button', () => {
        cy.visit('/');
        cy.get('button[name=purchase]').first().click();
        cy.url().should('contain', 'reservations/add/parkingid');
    });

    it('Can add reservation ', () => {
        cy.visit('/');
        cy.get('button[name=purchase]').first().click();
        
        cy.get('input[name=reservationFrom]')
        .type('2020-01-01T12:00')
        .trigger('keydown',{ key: 'Enter' });

        cy.get('input[name=reservationTo]')
            .type('2020-01-01T13:00')
            .trigger('keydown', { key: 'Enter' });


        cy.get('h1[name=price]').contains('10.00 DH');

        cy.get('button[name=order]').click();
        cy.url().should('contain', '/');

    });
})
