function addInput() {
    var node = document.createElement("input");
    var br = document.createElement("br");
    node.type = "text";
    node.name = "option[]";
    node.placeholder = "Введите вариант ответа";
    document.getElementById("addButton").append(br);
    document.getElementById("addButton").append(br);
    document.getElementById("addButton").append(node);
}

function addQuestion(question, element) {
    var node = document.createElement("input");
    var br = document.createElement("br");
    node.type = "text";
    node.name = "option[]";
    node.placeholder = "Введите вариант ответа";
    node.value = question;
    element.appendChild(br);
    element.appendChild(br);
    element.appendChild(node);
}

function addQuestion(question, multiselect, answers) {
    var div = document.createElement("div");
    var question = document.createElement("input");
    var br = document.createElement("br");
    var checkbox = document.createElement("div");
    question.type = "text";
    question.name = "question[]";
    question.textContent = question;

    var multiselect = document.createElement("input");
    multiselect.name = "multiselect";
    multiselect.id = "multiselect";
    multiselect.className = "form-check-input mt-0";
    multiselect.type = "checkbox";
    multiselect.checked = multiselect;
    var multiselectLabel = document.createElement("label");
    multiselectLabel.value = "Множественный выбор";
    checkbox.append(multiselect);

    div.append(question);
    div.append(checkbox);
    var userAnswers = new Array(answers.length);
    for (let i = 0; i < answers.length; i++) {
        userAnswers[i] = addQuestion(answers[i].value, div);
    }

    document.getElementById("questionList").appendChild(br);
    document.getElementById("questionList").appendChild(br);
    document.getElementById("questionList").appendChild(div);

}
