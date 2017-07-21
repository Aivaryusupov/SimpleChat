let chatId;
let token;

window.onload = connect();

function connect() {
    chatId = getUrlVars()['id'];
    websocket = new WebSocket("ws://localhost:8080/authHandler");
    websocket.onopen = function (evt) {
        token = getCookie("Auth-Token");
        if (typeof websocket !== 'undefined') {
            websocket.send(token + " " + chatId + " " + $('#message').val());
        } else {
            alert("Not connected.");
        }
    };

    websocket.onmessage = function (evt) {
        writeMessage(JSON.parse(evt.data).from, JSON.parse(evt.data).message);
    };
}

function disconnect() {
    if (typeof websocket !== 'undefined') {
        websocket.close();
        websocket = undefined;
    } else {
        alert("Not connected.");
    }
}
