function sendMessage(message) {
    var json = {};
    json["message"] = message.value;
    $.ajax({
        url: 'http://localhost:8080/chats/' + chatId + '/messages',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: {
            'Auth-Token': getCookie("Auth-Token")
        },
        data: JSON.stringify(json)
    })
    jQuery('#message').val('');
}

function writeMessage(from, message) {
    let select = document.getElementById('chatMessagesList');
    let messageOption = document.createElement('option');
    messageOption.value = 0;
    messageOption.innerHTML = from + " : " + message;
    select.appendChild(messageOption);
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
        vars[key] = value;
    });
    return vars;
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function getMessages() {
    var json = {};
    json["message"] = message.value;
    $.ajax({
        url: 'http://localhost:8080/chats/' + chatId + '/messages',
        type: 'get',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: {
            'Auth-Token': getCookie("Auth-Token")
        },
        data: JSON.stringify(json)
    })
    for (let i = 0; i < data.length; i++) {
        let select = document.getElementById('chatMessagesList');
        let messageOption = document.createElement('option');
        messageOption.value = 0;
        messageOption.innerHTML = data[i]["authorName"] + " : " + data[i]["text"];
        select.appendChild(messageOption);
    }
}