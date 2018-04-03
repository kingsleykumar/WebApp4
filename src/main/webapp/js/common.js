/**
 * Created by Kingsley Kumar on 24/09/2016.
 */

// function getTodayDate() {
//     var today = new Date();
//
//     return getFormattedDate(today);
// }

function getFormattedDate(date, format) {

    var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!
    var yyyy = date.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    return getDateStringBasedOnUserSelectedPattern(dd, mm, yyyy, format);
}

function getTodayDate(format) {

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    return getDateStringBasedOnUserSelectedPattern(dd, mm, yyyy, format);
}

function getDateStringBasedOnUserSelectedPattern(dd, mm, yyyy, format) {

    if (format == "dd.mm.yyyy") {

        return dd + '.' + mm + '.' + yyyy;

    } else if (format == "dd-mm-yyyy") {

        return dd + '-' + mm + '-' + yyyy;

    } else if (format == "yyyy-mm-dd") {

        return yyyy + '-' + mm + '-' + dd;

    } else if (format == "yyyy.mm.dd") {

        return yyyy + '.' + mm + '.' + dd;

    } else if (format == "mm/dd/yyyy") {

        return mm + '/' + dd + '/' + yyyy;
    }

    return dd + '/' + mm + '/' + yyyy;
}

function limitTextArea(limitField, limitCount, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    } else {
        limitCount.value = limitNum - limitField.value.length;
    }
}

function limitTextField(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}


function validateNumericInput(e, limitField, limitNum) {

    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    } else {

        if (e.keyCode == 190) { // allow only one dot.
            var inputArr = limitField.value.split('');
            var dotCount = 0;

            for (var i = 0; i < inputArr.length; i++) {
                var char = inputArr[i];
                if (char == ".") {
                    dotCount++;
                }
            }

            if (dotCount >= 1) {
//                        limitField.value = limitField.value.substring(0, limitNum);
                e.preventDefault();
                return;
            }
        }

        // console.log("e.keyCode === " + e.keyCode);

        // Allow: backspace, delete, tab, escape, enter, - and . (109, 189 are code for - sign)
        if ($.inArray(e.keyCode, [109, 189, 46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
            // Allow: Ctrl+C
            (e.keyCode == 67 && e.ctrlKey === true) ||
            // Allow: Ctrl+X
            (e.keyCode == 88 && e.ctrlKey === true) ||
            // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
            // let it happen, don't do anything
            return;
        }

        if (e.keyCode == 86 && e.ctrlKey === true) { //Allow: Ctrl+V, Validate and remove characters other than number and dot

            limitField.value = limitField.value.replace(/[^0-9\.]/g, '');

            return;
        }

        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    }
}

function getTextInputElement(idNamePrefix, rowIndex, value, inputcharlimit, style) {

    var col = document.createElement('td'); // create second column node

    var element = document.createElement("input");
    element.type = "text";
    element.id = idNamePrefix + rowIndex;
    element.name = idNamePrefix + rowIndex;
    // element.size = inputcharlimit;
    element.maxLength = inputcharlimit;
    element.value = value;
    element.style = style;
    element.onkeydown = function () {
        limitTextField(element, inputcharlimit);
    };
    element.onkeyup = function () {
        limitTextField(element, inputcharlimit);
    };

    col.appendChild(element);

    return col;
}

function getTextNumericInputElement(idNamePrefix, rowIndex, value, inputcharlimit, style) {

    var col = document.createElement('td'); // create second column node

    var element = document.createElement("input");
    element.type = "text";
    element.id = idNamePrefix + rowIndex;
    element.name = idNamePrefix + rowIndex;
//            element.size = inputcharlimit;
    element.maxLength = inputcharlimit;
    element.value = value;
    element.style = style;
    element.onkeydown = function () {
        validateNumericInput(event, element, inputcharlimit);
    };
    element.onkeyup = function () {
        validateNumericInput(event, element, inputcharlimit);
    };

    col.appendChild(element);

    return col;
}
