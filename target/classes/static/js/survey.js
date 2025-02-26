function setStartdateAndLimit() {
    var date = document.getElementById('startdate');
    var today = new Date();

    var todayDate = getFormattedDate(today);

    // limit startdate
    var maxDate = new Date();
    maxDate.setDate(today.getDate() + 730);

    var maxDay = String(maxDate.getDate()).padStart(2, '0');
    var maxMonth = String(maxDate.getMonth() + 1).padStart(2, '0');
    var maxYear = maxDate.getFullYear();

    date.min = todayDate;
    date.max = maxYear + '-' + maxMonth + '-' + maxDay;
}

function setEnddateAndLimit() {
    var date = document.getElementById('enddate');
    var today = new Date();

    var todayDate = getFormattedDate(today);

    // limit enddate
    var maxEndDate = new Date(today);
    maxEndDate.setFullYear(today.getFullYear() + 2);
    var maxEndDateString = getFormattedDate(maxEndDate);

    date.min = todayDate;
    date.max = maxEndDateString;
}

// function for formatting dates
function getFormattedDate(date) {
    var day = String(date.getDate()).padStart(2, '0');
    var month = String(date.getMonth() + 1).padStart(2, '0');
    var year = date.getFullYear();
    return year + '-' + month + '-' + day;
}

// this code checks upon submission and date changes whether the end date is greater than the start date
document.addEventListener('DOMContentLoaded', function () {
    validateDates();

    document.getElementById('startdate').addEventListener('change', validateDates);
    document.getElementById('enddate').addEventListener('change', validateDates);

    document.getElementById('addSurveyForm').addEventListener('submit', function (event) {
        validateDates();
        if (document.getElementById('error-message').textContent) {
            event.preventDefault();
        }
    });
});

function validateDates() {
    var startdateInput = document.getElementById('startdate');
    var enddateInput = document.getElementById('enddate');
    var errorMessage = document.getElementById('error-message');

    var startdate = new Date(startdateInput.value);
    var enddate = new Date(enddateInput.value);

    if (startdate > enddate) {
        errorMessage.textContent = "Das Startdatum darf nicht größer als das Enddatum sein.";
        return false;
    } else {
        errorMessage.textContent = "";
        return true;
    }
}