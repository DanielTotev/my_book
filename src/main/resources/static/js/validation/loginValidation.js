$(() => {
    const username = $('#username');
    const password = $('#password');
    const loginButton = $('#loginButton');

    validationUtils.validFormCheck([username,password], loginButton);

    username.on('input', () => {
        validationUtils.validateField(username, 'Invalid username', 'username_message', f => validationRules.usernameValidator(f.val()));
        validationUtils.validFormCheck([username,password], loginButton);
    });
    password.on('input', () => {
        validationUtils.validateField(password, 'Invalid password', 'password_message', f => validationRules.passwordValidator(f.val()));
        validationUtils.validFormCheck([username,password], loginButton);
    });
});