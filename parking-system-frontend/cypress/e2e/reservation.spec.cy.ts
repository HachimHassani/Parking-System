import { Chance } from 'chance';

describe('Navigation Test', () => {
    //login before each
    beforeEach(() => {
        cy.visit('/login');
        //correct identifiers
        const email = 'haitamksiks2001@gmail.com';
        const password = '123456789';
        //insert input
        cy.get('input[name=loginEmail]').type(email);
        cy.get('input[name=loginPassword]').type(password);
        //login
        cy.get('button[name=submit]').click();
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
        cy.url().should('equal', '/');

        //cy.visit('/reservations');
        /* check for parking in reser */
    });
})
