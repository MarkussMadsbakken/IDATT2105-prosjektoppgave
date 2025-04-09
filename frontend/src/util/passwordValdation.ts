
/**
 * Cheks if a password is valid and can be used to register a new user.
 * @param password  The password to be validated.
 * @returns   An object containing a boolean indicating if the password is valid and an error message if it is not.
 */
export const validatePassword = (password: string): { valid: boolean, error: string } => {
    if (!password) {
        return { valid: false, error: "passwordCannotBeEmpty" };
    }

    // Check if password is at least 8 characters long
    if (password.length < 8) {
        return { valid: false, error: "passwordTooShort" };
    }

    // Check if password contains at at least one letter
    if (!/[a-zA-Z]/.test(password)) {
        return { valid: false, error: "passwordMustContainLetter" };
    }

    // Check if password contains at least one number
    if (!/\d/.test(password)) {
        return { valid: false, error: "passwordMustContainNumber" };
    }

    return { valid: true, error: "" };
}