<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Staff</title>

    <script type="text/javascript" src="/js/jquery-2.1.1.js" ></script>
    <script type="text/javascript" src="/js/jquery-ui.js" ></script>
    <script type="text/javascript" src="/js/staff.js" ></script>
    <script type="text/javascript" src="/js/main.js" ></script>
    <link rel="stylesheet"  type="text/css" href="/css/style.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.structure.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.theme.css" />

</head>
<body>
<div id="current_id_department" style="display: none">${id_department}</div>
<h2>Staff of ${name_department} department</h2>
<div style="width: 100%; margin-bottom: 5px; margin-left: 2px; display: inline;">
    <input type="button" value="Create" id="create_button" onclick="javascript: createEmployeeDialog();">
</div>
<a href="/departments">Back to departments list</a>
<div id="container">
    <table id="table-staff" class="staff">
        <colgroup>
            <col width="15%" />
            <col width="25%" />
            <col width="25%" />
            <col width="15%" />
        </colgroup>
        <thead>
        <th>Number</th>
        <th>Name</th>
        <th>Email</th>
        <th>Hire Date</th>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</div>
<div id="createDialog" title="Create new employee" style="display: none">
    <p class="validateTips" >All fields are required.</p>
    <div>
        <label for="employee-name">Name</label>
        <input type="text" name="employee_name" id="employee-name" value="" class="text employee ui-widget-content ui-corner-all">
    </div>
    <div>
        <label for="employee-email">Email</label>
        <input type="text" name="employee_email" id="employee-email" value="" class="text employee ui-widget-content ui-corner-all">
    </div>
    <div>
        <label for="employee-department">Department</label>
        <select id="employee-department" class="employee ui-widget-content ui-corner-all"></select>
    </div>
    <div>
        <label for="employee-hireDate">Hire Date</label>
        <input type="text" name="employee_hire_date" id="employee-hireDate" value="" onkeydown="return false;" class="text employee ui-widget-content ui-corner-all" style="width: 160px;">
    </div>
</div>

<div id="updateDialog" title="Update employee data" style="display: none">
    <p class="validateTips" >All fields are required.</p>
    <div>
        <label for="old-employee-name">Name</label>
        <input type="text" name="employee_name" id="old-employee-name" value="" class="text employee ui-widget-content ui-corner-all">
    </div>
    <div>
        <label for="old-employee-email">Email</label>
        <input type="text" name="employee_email" id="old-employee-email" value="" class="text employee ui-widget-content ui-corner-all">
    </div>
    <div>
        <label for="old-employee-department">Department</label>
        <select id="old-employee-department" class="employee ui-widget-content ui-corner-all"></select>
    </div>
    <div>
        <label for="old-employee-hireDate">Hire Date</label>
        <input type="text" name="old-employee_hire_date" id="old-employee-hireDate" value="" onkeydown="return false;" class="text employee ui-widget-content ui-corner-all" style="width: 160px;">
    </div>
</div>
</body>
</html>