$(() => {
    const age = $('#age');
    const education = $('#education');
    const gender = $('#gender');
    // const birthday = $('#birthday');
    // const profilePicture = $('#profilePicture');
    const profileCreateBtn = $('#profileCreateBtn');

    validationUtils.validFormCheck([age, education, gender], profileCreateBtn);

    age.on('input', () => {
        validationUtils.validateField(age, 'Please enter a valid age', 'age_message', f => validationRules.notNullValidator(f.val()));
        validationUtils.validFormCheck([age, education, gender], profileCreateBtn);
    });

    education.on('input', () => {
        validationUtils.validateField(education, 'Please enter a valid education', 'education_message', f => validationRules.notNullValidator(f.val()));
        validationUtils.validFormCheck([age, education, gender], profileCreateBtn);
    });

    gender.on('input', () => {
        validationUtils.validateField(gender, 'Please enter a valid gender', 'gender_message', f => validationRules.notNullValidator(f.val()));
        validationUtils.validFormCheck([age, education, gender], profileCreateBtn);
    });

    // birthday.on('birthday', () => {
    //     validationUtils.validateField(birthday, 'Please enter a valid birthday', 'gender_message', f => validationRules.notNullValidator(f.val()));
    //     validationUtils.validFormCheck([age, education, gender, birthday], profileCreateBtn);
    // });
});