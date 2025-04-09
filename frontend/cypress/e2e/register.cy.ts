

describe("register", () => {
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

    it("should be able to enter a username and password", () => {
        cy.visit("/register");
        const form = cy.get("form").should("exist");

        form.find("input[name=username]").type("testuser");
        cy.get("input[name=username]").should("have.value", "testuser");
    });

    it("should say that the username is available", () => {
        cy.visit("/register");
        const form = cy.get("form").should("exist");

        form.find("input[name=username]").type("testuser");
        cy.get("p[class*='username-is-avaiable']").should("exist");
    });
});