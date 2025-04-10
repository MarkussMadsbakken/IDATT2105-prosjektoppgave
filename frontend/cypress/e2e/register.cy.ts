

describe("register", () => {
  before(() => {
    const base64 = "/9j/4AAQSkZJRgABAQEASABIAAD..."; // (lim inn et gyldig base64-bilde)
    cy.writeFile('cypress/fixtures/testImage.png', base64, 'base64');
  });
    it("should display the register form", () => {
        cy.visit("/register");
        const form = cy.get("form").should("exist");
        form.find("input[name=username]").should("exist");
    });

    it("should have step buttons", () => {
        cy.visit("/register");

        cy.get("button[class*='next-step']").should("exist");
        cy.get("button[class*='prev-step']").should("exist");
    })


  it('should be able to enter username and password and then go back', () => {
    cy.visit("/register");
    const form = cy.get("form").within(() =>
    {
      cy.get("input[name='username']").type("testuser");
      cy.get("input[name=username]").should("have.value", "testuser");
      cy.get("button.next-step").should("not.be.disabled").click();
      cy.get("button.prev-step").should("not.be.disabled").click();
      cy.get("input[name='username']").should("have.value", "testuser");
    });
  });


  it("should say that the username is available", () => {
        cy.visit("/register");
        const form = cy.get("form").should("exist");

        form.find("input[name=username]").type("testuser");
        cy.get("p[class*='username-is-avaiable']").should("exist");
    });

  it('should be able to register with picture', () => {
    cy.visit("/register");
    const form = cy.get("form").within(() =>
    {
      cy.get("input[name='username']").type("testuser");
      cy.get("input[name=username]").should("have.value", "testuser");
      cy.get("button.next-step").should("not.be.disabled").click();

      cy.get("input[name='password']").type("JegHeterJacobLeinLittVanskeligPassord1");
      cy.get("input[name=password]").should("have.value", "JegHeterJacobLeinLittVanskeligPassord1");

      cy.get("button.next-step").should("not.be.disabled").click();
      cy.get("input[type='file']").should("exist");
      cy.get('[data-cy="drop-zone"]')
        .selectFile('cypress/fixtures/testImage.png', {
          action: 'drag-drop',
          force: true
        });
      cy.get('.success-message').should('exist');
      cy.get('input[name="firstname"]').type("Jacob");
      cy.get('input[name="firstname"]').should('have.value', 'Jacob');
      cy.get("input[name='lastname']").type("Lein");
      cy.get("input[name='lastname']").should('have.value', 'Lein');
      cy.get("button.next-step").should("not.be.disabled").click();
      cy.intercept('GET', '**/api/user/username/testuser').as('getUserByUsername');
    });

    it('should not allow registration with a used username', () => {
      cy.visit("/register");
      const form = cy.get("form").within(() => {
        cy.get("input[name='username']").type("testuser"); // Assuming "testuser" is already taken
        cy.get("input[name=username]").should("have.value", "testuser");
        cy.get("button.next-step").should("not.be.disabled").click();

        cy.get("input[name='password']").type("JegHeterJacobLeinLittVanskeligPassord1");
        cy.get("input[name=password]").should("have.value", "JegHeterJacobLeinLittVanskeligPassord1");

        cy.get("button.next-step").should("not.be.disabled").click();
        cy.get("input[type='file']").should("exist");
        cy.get('[data-cy="drop-zone"]')
          .selectFile('cypress/fixtures/testImage.png', {
            action: 'drag-drop',
            force: true
          });
        cy.get('input[name="firstname"]').type("Jacob");
        cy.get('input[name="firstname"]').should('have.value', 'Jacob');
        cy.get("input[name='lastname']").type("Lein");
        cy.get("input[name='lastname']").should('have.value', 'Lein');

        cy.get("button.next-step").should("not.be.disabled").click();
      });

      cy.get('.error-message')
        .should('exist')
        .and('contain', 'Username is already taken'); // Adjust the error message text to match your application
    });

  });

});
