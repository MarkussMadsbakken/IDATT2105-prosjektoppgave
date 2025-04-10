describe("home", () => {
  // When logged out
  it("should display listings on the home page", () => {
    cy.visit("/");
    cy.get(".header-title").should("exist");
    cy.get(".recommended-listings .outer-wrapper").should("have.length.greaterThan", 2);
  });

  describe("when logged in", () => {
    beforeEach(() => {
      cy.visit("/login");
      cy.get("form").within(() => {
        cy.get("input[name='username']").type("testuser1");
        cy.get("#password").type("password1");
        cy.get("button[type='submit']").click();
      });
      cy.wait(1000);
      cy.get('.link.profile').click();
      cy.get(".username").should("contain.text", "@testuser1");
    });

    it("should display recommended listings on the home page", () => {
      cy.visit("/");
      cy.get(".header-title").should("exist");
      cy.get(".recommended-listings .outer-wrapper").should("have.length.greaterThan", 0);
    });
  });
});
