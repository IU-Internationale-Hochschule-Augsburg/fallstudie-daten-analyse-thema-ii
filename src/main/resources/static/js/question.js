// function to hide the +Answer button and remember the question types
// function to prepopulate the input fields with 'radiobutton'
window.onload = function () {
    updateQuestionType('radiobutton');
}

// function for the cancel button
function goBack() {
    window.history.back();
}

function updateQuestionType(qType) {
    // to remember radio buttons by default
    var answerOptionsDiv = document.getElementById('answerOptions');
    document.getElementById('questionType').value = document.querySelector('input[type=radio]:checked').value;

    // hide the answeroptions if the type is open text response. Otherwise, by default, display all 10 options
    if (qType === 'radiobutton' || qType === 'checkbox') {
        answerOptionsDiv.style.display = 'block';
        addRequiredFlag();
    } else {
        clearAnswerOptions();
        answerOptionsDiv.style.display = 'none';
        deleteRequiredFlag();
    }
}

function clearAnswerOptions() {
    const antwortOptionen = document.getElementById('answerOptions');
    const fragetypRadios = document.getElementsByName('questionType');

    let fragetypChecked = false;
    for (let i = 0; i < fragetypRadios.length; i++) {
        if (fragetypRadios[i].checked) {
            fragetypChecked = true;
            break;
        }
    }

    if (fragetypChecked) {
        antwortOptionen.innerHTML = '';
    } else {
        antwortOptionen.innerHTML = `
            <div id="answer1">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption1">Antwortoption 1: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption1" th:field="*{answerOption1}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer2">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption2">Antwortoption 2: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption2" th:field="*{answerOption2}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer3">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption3">Antwortoption 3: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption3" th:field="*{answerOption3}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer4">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption4">Antwortoption 4: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption4" th:field="*{answerOption4}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer5">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption5">Antwortoption 5: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption5" th:field="*{answerOption5}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer6">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption6">Antwortoption 6: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption6" th:field="*{answerOption6}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer7">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption7">Antwortoption 7: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption7" th:field="*{answerOption7}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>
            <div id="answer8">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption8">Antwortoption 8: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption8" th:field="*{answerOption8}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer9">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption9">Antwortoption 9: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption9" th:field="*{answerOption9}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>

            <div id="answer10">
                <div class="containerFlex" style="border-bottom: solid 1px black; padding-top: 2px; padding-bottom: 8px; padding-left: 10px; margin-bottom: 12px;">
                    <label style="width: 25%; display: flex; align-items: center" for="antwortOption10">Antwortoption 10: </label>

                    <!-- Input field -->
                    <div class="col-75">
                        <input class="textfeld" style="padding: 12px;height: 42px;" type="text" id="antwortOption10" th:field="*{answerOption10}"
                               placeholder="Geben Sie eine Antwortoption ein">
                    </div>
                </div>
            </div>
        `;
    }
}

function addRequiredFlag() {
    let questionTypeElement = document.getElementById('questionType');
    if (!questionTypeElement) {
        console.error('Element with id "questionType" not found.');
        return;
    }

    let questionType = questionTypeElement.value;

    if (questionType === "radiobutton" || questionType === "checkbox") {
        let antwortOption1 = document.getElementById('antwortOption1');
        let antwortOption2 = document.getElementById('antwortOption2');

        if (antwortOption1) {
            antwortOption1.setAttribute('required', 'true');
        }
        if (antwortOption2) {
            antwortOption2.setAttribute('required', 'true');
        }
    }
}

function deleteRequiredFlag() {
    const questionTypeElement = document.getElementById('questionType');
    if (!questionTypeElement) {
        console.error('Element with id "questionType" not found.');
        return;
    }

    const questionType = questionTypeElement.value;

    if (questionType === "open text response") {
        const antwortOption1 = document.getElementById('antwortOption1');
        const antwortOption2 = document.getElementById('antwortOption2');

        if (antwortOption1) {
            antwortOption1.removeAttribute('required');
        }
        if (antwortOption2) {
            antwortOption2.removeAttribute('required');
        }
    }
}

function validateForm() {
    const fields = ["antwortOption1", "antwortOption2"];
    const title = document.getElementById('questionText').value;
    const trimmedTitle = title.trim();
    let questionTypeElement = document.getElementById('questionType').value;

    if (questionTypeElement === 'open text response') {
        if (trimmedTitle === "") {
            alert("Der Titel darf nicht nur Leerzeichen oder Tabs enthalten.");
            return false;
        }
    } else if (questionTypeElement === 'radiobutton' || questionTypeElement === 'checkbox') {
        for (let i = 0; i < fields.length; i++) {
            const input = document.getElementById(fields[i]);
            if (input) {
                const trimmedInput = input.value.trim();
                input.value = trimmedInput;
                if (trimmedInput === "" || trimmedTitle === "") {
                    alert("Das Eingabefeld darf nicht nur Leerzeichen oder Tabs enthalten.");
                    return false;
                }
            }
        }
    }

    return true;
}