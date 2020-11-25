$(document).ready(function () {
    $.getJSON("getMyP",function (data) {
        let tbl = $("#tablebody");
        $.each(data,function (i,item) {
            let tr = $("<tr>");
            let td = $("<td>");
            td.append($("<a>",{
                'text':item.firstName
            }));
            tr.append(td);
            tr.append($("<td>",{'text':item.lastName}));
            tbl.append(tr);
        })
    })
});