<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"
        import="com.rulez.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Departments</title>

    <script type="text/javascript" src="/js/jquery-2.1.1.js" ></script>
    <script type="text/javascript" src="/js/departments.js" ></script>
    <script type="text/javascript" src="/js/jquery-ui.js" ></script>
    <link rel="stylesheet"  type="text/css" href="/css/style.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.structure.css" />
    <link rel="stylesheet"  type="text/css" href="/css/jquery-ui.theme.css" />


</head>
<body id="body">
<h2>Departments</h2>
<div style="width: 100%; margin-bottom: 5px; margin-left: 2px;">
    <input type="button" value="Create" id="create_button" onclick="javascript: $('#createDialog').dialog('open');">
</div>
<div id="container">
<table id="table-departments" class="departments">
    <colgroup>
        <col width="15%" />
        <col width="60%" />
        <col width="25%" />
    </colgroup>
    <thead>
        <th>Number</th>
        <th>Departments Names</th>
    </thead>
    <tbody>
    </tbody>
</table>
</div>

<div id="updateDialog" title="Update department name" style="display: none">
    <p class="validateTips" ></p>
    <label for="new_department_name">New Department Name</label>
    <input type="text" name="new_department_name" id="new_department_name" value="" class="new_department_name text ui-widget-content ui-corner-all">
</div>
<div id="createDialog" title="Create new department" style="display: none">
    <p class="validateTips" ></p>
    <label for="new_department_name">New Department Name</label>
    <input type="text" name="department_name" id="department_name" value="" class="new_department_name text ui-widget-content ui-corner-all">
</div>
</body>
</html>