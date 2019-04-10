const validationRules = (() => {
    const emailValidator = val => validator.isEmail(val);

    const usernameValidator = val => !validator.isEmpty(val) && validator.isAlphanumeric(val);

    const passwordValidator = val => !validator.isEmpty(val);

    const titleValidator = val => val !== null && !validator.isEmpty(val);

    const imageValidator = val => val !== null && !validator.isEmpty(val);

    return {
        emailValidator,
        usernameValidator,
        passwordValidator,
        titleValidator,
        imageValidator
    };
})();