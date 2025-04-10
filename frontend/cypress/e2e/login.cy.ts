describe("login", () => {

  it('should not log in with an incorrect username', () => {
    cy.visit("/login");
    const form = cy.get("form").within(() => {
      cy.get("input[name='username']").type("wronguser");
      cy.get("input[name=username]").should("have.value", "wronguser");
      cy.get('#password').type('JegHeterJacobLeinLittVanskeligPassord');
      cy.get("#password").should("have.value", "JegHeterJacobLeinLittVanskeligPassord");
      cy.get("button[type='submit']").should("not.be.disabled").click();
    });
    cy.get('.errorText').should('contain.text', 'Bad credentials');
  });
  it('should not log in with an incorrect password', () => {
    cy.visit("/login");
    const form = cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser");
      cy.get("input[name=username]").should("have.value", "testuser");
      cy.get('#password').type('WrongPassword123');
      cy.get("#password").should("have.value", "WrongPassword123");
      cy.get("button[type='submit']").should("not.be.disabled").click();
    });
    cy.get('.errorText').should('contain.text', 'Bad credentials');
  });
  it('should be able to log into testuser1', () => {
    cy.visit("/login");

    cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser1");
      cy.get("#password").type("password1");
      cy.get("button[type='submit']").click();
    });
    cy.wait(3000);
    cy.get('.link.profile').click();
    cy.get(".username").should("contain.text", "@testuser1");
  });


})

