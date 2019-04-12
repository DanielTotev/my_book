$(() => {
    const title = $('#title');
    const createBtn = $('#postEditBtn');

    validationUtils.validFormCheck([title], createBtn);

    title.on('input', () => {
        validationUtils.validateField(title, 'Invalid title', 'title_message', f => validationRules.titleValidator(f.val()));
        validationUtils.validFormCheck([title], createBtn);
    });
});