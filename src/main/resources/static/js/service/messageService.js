const messageService = (() => {
    const getMessagesByChatRoomId = id => http.doGet(`/api/messages?chatId=${id}`);

    return {
        getMessagesByChatRoomId
    }
})();