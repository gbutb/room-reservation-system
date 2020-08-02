/**
 * Autocompletes time input fields when one of
 * them is changed and the other one is empty.
 */
$(function () {
    $('#fromTime').change(function () {
        var fromTime = $('#fromTime').val();
        var toTime = $('#toTime').val();

        if (toTime.length === 0) {
            document.getElementById("toTime").value = fromTime;
        }
    });

    $('#toTime').change(function () {
        var fromTime = $('#fromTime').val();
        var toTime = $('#toTime').val();

        if (fromTime.length === 0) {
            document.getElementById("fromTime").value = toTime;
        }
    });
});

/**
 * Validates input time range of given form. Alerts the user
 * if inputs are invalid or submits the form successfully.
 */
function checkTime() {
    var fromTime = $('#fromTime').val();
    var toTime = $('#toTime').val();

    if (fromTime.length === 0 && toTime.length === 0) {
        document.getElementById("timeRangeForm").submit();
    } else {
        var fromTimeDate = Date.parse('09/02/2020 ' + fromTime);
        var toTimeDate = Date.parse('09/02/2020 ' + toTime);

        if (fromTimeDate >= Date.parse('09/02/2020 00:00') && fromTimeDate < Date.parse('09/02/2020 09:00')) {
            fromTimeDate = Date.parse('10/02/2020 ' + fromTime);
        }

        if (toTimeDate >= Date.parse('09/02/2020 00:00') && toTimeDate <= Date.parse('09/02/2020 09:00')) {
            toTimeDate = Date.parse('10/02/2020 ' + toTime);
        }

        if (toTimeDate < fromTimeDate) {
            $('#timeInputsModal').modal();
        } else {
            document.getElementById("timeRangeForm").submit();
        }
    }
}

/**
 * Validates input time range of given form. Alerts the user
 * if inputs are invalid or submits the form successfully.
 * Does nothing if time inputs are empty.
 */
function checkReservationTime() {
    var fromTime = $('#fromTime').val();
    var toTime = $('#toTime').val();

    if (fromTime.length !== 0 && toTime.length !== 0) {
        var fromTimeDate = Date.parse('09/02/2020 ' + fromTime);
        var toTimeDate = Date.parse('09/02/2020 ' + toTime);

        if (fromTimeDate >= Date.parse('09/02/2020 00:00') && fromTimeDate < Date.parse('09/02/2020 09:00')) {
            fromTimeDate = Date.parse('10/02/2020 ' + fromTime);
        }

        if (toTimeDate >= Date.parse('09/02/2020 00:00') && toTimeDate <= Date.parse('09/02/2020 09:00')) {
            toTimeDate = Date.parse('10/02/2020 ' + toTime);
        }

        if (toTimeDate < fromTimeDate) {
            $('#timeInputsModal').modal();
        } else {
            document.getElementById("timeRangeForm").submit();
        }
    }
}
