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
        cy.url().should('include', '/profile/listings/create');

        cy.get("input[type='file']").should("exist");
        cy.get('[data-cy="drop-zone"]')
            .selectFile('cypress/fixtures/testImage.png', {
                action: 'drag-drop',
                force: true
            });
        cy.get('.success-message').should('exist');
        cy.get('input[name="title"]').type("Test listing");
        cy.get('input[name="title"]').should('have.value', 'Test listing');
        cy.get('textarea[name="description"]').type("This is a test listing");
        cy.get('textarea[name="description"]').should('have.value', 'This is a test listing');
        cy.get('input[name="price"]').type("1000");
        cy.get('input[name="price"]').should('have.value', '1000');

        cy.get('#select-position-button').click();
        cy.get('#map-selector').should('exist');
        cy.wait(500);

        cy.get('#address')
            .should('be.visible')
            .type('Eirik jarls gate 2');
        cy.get('#select-position-popup').should('exist').click();
        cy.get("#category").should("exist").click();
        cy.get('#pv_id_2_0').should('exist').click();
        cy.get('#Test\\ SubCategory\\ 0').check();
        cy.get('#Test\\ SubCategory\\ 0').should('be.checked');

        cy.get("#submit-button").should("not.be.disabled").click();
        cy.get('.listing-title').should('contain.text', 'Test listing');
    });
});


describe("Listing page", () => {
    it("should be able to go to a listing from the front page", () => {
        cy.intercept("GET", "/api/listing**").as("getListings");
        cy.visit("/");
        cy.wait("@getListings");
        cy.get(".recommended-listings").children().first().click();
        cy.url().should("include", "/listing");
    });
})