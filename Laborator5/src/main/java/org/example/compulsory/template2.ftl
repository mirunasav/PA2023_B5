<!DOCTYPE html>
<html>
<head>
    <title>Document Report</title>
</head>
<body>
<h1>Document Report</h1>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Pathname</th>
    </tr>
    </thead>
    <tbody>
    <#list documents as doc>
        <tr>
            <td>${doc.ID}</td>
            <td>${doc.name}</td>
            <td>${doc.pathName}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>

