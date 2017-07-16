function registration(name, age, login, password) {
    var json = {};
    json["name"] = name;
    json["age"] = parseInt(age);
    json["login"] = login;
    json["password"] = password;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/users", true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(json));
    window.location = "http://localhost:8080/signin.html";
}
