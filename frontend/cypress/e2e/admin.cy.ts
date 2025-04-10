describe("Admin", () => {
  beforeEach(() => {
    cy.visit("/admin");
  });

  // while not logged in
  it("should not display the admin page but redirect to loginpage", () => {
    cy.get(".outer-wrapper .sidebar").should("not.exist");
    cy.get(".outer-wrapper .page").should("not.exist");
    cy.get(".outer-wrapper .login-form").should("exist");
  });

  // while logged in as not admin
  it("should not display the admin page but redirect to profilepage", () => {
    cy.visit("/login");
    cy.get("form").within(() => {
      cy.get("input[name='username']").type("testuser1");
      cy.get("#password").type("password1");
      cy.get("button[type='submit']").click();
    });
    cy.wait(1000);
    cy.get('.link.profile').click();
    cy.get(".username").should("contain.text", "@testuser1");
    cy.visit("/admin");
    cy.get(".outer-wrapper .sidebar").should("not.exist");
    cy.get(".outer-wrapper .page").should("not.exist");
    cy.get(".profile-header").should("exist");
  });

  describe("when logged in as admin", () => {
    beforeEach(() => {
      cy.visit("/login");
      cy.get("form").within(() => {
        cy.get("input[name='username']").type("testuser0");
        cy.get("#password").type("password0");
        cy.get("button[type='submit']").click();
      });
      cy.wait(1000);
      cy.get('.admin-link').click();
    });

    it("should display the admin page", () => {
      cy.get(".outer-wrapper .sidebar").should("exist");
      cy.get(".outer-wrapper .page").should("exist");
    });

    describe("when editing a category", () => {
      beforeEach(() => {
        cy.get(".categories .category-inner-wrapper .category-card-outer-wrapper").first().click();
      });

      it("should open a category when clicked", () => {
        cy.get(".p-dialog-header").should("exist");
      });

      it("should close the category when the close button is clicked", () => {
        cy.get(".p-dialog-close-button").click();
        cy.get(".p-dialog-header").should("not.exist");
      });

      describe("when creating a new subcategory", () => {
        beforeEach(() => {
          cy.get(".create-sub-category-button").click();
          cy.get(".p-dialog-header").should("have.length.greaterThan", 1);
        });

        it("should open a subcategory creation dialog", () => {
          cy.get(".p-dialog-header").should("have.length.greaterThan", 1);
        });

        it("should close the subcategory when the close button is clicked", () => {
          cy.get(".p-dialog-close-button").last().click();
          cy.get(".p-dialog-header").should("have.length", 1);
        });
      });
    });
  });
});
