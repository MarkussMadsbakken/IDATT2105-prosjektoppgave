describe("User Edit", () => {
  beforeEach(() => {
    cy.visit("/login");
    cy.intercept("POST", "/api/auth/login").as("loginRequest");
    cy.intercept('GET', '/api/user/2').as('getUser');
    cy.intercept('POST', '/api/auth/change-credentials').as('updateUser');

    cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser1");
      cy.get("#password").type("password1");
      cy.get("button[type='submit']").click();
    });

    cy.wait("@loginRequest");
    cy.url().should("include", "/");
    cy.visit('/profile');
    cy.wait('@getUser');
    cy.visit('/profile/edit')
  });

  it('should be able to edit picture and first- and last name', () => {
    cy.get("input[type='file']").should("exist");
    cy.get('[data-cy="drop-zone"]')
      .selectFile('cypress/fixtures/testImage2.png', {
        action: 'drag-drop',
        force: true
      });
    cy.get('.success-message').should('exist');
    cy.get('#firstName').type("Jakop");
    cy.get('#firstName').should('have.value', 'Jakop');
    cy.get("#lastName").type("Klein");
    cy.get("#lastName").should('have.value', 'Klein');
    cy.get('[data-cy="save-button"]').click();
    cy.intercept('PUT', '/api/user/update').as('updateUser');
    cy.get('.name').should('contain.text', 'Jakop Klein');
  });

  it('should be able to change username and password', () => {
    cy.get('.edit-password-button').click();
    cy.get('input#userName').clear().type('jacoblein');
    cy.get('input[type="password"]#currentPassword').type('password1');
    cy.get('input[type="password"]#newPassword').type('mittpassord3');
    cy.get('input[type="password"]#repeatPassword').type('mittpassord3');
    cy.get('.save-password-button').click();
    cy.wait('@updateUser');

    cy.get('.logout-button').click();
    cy.get("form").within(() => {
      cy.get("input[name='username']").type("jacoblein");
      cy.get("#password").type("mittpassord3");
      cy.get("button[type='submit']").click();
    });
    cy.wait('@loginRequest');
    cy.url().should("include", "/");
    cy.visit('/profile');
    cy.wait('@getUser');
    cy.get(".username").should("contain.text", "@jacoblein");
  })


});
