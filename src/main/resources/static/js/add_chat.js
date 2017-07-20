function addChat(chatName) {
    var json = {};
    json["name"] = chatName;
    $.ajax({
        url: 'http://localhost:8080/chats',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json),
        headers: {
            "Auth-Token": getCookie("Auth-Token")
        }
    })
}

function toChatList() {
    window.location = "http://localhost:8080/chat_list.html";
}