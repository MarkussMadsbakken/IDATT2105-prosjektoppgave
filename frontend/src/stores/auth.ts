import { type RegisterResponse, type LoginResponse } from "@/types";
import type { Token } from "@/types/token";
import { jwtDecode } from "jwt-decode";
import { defineStore } from "pinia";

export const useAuth = defineStore("auth", {
    state: () => {
        // get token from session storage
        const rawToken = sessionStorage.getItem("token")

        // No toke, no user
        if (!rawToken) {
            return {
                loggedIn: false,
            }
        }

        // Decode the token
        let token: Token = jwtDecode(rawToken);

        // If the token is expired, log out
        if (token?.exp && token.exp < Date.now() / 1000) {
            sessionStorage.setItem("token", "");
            return {
                loggedIn: false,
            }
        }

        // Return user
        return {
            loggedIn: true,
            decodedToken: token,
            rawToken: rawToken
        }
    },
    getters: {
        isAdmin(state) {
            return state.decodedToken?.isAdmin ?? false;
        },
        userId(state) {
            return state.decodedToken?.userId;
        },
        username(state) {
            return state.decodedToken?.sub;
        },
        token(state) {
            return state.rawToken;
        }
    },
    actions: {
        /**
         *  Returns and checks if the user is logged in, and that the token is not expired
         */
        isLoggedIn() {
            // Check if the token is expired, and if so, log out
            if (this.decodedToken?.exp && this.decodedToken.exp < Date.now() / 1000) {
                this.logout();
                return false;
            }

            return this.loggedIn;

        },
        /**
         * Logs the user in by calling the login action
         *
         * @param loginAction  The login action to call
         * @returns  success  Whether the login was successful
         */
        async login(loginAction: () => Promise<LoginResponse>) {
            // Call the login action
            let res = await loginAction();

            // If the response does not contain a token, return an error
            // Should always contain a token, but whatever...
            if (!res.token) {
                return {
                    success: false,
                    reason: res.message
                }
            }

            // Set values
            this.rawToken = res.token;
            this.decodedToken = this.decodeToken(res.token);
            this.loggedIn = true;

            sessionStorage.setItem("token", res.token);

            return {
                success: true
            }
        },
        /**
         * Registers the user and logs them in
         *
         * @param registerAction  The register action to call
         * @returns success  Whether the registration was successful
         */
        async register(registerAction: () => Promise<RegisterResponse>) {
            // Call the register action
            let res = await registerAction();

            // If the response does not contain a token, return an error
            if (!res.token) {
                return {
                    success: false,
                    reason: res.message
                }
            }

            // Set values
            this.rawToken = res.token;
            this.decodedToken = this.decodeToken(res.token);
            this.loggedIn = true;
            sessionStorage.setItem("token", res.token);

            return {
                success: true
            }
        },
        decodeToken(token: string) {
            return jwtDecode(token) as Token;
        },
        /**
         * Logs the user out by clearing the token from session storage
         * and setting the loggedIn state to false
         */
        logout() {
            // Clear values
            sessionStorage.setItem("token", "");
            this.loggedIn = false;
            this.decodedToken = undefined;
            this.rawToken = undefined;
        },
        /**
         * Swaps the current token with a new one, and logs the user in again
         * @param token  The new token to swap with
         */
        swapToken(token: string) {
            this.logout();
            this.login(async () => {
                return {
                    token: token,
                    message: "Token swapped"
                }
            });
        }
    }
});

// Logout helper function
export function logout() {
    return useAuth().logout();
}