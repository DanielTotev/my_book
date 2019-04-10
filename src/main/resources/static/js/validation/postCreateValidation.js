$(() => {
    const title = $('#title');
    const image = $('#image');
    const createBtn = $('#postCreateBtn');

    validationUtils.validFormCheck([title,image], createBtn);

    title.on('input', () => {
        validationUtils.validateField(title, 'Invalid title', 'title_message', f => validationRules.titleValidator(f.val()));
        validationUtils.validFormCheck([title,image], createBtn);
    });
    image.on('input', () => {
        validationUtils.validateField(image, 'Invalid image', 'image_message', f => validationRules.imageValidator(f.val()));
        validationUtils.validFormCheck([title,image], createBtn);
    });
});