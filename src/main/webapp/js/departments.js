/**
 * Created by dukaa on 17.11.2014.
 */

function buildTable() {
    $.ajax({
        type: "POST",
        url: "/departments",
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8"
    }).done(function(data){
        $('#table-departments > tbody').html("");
        for (var i=0; i < data.length; i++) {
            var tr = createRow(data[i]);
            if ( i % 2 == 0 ) {
                tr.setAttribute("class", "even");
            } else {
                tr.setAttribute("class", "odd");
            }
            $('#table-departments > tbody').append(tr);
        }
    });
}

function updateRow(id) {
    $('#updateDialog').dialog({
        autoOpen: false,
        resizable : false,
        height : 300,
        modal : true,
        buttons : {
            "Update" : function() {
                $(this).dialog( updateDepartment(id) );
            },
            Cancel : function() {
                $( this ).dialog( "close" );
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    });
    $('#updateDialog').dialog("open");
}


function createRow(data) {
    var tr = document.createElement("tr");

    var tdId = document.createElement("td");
    var spanId = document.createElement("span");
    spanId.innerText = data.model_id;
    tdId.appendChild(spanId);
    tr.appendChild(tdId);

    var tdName = document.createElement("td");
    var aName = document.createElement("a");
    aName.innerText = data.model_name;
    aName.href = "/staff?id_department=" + data.model_id + "&name_department=" + data.model_name;
    tdName.appendChild(aName);
    tr.appendChild(tdName);


    var divControls = document.createElement("div");
    var tdDeleteBtn = document.createElement("td");
    var deleteBtn = document.createElement("input");
    var updateBtn = document.createElement("input");
    deleteBtn.type = "button";
    updateBtn.type = "button";
    updateBtn.setAttribute("value", "Update");
    deleteBtn.setAttribute("value", "Delete");
    updateBtn.setAttribute( "onClick", "javascript: updateRow(" + data.model_id + ");" )
    deleteBtn.setAttribute( "onClick", "javascript: deleteDepartment(" + data.model_id + ");" );
    divControls.appendChild(deleteBtn);
    divControls.appendChild(updateBtn);
    tdDeleteBtn.appendChild(divControls);
    tr.appendChild(tdDeleteBtn);



    return tr;
}

$( document ).ready(function() {
    buildTable();
    $('#createDialog').dialog({
        autoOpen: false,
        resizable : false,
        height : 300,
        modal : true,
        buttons : {
            "Create" : function() {
                $(this).dialog( createDepartment() );
            },
            Cancel : function() {
                $( this ).dialog( "close" );
            }
        },
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
    })
});

function deleteDepartment(id) {
    $.ajax({
        type: "POST",
        url: "/departments",
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({ action : 'DELETE', model_id :  id })
    }).done( buildTable() );
}

function validation(name) {
    var allFields = $( [] ).add( name );
    var valid = true;
    allFields.removeClass( "ui-state-error" );

    valid = valid && checkLength( name, "department", 2, 45 );
    valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Department name may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
    valid = valid && serverDepartmentNameValidation(name, "This department already exists.");

    return valid;
}

function updateDepartment(id) {
    var new_name = $('#new_department_name');
    if (validation(new_name)) {
        $.ajax({
            type: "POST",
            url: "/departments",
            dataType: "json",
            async : false,
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify({action: 'UPDATE', model_id: id, model_new_name: new_name.val()})
        }).done();
        buildTable();
        $("#updateDialog").dialog("close");
    }
}

function createDepartment() {
    var name_department = $('#department_name');
    if (validation(name_department)) {
        $.ajax({
            type: "POST",
            url: "/departments",
            dataType: "json",
            async : false,
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify({action: 'CREATE', model_name: name_department.val()})
        }).done();
        buildTable();
        $("#createDialog").dialog( "close" );
    }
}

function serverDepartmentNameValidation(name, n) {
    var valid;
    $.ajax({
            type: "POST",
            url: "/departments",
            dataType: "json",
            async : false,
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify({ action : 'VALIDATE', model_name :  name.val()})
    }).done(function(data) { valid = data[0].validation_result; });
    if (!valid) {
        name.addClass( "ui-state-error" );
        updateTips( n );
    }
    return valid;
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
