/*$(document).ready(function () {
    $.getJSON("getall",function (data) {
        let tbl = $("#tablebody");
        $.each(data,function (i,item) {
            let tr = $("<tr>");
            let td = $("<td>");
            td.append($("<a>",{
                'href': "patient?patientId="+item.id,
                'text':item.lastName
            }));
            tr.append(td);
            tr.append($("<td>",{'text':item.firstName}));
            tr.append($("<td>",{'text':item.state}));
            tr.append($("<td>",{'text':item.age}));
            tr.append($("<td>",{'text':item.gender}));
            tbl.append(tr);
        })
    });
});*/
$(document).ready(function () {
    $.getJSON("getall",function (data) {
        let tbl = $("#tablebody");
        $.each(data,function (i,item) {
            let tr = $("<tr>");
            let td = $("<td>");
            td.append($("<a>",{
                'href': "patient/"+item.id,
                'text':item.lastName
            }));
            tr.append(td);
            tr.append($("<td>",{'text':item.firstName}));
            tr.append($("<td>",{'text':item.state}));
            tr.append($("<td>",{'text':item.age}));
            tr.append($("<td>",{'text':item.gender}));
            tbl.append(tr);
        })
    });
});
/*
$(document).ready(function () {
    var pageNumbers = 0;
    ajaxPost($("#sort option:selected").html(), 0);
    // *** как использовать функцию? а можно просто getAll из контроллера? ***
    getall();
    $("#searchP-form").submit(function (event) {
        event.preventDefault();
        getall();
        ajaxPost($("#sort option:selected").html(), 0 );
    });
    $("#pagination").on("click", "a", function (event) {
        event.preventDefault();
        ajaxPost($("#sort option:selected").html(), $(this).html()-1);
    });

    function getall() {
        var formData = {
            sort: "Last Name",
            page: 0
        };
        // DO POST
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "post",
            data: JSON.stringify(formData),
            url: "getallP",
            async: false,
            dataType: "json",
            success: function (response) {
                // var tbl = $("#tablebody");
                pageNumbers =Math.ceil(response.length/5);
                // вывод в консоль
                console.log(pageNumbers);

                var pagination = $("#pagination");
                pagination.find("li").remove();
                for (i=0; i<pageNumbers; i++){
                    var li =$("<li>");
                    li.append($("<a>", { 'href': "", 'text': i+1}));
                    pagination.append(li);
                }
            }
        });
    }

    function ajaxPost(sort, page) {
        console.log("POST!!");
        // PREPARE FORM DATA
        var formData = {
            sort: sort,
            page: page
        };
        console.log(formData);
        // DO POST
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "post",
            data: JSON.stringify(formData),
            url: "patientSort",
            async: false,
            dataType: "json",
            success: function (response) {
                var tbl = $("#tablebody");
                tbl.find("tr").remove();
                $.each(response, function (i, item) {
                    var tr = $("<tr>");
                    var td = $("<td>");
                    td.append($("<a>", {
                        'href': window.location + "patients/patient?patientId=" + item.id,
                        'text': item.lastName
                    }));
                    tr.append(td);
                    tr.append($("<td>", {'text': item.firstName}));
                    tr.append($("<td>", {'text': item.state}));
                    tr.append($("<td>", {'text': item.age}));
                    tr.append($("<td>", {'text': item.gender}));
                    tbl.append(tr);
                })
            },
            error: function () {
                alert('Выберите способ сортировки');
            }
        }); console.log("After ajax!!");
    }


});*/