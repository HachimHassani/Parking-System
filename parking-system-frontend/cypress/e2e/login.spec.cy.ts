import { Chance } from 'chance';

describe('Login Test', () => {

    it('Visits login page', () => {
        cy.visit('/login');
        //page visited
        cy.contains('Login');
    });

    it('Can switch to sign in form', () => {
        cy.visit('/login');
        //switch to signin
        cy.get('button[name=switch]').click();
        //check for signin form
        cy.contains('Confirm Password');
    });

    it('Can switch back to in form', () => {
        cy.visit('/login');
        //switch to signin
        cy.get('button[name=switch]').click();
        //check for signin form
        cy.contains('Confirm Password');
    });

    it('Cannot Login with wrong identifier', () => {
        cy.visit('/login');
        //fill wrong password and email
        const chance = new Chance();
        //some random identifier
        const email = chance.email();
        const password = chance.animal();
        //insert input
        cy.get('input[name=loginEmail]').type(email);
        cy.get('input[name=loginPassword]').type(password);
        //login
        cy.get('button[name=submit]').click();
        //expect password worng
        cy.contains('Password Error');
    });

    it('Can Login with correct identifier', () => {
        cy.visit('/login');
        //correct identifiers
        const email = 'haitamksiks2001@gmail.com';
        const password = '123456789';
        //insert input
        cy.get('input[name=loginEmail]').type(email);
        cy.get('input[name=loginPassword]').type(password);
        //login
        cy.get('button[name=submit]').click();
        cy.get('button[name=submit]').should('not.be', 'disabled');
        //expect password worng
        cy.url().should('contain', '/');
    });
})
