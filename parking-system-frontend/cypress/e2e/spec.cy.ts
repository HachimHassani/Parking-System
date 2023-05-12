import {Chance} from 'chance';

describe('Navigation Test', () => {
	it('Visits the initial project page', () => {
		cy.visit('/');
		cy.contains('arking System');
	});
})
