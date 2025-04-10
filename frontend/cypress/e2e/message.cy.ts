describe("Message", () => {
  before(() => {
    cy.visit("/login");
    const form = cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser1");
      cy.get("input[name=username]").should("have.value", "testuser1");
      cy.get('#password').type('password1');
      cy.get("#password").should("have.value", "password1");
      cy.get("button[type='submit']").should("not.be.disabled").click();
      cy.wait(1000);
    });
  });

  it('Should be able to send a message and user receives', () => {
    cy.get('.outer-wrapper.medium').eq(0).click();
    cy.get('.contact-button').click();
    cy.get('input.input').type('Hei, er denne fortsatt ledig?');
    cy.get('#send-button').click();
    cy.visit('/profile');
    cy.get('.logout-button').click();

    cy.get("input[name='username']").type("testuser0");
    cy.get("input[name=username]").should("have.value", "testuser0");
    cy.get('#password').type('password0');
    cy.get("#password").should("have.value", "password0");
    cy.get("button[type='submit']").should("not.be.disabled").click();
    cy.wait(1000);

    cy.visit('/chat');
    cy.get('.chats-wrapper').should('exist').click();
    cy.contains('.message-content', 'Hei, er denne fortsatt ledig?');

  });
})
