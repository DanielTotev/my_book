const validationUtils = (() => {

    const validateField = (field, message, messageId, validatorFunction) => {
        const messageToAppend = $(`<div id="${messageId}" class=" alert alert-danger">${message}</div>`);
        field.touched = true;

        console.log(validatorFunction);

        if(!validatorFunction(field) && $(`#${messageId}`).length === 0) {
            messageToAppend.insertAfter(field);
        }

        if(validatorFunction(field) &&  $(`#${messageId}`).length !== 0) {
            $(`#${messageId}`).remove();
        }
    };

    const validFormCheck = (fields, button) => {
        const untouchedFields = fields.filter(x => !x.touched);

        console.log(untouchedFields);

        if(untouchedFields.length === 0 && $('.alert-danger').length === 0) {
            button.attr('disabled', false);
        } else {
            button.attr('disabled', true);
        }
    };

    return {
        validateField,
        validFormCheck
    };
})();