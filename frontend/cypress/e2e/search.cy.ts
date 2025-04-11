
describe("search", () => {
    it("should display a search bar and advanced search", () => {
        cy.intercept("GET", "/api/categories/**").as("getSubCategories");
        cy.visit("/search");
        cy.get(".search").should("exist");
        cy.wait("@getSubCategories");
        cy.get(".advanced-search-outer-wrapper").should("exist");
        cy.get(".no-sub-categories-text").should("exist");
    });

    it("should be able to select a category and subcategory", () => {
        cy.intercept("GET", "/api/categories").as("getCategories");
        cy.visit("/search");
        cy.wait("@getCategories");
        cy.get("[data-cy='collapsible-title']").click();
        cy.get(".categories").children().first().click();
        cy.url().should("include", "/search");
        cy.url().should("include", "category");
        cy.get(".sub-category-selector").should("exist").children().should("have.length.greaterThan", 0);
        cy.get(".sub-category-selector").children().first().click();
        cy.url().should("include", "/search");
        cy.url().should("include", "subCategoryId");
    });

    it("should be able to search for a specific listing", () => {
        cy.intercept("GET", "/api/categories").as("getCategories");
        cy.intercept("GET", "/api/search**").as("getSearch");
        cy.intercept("GET", "/api/listing/**").as("getListings");
        cy.visit("/search");
        cy.wait("@getCategories");
        cy.get(".search").type("Test Listing 0").type("{enter}");
        cy.wait("@getSearch");
        cy.wait("@getListings");
        cy.url().should("include", "/search");
        cy.url().should("include", "q=Test+Listing+0");
        cy.get(".search-results").should("exist").and("have.length.greaterThan", 0);
    });

});