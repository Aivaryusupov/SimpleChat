let chatId;

window.onload = connect();

function connect() {
    redirect();
    chatId = getUrlVars()['id'];
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({'Auth-Token': getCookie('Auth-Token')}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(
            '/topic/chats/' + chatId,
            function (messageOutput) {
                writeMessage(JSON.parse(messageOutput.body).from, JSON.parse(messageOutput.body).message);
            });
    });
}