const http = (() => {
    const doRequest = (route, method, body, headers) => {
        const requestParams = {
            method,
            headers,
            body
        };

        return fetch(route, requestParams)
            .then(res => res.json());
    };

    const doGet = (route, headers) => doRequest(route, 'GET', null, headers);

    const doPost = (route, headers, body) => doRequest(route, 'POST', body, headers);

    return {
        doRequest,
        doGet,
        doPost
    }
})();