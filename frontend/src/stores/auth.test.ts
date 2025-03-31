import { createPinia, setActivePinia } from "pinia";
import { beforeEach, describe, expect, test } from "vitest";
import { useAuth } from "./auth";
import type { LoginResponse, User } from "@/types";

describe("calcstore", () => {
    beforeEach(() => {
        setActivePinia(createPinia());
    });

    test("default state", () => {
        const store = useAuth();
        expect(store.loggedIn).toBe(false);
        expect(store.decodedToken).toBe(undefined);
        expect(store.rawToken).toBe(undefined);
        expect(store.isAdmin).toBe(false);
    });

    test("log in", async () => {
        const store = useAuth();
        await store.login(async () => {
            return {
                token: "eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjpmYWxzZSwidXNlcklkIjo5LCJyZWZyZXNoVG9rZW4iOiJwbGFjZWhvbGRlcjogcmVmcmVzaHRva2VuIiwic3ViIjoiYSIsImlhdCI6MTc0MzQwOTYxMywiZXhwIjoxNzQzNDA5OTEzfQ.9tjrVmnNK_u8GNcgtlcR9Y_jQNnokCxOveXHXNTvKWE",
                message: "Login successful",
            };
        });
        expect(store.loggedIn).toBe(true);
        expect(store.isAdmin).toBe(false);
        expect(store.userId).toBe(9);
        expect(store.username).toBe("a");
    });

    test("register", async () => {
        const store = useAuth();
        await store.register(async () => {
            return {
                token: "eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjpmYWxzZSwidXNlcklkIjo5LCJyZWZyZXNoVG9rZW4iOiJwbGFjZWhvbGRlcjogcmVmcmVzaHRva2VuIiwic3ViIjoiYSIsImlhdCI6MTc0MzQwOTYxMywiZXhwIjoxNzQzNDA5OTEzfQ.9tjrVmnNK_u8GNcgtlcR9Y_jQNnokCxOveXHXNTvKWE",
                message: "Register successful",
            };
        });
        expect(store.loggedIn).toBe(true);
        expect(store.isAdmin).toBe(false);
        expect(store.userId).toBe(9);
        expect(store.username).toBe("a");
    });

    test("log out", async () => {
        const store = useAuth();
        await store.login(async () => {
            return {
                token: "eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjpmYWxzZSwidXNlcklkIjo5LCJyZWZyZXNoVG9rZW4iOiJwbGFjZWhvbGRlcjogcmVmcmVzaHRva2VuIiwic3ViIjoiYSIsImlhdCI6MTc0MzQwOTYxMywiZXhwIjoxNzQzNDA5OTEzfQ.9tjrVmnNK_u8GNcgtlcR9Y_jQNnokCxOveXHXNTvKWE",
                message: "Login successful",
            };
        });
        store.logout();
        expect(store.loggedIn).toBe(false);
        expect(store.isAdmin).toBe(false);
        expect(store.userId).toBe(undefined);
        expect(store.username).toBe(undefined);
        expect(store.rawToken).toBe(undefined);
        expect(store.decodedToken).toBe(undefined);
    });

    test("expired token", async () => {
        const store = useAuth();
        await store.login(async () => {
            return {
                token: "eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjpmYWxzZSwidXNlcklkIjo5LCJyZWZyZXNoVG9rZW4iOiJwbGFjZWhvbGRlcjogcmVmcmVzaHRva2VuIiwic3ViIjoiYSIsImlhdCI6MTc0MzQwOTYxMywiZXhwIjoxNzQzNDA5OTEzfQ.9tjrVmnNK_u8GNcgtlcR9Y_jQNnokCxOveXHXNTvKWE",
                message: "Login successful",
            };
        });
        expect(store.isLoggedIn()).toBe(false);
    });

});