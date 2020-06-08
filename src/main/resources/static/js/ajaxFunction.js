$(document).ready(function () {
    dropdown()
    datepicker()

    $("#equity-data").submit(function (event) {

        //stop submit the form event. Do this manually using ajax post function
        event.preventDefault();
        console.log("Submit function invoked")

        let stockExchange = $("#stock-exchange option:selected").text();
        let stockId = $("#stocks-id option:selected").text();
        let interval = $("#stock-interval option:selected").text();
        let fromDate = $("#frm-datepicker").datepicker({dateFormat: 'dd-mm-yyyy'}).val()
        let toDate = $("#to-datepicker").datepicker({dateFormat: 'dd-mm-yyyy'}).val()

        console.log("StockExchange: " + stockExchange)
        console.log("StockId: " + stockId)
        console.log("Interval: " + interval)
        console.log("From Date: " + fromDate)
        console.log("To Date: " + toDate)
        console.log("From date format: " + $("#frm-datepicker").datepicker("option", "dateFormat"))
        console.log("From date value: " + $("#to-datepicker").datepicker({dateFormat: 'dd-mm-yyyy'}).val())


        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/equity/history",
            data: {
                "stockExchange": stockExchange,
                "stockId": stockId,
                "stockInterval": interval,
                "fromDate": fromDate,
                "toDate": toDate
            },
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                $('#error-panel').empty();
                $('#stock-history').empty();
                createDataTable(data)
            },
            error: function (e) {
                console.log("ERROR : ", e.responseText);
                let data = JSON.parse(e.responseText);
                $('#error-panel').empty();
                $(function () {
                    let value = $('<pre>').text(data.error + " : " + data.message)
                    $('#error-panel').append(value)
                });
            }
        });


    });

});

function dropdown() {
    $("#stock-exchange").selectmenu();
    $("#stocks-id").selectmenu();
    $("#stock-interval").selectmenu();
}

function datepicker() {
    $("#frm-datepicker").datepicker({
        currentText: "Now",
        dateFormat: "dd-mm-yy",
        showOn: "both",
        buttonImageOnly: true,
        buttonImage: "/js/images/calendar.gif",
        buttonText: "Calendar"
    });
    $("#to-datepicker").datepicker({
        currentText: "Now",
        dateFormat: "dd-mm-yy"
    });
}

function createDataTable(data) {
    let table = $('<table>').addClass('foo');
    table.append(
        $('<tr>').addClass('header'),
        $('<th>').text('Exchange'),
        $('<th>').text('StockId'),
        $('<th>').text('Year'),
        $('<th>').text('Month'),
        $('<th>').text('Open'),
        $('<th>').text('High'),
        $('<th>').text('Low'),
        $('<th>').text('Close')
    )
    $.each(data, function (i, item) {
        table.append(
            $('<tr>').addClass('bar'),
            $('<td>').text(item.exchange),
            $('<td>').text(item.stockSymbol),
            $('<td>').text(item.year),
            $('<td>').text(item.month),
            $('<td>').text(item.open),
            $('<td>').text(item.high),
            $('<td>').text(item.low),
            $('<td>').text(item.close),
        );
    });
    $('#stock-history').append(table)
}