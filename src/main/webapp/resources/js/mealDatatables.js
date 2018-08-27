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
                "asc"
            ]
        ]
    });
    makeEditable();

    jQuery('#dateTime').datetimepicker();
});

function saveMeal() {
    const formAction = $("#detailsForm").attr("action");
    const formData = {
        "dateTime": new Date($("#dateTime")[0].value),
        "description": $("#description")[0].value,
        "calories": $("#calories")[0].value
    };

    $.ajax({
        type: "POST",
        url: formAction,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formData),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
    });
}