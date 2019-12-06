// $(document).ready(function () {
const userUrl = "ajax/admin/users/";
$(function () {
    makeEditable({
            ajaxUrl: userUrl,
            datatableApi: $("#datatable").DataTable({
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
                        "render": function ( data, type, full, meta ) {
                            return '<input type="checkbox" class="checkbox" onclick="enableUser(this, ' + full.id + ')"' + (full.enabled === true ? ' checked' : '') + ' />';
                        }
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "orderable": false,
                        "render": function ( data, type, full, meta ) {
                            return '<button class="btn btn-primary btn-small edit" id = ' + full.id +'>Edit</button>';
                        }
                    },
                    {
                        "orderable": false,
                        "render": function ( data, type, full, meta ) {
                            return '<button class="btn btn-primary btn-small delete" onclick="deleteRow(' + full.id +')">Delete</button>';
                        }
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
    updateTable();
});

function enableUser(box, id) {
    $.get(userUrl + 'enabled/' + id, {enable : $(box).prop("checked")});
}