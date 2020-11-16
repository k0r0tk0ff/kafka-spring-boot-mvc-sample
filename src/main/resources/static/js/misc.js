console.log('Script "misc.js" has been success loaded...');

//######################### Блок констант #####################################################
const CONFIGURATION = Object.freeze({
    SEND_MESSAGE_URL: "/sendMessage",
});

//##### Обертка, которая позволяет запускать listener после загрузки документа #################
// инфа по совету Владимира Епишева взята отсюда -
// https://developer.mozilla.org/en-US/docs/Web/API/Document/DOMContentLoaded_event
document.addEventListener('readystatechange', () => {
    console.log(`readystate: ${document.readyState}`);
});


document.addEventListener('DOMContentLoaded', () => {
    console.log('The DOMContent has been loaded...');

    //Назначаем обработчик отправки сообщений после того, как документ будет подгружен
    document.getElementById('sendMessage').addEventListener('submit', submitForm);
    console.log('The SendMessageListener has been loaded...');

    //Назначаем обработчик обновления страницы по нажатию кнопки после того, как документ будет подгружен
    window.addEventListener("load", () => {
        document.getElementById("reload").onclick = function() {
            location.reload(true);
        }
    });
});
//#############################################################################################

// Метод-обработчик событий по нажатию кнопки "Send Message"
function submitForm(event) {
    // Отменяем стандартное поведение браузера с отправкой формы
    event.preventDefault();
    let formData = new FormData(event.target);
    // formData.forEach(($$value, $$key) => console.log("key: " + $$key + " value: " + $$value));

    // Собираем данные формы в объект
    const obj = {};
    formData.forEach((value, key) => obj[key] = value);

    //Асинхронно отправляем запрос
    sendAsyncRequest(JSON.stringify(obj));
}

// Метод, осуществляющий асинхронную отправку сообщения
function sendAsyncRequest(sendObject) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", CONFIGURATION.SEND_MESSAGE_URL, true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');

    //Добавляем обработчик событий изменения readyState запроса
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            console.log("[Response status: [" + this.status + "]] [Answer message: [" + this.responseText + "]]");
        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status !== 200) {
            console.error("[Error! Response status: [" + this.status + "]] [Answer message: [" + this.responseText + "]]");
        }
    };

    console.log("Object for send: " + sendObject);
    xhr.send(sendObject);
}