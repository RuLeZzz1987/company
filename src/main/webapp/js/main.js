/**
 * Created by Rulezzz on 21.11.2014.
 */

departmentsUrl = "/departments";

function makeRequest(url, data) {
    var result;
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8",
        data : data
    }).done( function(data) { result = data; });
    return result;
}

function buildT(table, data) {
    $(table).html("");
    for (var i=0; i < data.length; i++) {
        if ( i % 2 == 0 ) {
            var tr = createRow(data[i], "even");
        } else {
            var tr = createRow(data[i], "odd");
        }
        $(table).append(tr);
    }
}

function checkRegexp( o, regexp, n ) {
    if ( !( regexp.test( o.val() ) ) ) {
        o.addClass( "ui-state-error" );
        updateTips( n );
        return false;
    } else {
        return true;
    }
}

function updateTips( t ) {
    var tips = $( ".validateTips" );
    tips
        .text( t )
        .addClass( "ui-state-highlight" );
    setTimeout(function() {
        tips.removeClass( "ui-state-highlight", 1500 );
    }, 500 );
}

function checkLength( o, n, min, max ) {
    if ( o.val().length > max || o.val().length < min ) {
        o.addClass( "ui-state-error" );
        updateTips( "Length of " + n + " must be between " +
            min + " and " + max + "." );
        return false;
    } else {
        return true;
    }
}