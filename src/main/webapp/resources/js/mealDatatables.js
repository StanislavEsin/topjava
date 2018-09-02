const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 16).replace("T", " ");
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).attr("data-mealExceed", data.exceed);
        },
        "initComplete": makeEditable
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
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