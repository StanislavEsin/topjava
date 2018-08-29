const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i',
        formatTime:'H:i',
        formatDate:'Y-m-d'
    });

    const startDate = $('#startDate');
    const endDate = $('#endDate');

    startDate.datetimepicker({
        format: 'Y-m-d',
        onShow: function(ct) {
            this.setOptions({
                maxDate:endDate.val() ? endDate.val() : false
            })
        },
        timepicker: false
    });

    endDate.datetimepicker({
        format: 'Y-m-d',
        onShow:function(ct) {
            this.setOptions({
                minDate: startDate.val() ? startDate.val() : false
            })
        },
        timepicker: false
    });

    $('#startTime, #endTime').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
});

function save() {
    const form       = $("#detailsForm");
    const formMethod = form.attr("method");
    const formAction = form.attr("action");
    const formData = {
        "dateTime": new Date($("#dateTime")[0].value + ":00.000Z"),
        "description": $("#description")[0].value,
        "calories": $("#calories")[0].value
    };

    $.ajax({
        type: formMethod,
        url: formAction,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formData),
        success: saveCallback
    });
}

function updateTable() {
    const form = $("#filter");
    const formMethod = form.attr("method");
    const formAction = form.attr("action");

    $.ajax({
        type: formMethod,
        url: formAction,
        data: form.serialize(),
        success: updateTableByData
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}