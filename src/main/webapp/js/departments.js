/**
 * Created by dukaa on 17.11.2014.
 */

table = '#table-departments > tbody';

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

$( document ).ready(function() {
    buildT(table, makeRequest(departmentsUrl));
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
    var payload = JSON.stringify({ action : 'DELETE', model_id :  id });
    buildT(table, makeRequest(departmentsUrl, payload));
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
        var payload = JSON.stringify({action: 'UPDATE', model_id: id, model_new_name: new_name.val()});
        buildT(table, makeRequest(departmentsUrl, payload));
        $("#updateDialog").dialog("close");
    }
}

function createDepartment() {
    var name_department = $('#department_name');
    if (validation(name_department)) {

        var payload = JSON.stringify({action: 'CREATE', model_name: name_department.val()});
        buildT(table, makeRequest(departmentsUrl, payload));
        $("#createDialog").dialog( "close" );
    }
}

function serverDepartmentNameValidation(name, n) {
    var payload = JSON.stringify({ action : 'VALIDATE', model_name :  name.val()});
    var valid = makeRequest(departmentsUrl, payload)[0].validation_result;
    if (!valid) {
        name.addClass( "ui-state-error" );
        updateTips( n );
    }
    return valid;
}
