function shareFile(login, file) {
    navigator.clipboard.writeText("http://localhost:8080/request/" + login + "/" + file).then(function() {
        alert("Ссылка на файл успешно скопирована в буфер обмена");
    }, function(err) {
        console.error("Произошла ошибка при копировании текста: ", err);
    });
}

