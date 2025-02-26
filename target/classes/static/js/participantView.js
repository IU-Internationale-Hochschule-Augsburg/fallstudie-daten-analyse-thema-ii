document.addEventListener("DOMContentLoaded", function () {
    // When loading, all question types are initially hidden
    function hideAllQuestionTypes() {
        var questionTypes = document.querySelectorAll('.col-75');
        questionTypes.forEach(function (type) {
            type.style.display = "none";
        });
    }

    hideAllQuestionTypes();

    // This function displays the corresponding div based on the question type
    function showQuestionType() {
        hideAllQuestionTypes();
        var questionType = document.getElementById("questionType").value;

        if (questionType === 'checkbox') {
            document.getElementById("checkboxes").style.display = "block";
        } else if (questionType === 'radiobutton') {
            document.getElementById("radiobuttons").style.display = "block";
        } else if (questionType === 'open text response') {
            document.getElementById("openTextResponse").style.display = "block";
            // das will irgendwie nicht mehr
            //addRequiredAttribute('input field');
        }
    }

    showQuestionType();
});

// This function hides all answer options that the survey creator has not filled in for the checkbox question type
document.addEventListener("DOMContentLoaded", function () {
    for (var i = 1; i <= 10; i++) {
        var label = document.getElementById('label' + i);
        var content = label.textContent.trim();

        if (content === "") {
            /* If the answer option is not filled, it will be hidden, and an empty string will be written into it.
            The reason for this is that the variables for the checkbox answer options are initialized with 'N' by default. */
            label.style.display = "none";
            document.getElementById('checkboxAntwortOption' + i).value = ' ';
        } else {
            // If the answer option is filled, it will be displayed
            label.style.display = "block";
        }
    }
});

// This function hides all answer options that the survey creator has not filled in for the radiobutton question type
document.addEventListener("DOMContentLoaded", function () {
    for (var i = 1; i <= 10; i++) {
        var label = document.getElementById('radio' + i);
        var content = label.textContent.trim();

        if (content === "") {
            label.style.display = "none";
        } else {
            label.style.display = "block";
        }
    }
});

// This function writes a 'Y' for checked answer options and 'N' for unchecked available options
function handleCheckboxClick(optionId) {
    var checkbox = document.getElementById('checkbox' + optionId);
    var antwortOption = document.getElementById('checkboxAntwortOption' + optionId);

    if (checkbox.checked) {
        antwortOption.value = 'Y';
    } else {
        antwortOption.value = 'N';
    }
}

/* The function writes a 'Y' for Yes into an answer option if it has been selected. For radio buttons, all other available
answer options are filled with 'N' for No. Options that the Survey Creator has not filled are hidden and filled
with an empty string */
function handleRadioButtonClick(clickedIndex) {
    for (var i = 1; i <= 10; i++) {
        var input = document.getElementById('radiobuttonAntwortOption' + i);
        var label = document.getElementById('radio' + i);
        var text = label ? label.textContent : '';

        if (i === clickedIndex) {
            input.value = 'Y';
        } else {
            // here we distinguish again between an empty answer option and not checked
            if (text.trim() === '') {
                input.value = ' ';
            } else {
                input.value = 'N';
            }

        }
    }
}

// New Code: Code for mandatory questions and displaying tooltips
document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.getElementById('questionTypesForm');
    const submitButton = document.getElementById('submitButton');
    const tooltip = document.getElementById('tooltipDiv');
    const currentQuestionType = document.getElementById('questionType').value;

    submitButton.addEventListener('click', function () {
        let isValid = true;
        let message = '';

        // questionType = checkbox => Tooltip, if no input has been provided
        const checkboxQuestion = document.getElementById('checkboxes');
        const checkboxes = checkboxQuestion.querySelectorAll('input[type="checkbox"]');
        if (currentQuestionType === 'checkbox') {
            if (![...checkboxes].some(checkbox => checkbox.checked)) {
                isValid = false;
                alert('Bitte mindestens eine Checkbox auswählen.')
            }
        }

        // questionType = radiobutton => Tooltip, if no input has been provided
        const radiobuttonQuestion = document.getElementById('radiobuttons');
        const radioButtons = radiobuttonQuestion.querySelectorAll('input[type="radio"]');
        if (currentQuestionType === 'radiobutton') {
            if (![...radioButtons].some(radio => radio.checked)) {
                isValid = false;
                alert('Bitte eine Radiobutton-Option auswählen.')
            }
        }

        // questionType = open text response => Tooltip, if no input has been provided
        if (currentQuestionType === 'open text response') {
            const inputElement = document.getElementById('input field'); // ID des Eingabefelds
            if (inputElement) {
                inputElement.setAttribute('required', 'true');
                if (!inputElement.value.trim()) {
                    isValid = false;
                    alert('Bitte geben Sie eine Antwort ein.')
                }
            } else {
                console.error('Input field with id "input field" not found.');
            }
        }

        // submitting form
        if (isValid) {
            console.log("Formular ist gültig und wird übermittelt.");
            form.submit();
        } else {
            showTooltip(message, checkboxQuestion, radiobuttonQuestion);
        }
    });

    // function for displaying tool tips
    function showTooltip(message, submitButton) {
        tooltip.textContent = message;

        tooltip.style.display = 'block';
        tooltip.style.width = 'auto';
        tooltip.style.whiteSpace = 'nowrap';

        const submitButtonRect = submitButton.getBoundingClientRect();
        tooltip.style.top = `${submitButtonRect.bottom + window.scrollY + 5}px`;
        tooltip.style.left = `${submitButtonRect.left}px`;

        const tooltipWidth = tooltip.offsetWidth;
        tooltip.style.width = `${tooltipWidth}px`;

        setTimeout(() => {
            tooltip.style.display = 'none';
        }, 3000);
    }
});