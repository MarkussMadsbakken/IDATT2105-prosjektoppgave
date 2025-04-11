
describe("index", () => {
    it("should display a search bar, categories", () => {
        cy.intercept("GET", "/api/categories").as("getCategories");
        cy.visit("/");
        cy.get(".search").should("exist");
        cy.wait("@getCategories");
        cy.get(".categories").children().should("have.length.greaterThan", 0);
    });

    it("should display a some listings", () => {
        cy.intercept("GET", "/api/listing**").as("getListings");
        cy.visit("/");
        cy.wait("@getListings");
        cy.get(".recommended-listings").children().should("have.length.greaterThan", 0);
    });

    it("should be able to go to seach cia clicking on a category", () => {
        cy.intercept("GET", "/api/categories").as("getCategories");
        cy.visit("/");
        cy.wait("@getCategories");
        cy.get(".categories").children().first().click();
        cy.url().should("include", "/search");
        cy.url().should("include", "category");
    });

    it("should be able to search", () => {
        cy.intercept("GET", "/api/categories").as("getCategories");
        cy.visit("/");
        cy.wait("@getCategories");
        cy.get(".search").type("test").type("{enter}");
        cy.url().should("include", "/search");
        cy.url().should("include", "q=test");
    });
});