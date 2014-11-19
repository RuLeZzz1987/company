/**
 * Created by DukaA on 18.11.2014.
 */

function buildTable() {
    $.ajax({
        type: "POST",
        url: document.url,
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8"
    }).done(function(data){ buildT(data) });
}

function createRow(data) {
    var tr = document.createElement("tr");

    var tdId = document.createElement("td");
    var spanId = document.createElement("span");
    spanId.className = "spanId";
    spanId.innerText = data.model_id;
    tdId.appendChild(spanId);
    tr.appendChild(tdId);

    var tdName = document.createElement("td");
    var spanName = document.createElement("span");
    spanName.className = "spanName";
    spanName.innerText = data.model_name;
    tdName.appendChild(spanName);
    tr.appendChild(tdName);

    var tdEmail = document.createElement("td");
    var spanEmail = document.createElement("span");
    spanEmail.className = "spanEmail";
    spanEmail.innerText = data.model_email;
    tdEmail.appendChild(spanEmail);
    tr.appendChild(tdEmail);

    var tdHireDate = document.createElement("td");
    var spanHireDate = document.createElement("span");
    spanHireDate.className = "spanHireDate";
    spanHireDate.innerText = data.model_hire_date;
    tdHireDate.appendChild(spanHireDate);
    tr.appendChild(tdHireDate);

    var divControls = document.createElement("div");
    var tdDeleteBtn = document.createElement("td");
    var deleteBtn = document.createElement("input");
    var updateBtn = document.createElement("input");
    deleteBtn.type = "button";
    updateBtn.type = "button";
    updateBtn.setAttribute("value", "Update");
    deleteBtn.setAttribute("value", "Delete");
    updateBtn.setAttribute( "onClick", "javascript: updateRow(" + data.model_id + ",'" + data.model_name + "','" + data.model_email + "','" + data.model_hire_date + "');" )
    deleteBtn.setAttribute( "onClick", "javascript: deleteEmployee(" + data.model_id + ");" );
    divControls.appendChild(deleteBtn);
    divControls.appendChild(updateBtn);
    tdDeleteBtn.appendChild(divControls);
    tr.appendChild(tdDeleteBtn);

    return tr;
}

function buildT(data) {
    $('#table-staff > tbody').html("");
    for (var i=0; i < data.length; i++) {
        var tr = createRow(data[i]);
        if ( i % 2 == 0 ) {
            tr.setAttribute("class", "even");
        } else {
            tr.setAttribute("class", "odd");
        }
        $('#table-staff > tbody').append(tr);
    }
}

function deleteEmployee(id) {
    $.ajax({
        type: "POST",
        url: document.url,
        dataType: "json",
        async: false,
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({action : "DELETE", model_id : id})
    }).done(function(data) { buildT(data); });
}

$( document ).ready(function() {
    buildTable();
    $('#createDialog').dialog({
        autoOpen: false,
        resizable : false,
        height : 400,
        modal : true,
        buttons : {
            "Create" : function() {
                $(this).dialog( createEmployee() );
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
    $( "#employee-hireDate" ).datepicker({
        dateFormat:"yy.mm.dd"
    }).datepicker("setDate", new Date());
    $( "#employee-hireDate").on('paste', function(){
        return false;
    });
    $( "#old-employee-hireDate" ).datepicker({
        dateFormat:"yy.mm.dd"
    });
    $( "#old-employee-hireDate").on('paste', function(){
        return false;
    });
});

function rowValidation(name, email) {
    var emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/ ;
    var allFields = $( [] ).add( name ).add( email);
    var valid = true;
    allFields.removeClass( "ui-state-error" );
    valid = valid && checkLength( name, "username", 3, 16 );
    valid = valid && checkLength( email, "email", 6, 45 );

    valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
    valid = valid && checkRegexp( email, emailRegex, "eg. Ivan@company.com" );

    return valid;
}

function updateRow(id, name, mail, hireDate) {
    $('#updateDialog').dialog({
        autoOpen: false,
        resizable : false,
        height : 400,
        modal : true,
        buttons : {
            "Update" : function() {
                $(this).dialog( updateEmployee(id) );
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
    $("#old-employee-email").val(mail);
    $("#old-employee-name").val(name);
    $("#old-employee-hireDate").val(hireDate);
    getSelectDepartments($('#old-employee-department'));
    $('#updateDialog').dialog("open");
}

function updateEmployee(id) {
    var name = $("#old-employee-name");
    var email = $("#old-employee-email");
    var hireDate = $("#old-employee-hireDate");
    var id_department = $( "#old-employee-department" );

    if (rowValidation(name, email)) {
        if (serverMailValidationForUpdate(email, "This email already exist.", id)) {
            $.ajax({
                type: "POST",
                url: document.url,
                dataType: "json",
                async: false,
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({
                    action: 'UPDATE',
                    model_id: id,
                    model_name: name.val(),
                    model_email: email.val(),
                    model_id_department: id_department.val(),
                    model_hire_date: hireDate.val()
                })
            }).done(function (data) {
                buildT(data);
            });
            $("#updateDialog").dialog("close");
        }
    }
}


function createEmployee() {
    var name = $( "#employee-name" );
    var email = $( "#employee-email" );
    var id_department = $( "#employee-department" );
    var hireDate = $( "#employee-hireDate" );

    if (rowValidation(name, email)) {
        if (serverMailValidation(email, "This email already exist.")) {
            $.ajax({
                type: "POST",
                url: document.url,
                dataType: "json",
                async: false,
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({
                    action: 'CREATE',
                    model_name: name.val(),
                    model_email: email.val(),
                    model_id_department: id_department.val(),
                    model_hire_date: hireDate.val()
                })
            }).done(function (data) {
                buildT(data);
            });
            $("#createDialog").dialog("close");
        }
    }
}

function serverMailValidation(mail, n) {
    var valid;
    $.ajax({
        type: "POST",
        url: document.url,
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({ action : 'VALIDATE', model_email :  mail.val()})
    }).done(function(data) {
        valid = data[0].validation_result;
    });
    if (!valid) {
        mail.addClass( "ui-state-error" );
        updateTips( n );
    }
    return valid;
}

function serverMailValidationForUpdate(mail, n, id) {
    var valid;
    var cur_id = -1;
    $.ajax({
        type: "POST",
        url: document.url,
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({ action : 'VALIDATE', model_email :  mail.val()})
    }).done(function(data) {
        valid = data[0].validation_result;
        cur_id = data[0].valid_for_update;
    });
    if (!valid) {
        if (cur_id != id) {
            mail.addClass( "ui-state-error" );
            updateTips( n );
        } else {
            valid = true;
        }
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

function createEmployeeDialog() {
    getSelectDepartments($('#employee-department'));
    $('#createDialog').dialog('open');
}

function getSelectDepartments(employeeDepartment) {
    $.ajax({
        type: "POST",
        url: "/departments",
        dataType: "json",
        async : false,
        contentType: "application/json; charset=UTF-8"
    }).done(function(data){ fillingSelectDepartments(data, employeeDepartment); });
}

function fillingSelectDepartments(data, employeeDepartment) {
    var option = document.createElement("option");
    employeeDepartment.html('');
    for (var i = 0; i < data.length; i++) {
        var option = document.createElement("option");
        option.setAttribute("value", data[i].model_id);
        option.innerText = data[i].model_name;
        employeeDepartment.append(option);
    }
    employeeDepartment.val($('#current_id_department').text());
}