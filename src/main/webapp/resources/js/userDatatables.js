var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
});

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

function save() {
    const form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: saveCallback
    });
}

function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled,
        success: function () {
            checkbox.closest("tr")[0].dataset.userenabled = enabled;
            successNoty(enabled ? "Enabled" : "Disabled");
        },
        error: function () {
            $(checkbox).prop("checked", !enabled);
        }
    });
}