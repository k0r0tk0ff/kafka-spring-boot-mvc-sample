console.log("Script \"misc.js\" has been success loaded...");

let CONFIG = (function() {
    let constants = {
        'SEND_MESSAGE_URL': '/sendMessage',
        'ANOTHER_CONST': '2'
    };

    return {
        get: function(name) { return constants[name]; }
    };
})();

function test() {
    const dataToSend = JSON.stringify({"key": "asdf", "messageBody": "101010"});

    //fetch("/sendMessage", {
    fetch(CONFIG.get('SEND_MESSAGE_URL'), {
        method: "POST",
        body: dataToSend,
        headers:{"content-type": "application/json"}
    })
        .then( (response) => {
            if (response.status !== 200) {
                return Promise.reject();
            }
            return response.text()
        })
        .then(i => console.log(i))
        .catch(() => console.log('ошибка'));
}

document.getElementById('sendMessage').addEventListener('submit', submitForm);
function submitForm(event) {
    // Отменяем стандартное поведение браузера с отправкой формы
    event.preventDefault();
    let formData = new FormData(event.target);
    // formData.forEach(($$value, $$key) => console.log("key: " + $$key + " value: " + $$value));

    // Собираем данные формы в объект
    let obj = {};
    formData.forEach((value, key) => obj[key] = value);

    // Собираем запрос к серверу
    let request = new Request(CONFIG.get('SEND_MESSAGE_URL'), {
        method: 'POST',
        body: JSON.stringify(obj),
        headers: {
            'Content-Type': 'application/json',
        },
    });

    // Отправляем (асинхронно!)
    fetch(request)
        //Обрабатываем ответ
        .then(response => parseResponseAndLogAnswerMessage(response))
        .catch(error => console.error(error));

    //Alternative variant
   /* fetch(request).then(
        function(response) {
            // Запрос успешно выполнен
            parseResponseAndLogAnswerMessage(response);
        },
        function(error) {
            // Запрос не получилось отправить
            console.error(error);
        }
    );*/
    // console.log('Запрос отправился успешно');
}

//Пример взят из комментов к https://learn.javascript.ru/fetch-progress
function parseResponseAndLogAnswerMessage(response) {
    (async () => {
        //let response = await fetch('https://jsonplaceholder.typicode.com/photos');
        const reader = response.body.getReader();

        const contentLength = +response.headers.get('Content-Length');

        let receivedLength = 0;
        let chunks = [];

        while(1) {
            const {done, value} = await reader.read();
            if (done) {
                break;
            }

            chunks.push(value);
            receivedLength += value.length;

            console.log(`Получено ${receivedLength} из ${contentLength}`)
        }

        let chunksAll = new Uint8Array(receivedLength);
        let position = 0;
        for (let chunk of chunks) {
            chunksAll.set(chunk, position);
            position += chunk.length;
        }

        //Convert all chunks to String
        let result = new TextDecoder('utf-8').decode(chunksAll);

        // Realization for json
        //let jsonResult = JSON.parse(result);
        console.log("Answer message: "+ result);
    })();
}
