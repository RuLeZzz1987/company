/**
 * Created by DukaA on 18.11.2014.
 */
table = '#table-staff > tbody';
staffUrl = document.url;

function createRow(data, trClass) {
    return $('<tr>').addClass(trClass).append($('<td>').append($('<span>').addClass('spanId').text(data.model_id)))
                    .append($('<td>').append($('<span>').addClass('spanName').text(data.model_name)))
                    .append($('<td>').append($('<span>').addClass('spanEmail').text(data.model_email)))
                    .append($('<td>').append($('<span>').addClass('spanHireDate').text(data.model_hire_date)))
                    .append($('<td>').append($('<div>').append($('<input>').attr('type', 'button').attr('value', 'Udpate').click( function() { updateRow(data.model_id, data.model_name, data.model_email, data.model_hire_date); } ))
                    .append($('<input>').attr('type', 'button').attr('value', 'Delete').click( function() { deleteEmployee(data.model_id); }) )));
}

function deleteEmployee(id) {
    var payload = JSON.stringify({action : "DELETE", model_id : id});
    buildT(table, makeRequest(staffUrl, payload));
}

$( document ).ready(function() {
    buildT(table, makeRequest(staffUrl));
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
            var payload = JSON.stringify({
                action: 'UPDATE',
                model_id: id,
                model_name: name.val(),
                model_email: email.val(),
                model_id_department: id_department.val(),
                model_hire_date: hireDate.val()
            });
            buildT(table, makeRequest(staffUrl, payload));
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
            var payload = JSON.stringify({
                action: 'CREATE',
                model_name: name.val(),
                model_email: email.val(),
                model_id_department: id_department.val(),
                model_hire_date: hireDate.val()
            });
            buildT(table, makeRequest(staffUrl, payload));
            $("#createDialog").dialog("close");
        }
    }
}

function serverMailValidation(mail, n) {
    var payload = JSON.stringify({ action : 'VALIDATE', model_email :  mail.val()});
    var valid = makeRequest(staffUrl, payload)[0].validation_result;
    if (!valid) {
        mail.addClass( "ui-state-error" );
        updateTips( n );
    }
    return valid;
}

function serverMailValidationForUpdate(mail, n, id) {
    var payload = JSON.stringify({ action : 'VALIDATE', model_email :  mail.val()});
    var reqResult = makeRequest(staffUrl, payload);
    var valid = reqResult[0].validation_result;
    var cur_id = reqResult[0].valid_for_update;
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

function createEmployeeDialog() {
    getSelectDepartments($('#employee-department'));
    $('#createDialog').dialog('open');
}

function getSelectDepartments(employeeDepartment) {
    fillingSelectDepartments(makeRequest(departmentsUrl), employeeDepartment);
}

function fillingSelectDepartments(data, employeeDepartment) {
    employeeDepartment.html('');
    for (var i = 0; i < data.length; i++) {
        employeeDepartment.append($('<option>').attr('value', data[i].model_id).text(data[i].model_name));
    }
    employeeDepartment.val($('#current_id_department').text());
}