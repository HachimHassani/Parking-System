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
    
    it('Cannot Signin with wrong identifier', () => {
        cy.visit('/login');
        //switch to signin
        cy.get('button[name=switch]').click();
        //fill wrong password and email
        const chance = new Chance();
        //some random identifier
        const firstName = chance.first();
        const lastName = chance.last();
        const licensePlate = chance.string({length: 5});
        const email = chance.email();
        const password = chance.animal();
        const confirmPassword = "0";
        //insert input
        cy.get('input[name=signinFirstName]').type(firstName);
        cy.get('input[name=signinLastName]').type(lastName);
        cy.get('input[name=signinLicensePlate]').type(licensePlate);
        cy.get('input[name=signinEmail]').type(email);
        cy.get('input[name=signinPassword]').type(password);
        cy.get('input[name=signinConfirmPassword]').type(confirmPassword);
        //login
        cy.get('button[name=submit]').click();
        //expect password worng
        cy.contains('Password confirm incorrect');
    });

    
    it('Can Signin with new identifier', () => {
        cy.visit('/login');
        //switch to signin
        cy.get('button[name=switch]').click();
        //fill somedata
        const chance = new Chance();
        //some random identifier
        const firstName = chance.first();
        const lastName = chance.last();
        const licensePlate = chance.string({ length: 5 });
        const email = chance.email();
        const password = chance.animal();
        const confirmPassword = password;
        //insert input
        cy.get('input[name=signinFirstName]').type(firstName);
        cy.get('input[name=signinLastName]').type(lastName);
        cy.get('input[name=signinLicensePlate]').type(licensePlate);
        cy.get('input[name=signinEmail]').type(email);
        cy.get('input[name=signinPassword]').type(password);
        cy.get('input[name=signinConfirmPassword]').type(confirmPassword);
        //sign in
        cy.get('button[name=submit]').click();
        //expect account to be created
        cy.contains('Account Created');
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
