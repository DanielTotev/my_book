$(() => {
    const username = $('#username');
    const email = $('#email');
    const password = $('#password');
    const confirmPassword = $('#confirmPassword');
    const registerButton = $('#regButton');

    const confirmPasswordValidator = () =>{
        console.log(password.val());
        console.log(confirmPassword.val());
        return password.val() === confirmPassword.val();
    };

    validationUtils.validFormCheck([username, email, password, confirmPassword], registerButton);

    email.on('input', () => {
        validationUtils.validateField(email, 'Please enter a valid email', 'email_message', f => validationRules.emailValidator(f.val()));
        validationUtils.validFormCheck([username, email, password, confirmPassword], registerButton);
    });
    username.on('input', () => {
        validationUtils.validateField(username, 'Invalid username', 'username_message', f => validationRules.usernameValidator(f.val()));
        validationUtils.validFormCheck([username, email, password, confirmPassword], registerButton);
    });
    password.on('input', () => {
        validationUtils.validateField(password, 'Invalid password', 'password_message', f => validationRules.passwordValidator(f.val()));
        validationUtils.validFormCheck([username, email, password, confirmPassword], registerButton);
    });
    confirmPassword.on('input', () => {
        validationUtils.validateField(confirmPassword, 'Both passwords should match', 'confirm_password_message', confirmPasswordValidator)
        validationUtils.validFormCheck([username, email, password, confirmPassword], registerButton);
    });
});