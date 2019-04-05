const validationRules = (() => {
    const emailValidator = val => validator.isEmail(val);

    const usernameValidator = val => !validator.isEmpty(val) && validator.isAlphanumeric(val);

    const passwordValidator = val => !validator.isEmpty(val);

    return {
        emailValidator,
        usernameValidator,
        passwordValidator
    };
})();