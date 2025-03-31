
describe("index", () => {
    it("should display the some listings", () => {
        cy.visit("/");
        cy.get(".recommended-listings").should("exist");
    });
});