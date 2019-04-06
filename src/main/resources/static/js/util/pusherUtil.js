const pusher = (() => {
    Pusher.logToConsole = true;

    const pusher = new Pusher('d09510aa6ece601ef463', {
        cluster: 'eu',
        forceTLS: true
    });

    return pusher;
})();