import {should} from "vitest";

describe("Create listing", () => {
  before(() => {
    cy.visit("/login");
    const form = cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser1");
      cy.get("input[name=username]").should("have.value", "testuser1");
      cy.get('#password').type('password1');
      cy.get("#password").should("have.value", "password1");
      cy.get("button[type='submit']").should("not.be.disabled").click();
    });
    });
    it('Should be able to create listing', () => {
      cy.get('[data-testid="create-listing-link"]').click();
      cy.get('.username').should('contain.text', '@testuser');


    });
});
