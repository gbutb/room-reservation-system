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

    var now = new Date();
    var alteredTime = now.getHours() + now.getMinutes() / 60.0;
    if (alteredTime < 9) alteredTime += 24;
    var from = parseFloat(fromTime.substr(0, 2));
    from += parseFloat(fromTime.substr(3, 2)) / 60.0;
    if (from < 9) from += 24;

    if (alteredTime > from) {
        $('#timeInputsModal').modal();
    } else if (fromTime.length === 0 && toTime.length === 0) {
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

function hasReservations() {
    var success = false;
    $.ajax({
        url: "/has_reservations",
        type: 'GET',
        async: false,
        success: function(res) {
            success = (res == "true") ?
                true : false;
        }
    });
    return success;
}

/**
 * Validates input time range of given form. Alerts the user
 * if inputs are invalid or submits the form successfully.
 * Does nothing if time inputs are empty.
 */
function checkReservationTime() {
    var fromTime = $('#fromTime').val();
    var toTime = $('#toTime').val();

    if (checkMinRange(fromTime, toTime)) {
        $('#minRangeModal').modal();
        return;
    }

    if (hasReservations()) {
        $('#hasReservationsModal').modal();
        return;
    }

    var now = new Date();
    var alteredTime = now.getHours() + now.getMinutes() / 60.0;
    if (alteredTime < 9) alteredTime += 24;
    var from = parseFloat(fromTime.substr(0, 2));
    from += parseFloat(fromTime.substr(3, 2)) / 60.0;
    if (from < 9) from += 24;

    if (alteredTime > from) {
        $('#timeInputsModal').modal();
    } else if (fromTime.length !== 0 && toTime.length !== 0) {
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
 * Checks gap between two time inputs.
 */
function checkMinRange(fromTime, toTime) {
    var fromHours = parseInt(fromTime.substr(0, 2));
    var fromMinutes = parseInt(fromTime.substr(3, 2));

    var toHours = parseInt(toTime.substr(0, 2));
    var toMinutes = parseInt(toTime.substr(3, 2));

    if (fromHours < 9) fromHours += 24;
    if (toHours <= 9) toHours += 24;

    if (fromHours === toHours && (toMinutes - fromMinutes) < 20) {
        return true;
    } else if ((toHours - fromHours) > 2 ||
        ((toHours - fromHours) === 2 && toMinutes > fromMinutes)) {
        return true;
    }

    return false;
}
