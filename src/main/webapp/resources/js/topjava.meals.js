const mealUrl = "ajax/profile/meals/";

$(function () {
    makeEditable({
            ajaxUrl: mealUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "createdRow": function( row, data, dataIndex){
                    if( data.excess === true){
                        $(row).addClass('table-success');
                    } else {
                        $(row).addClass('table-danger');
                    }
                },
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

    $('#filter-but').click(function () {
        var ajaxUrl = 'ajax/profile/meals/filter?';
        var tmp = $('#startDate').val();
        if (tmp.trim()) {
            ajaxUrl += '&startDate=' + tmp;
        }
        tmp = $('#startTime').val();
        if (tmp.trim()) {
            ajaxUrl += '&startTime=' + tmp;
        }
        tmp = $('#endDate').val();
        if (tmp.trim()) {
            ajaxUrl += '&endDate=' + tmp;
        }
        tmp = $('#endTime').val();
        if (tmp.trim()) {
            ajaxUrl += '&endTime=' + tmp;
        }
        // console.log(context.ajax);
        context.ajaxUrl = ajaxUrl;
        updateTable();
    });

    $('#filterReset-but').click(function () {
        context.ajaxUrl = mealUrl;
        updateTable();
        $('#startDate').val('');
        $('#startTime').val('');
        $('#endDate').val('');
        $('#endTime').val('');
    });

});
