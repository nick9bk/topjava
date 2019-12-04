$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
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
                        "data": "excess"
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
                            return '<button class="btn btn-primary btn-small delete" id = ' + full.id +'>Delete</button>';
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