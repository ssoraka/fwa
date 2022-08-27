<%@ page import="edu.school.cinema.models.User" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.util.stream.Stream" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="edu.school.cinema.models.Authentication" %>
<%@ page import="edu.school.cinema.services.UserService" %>
<%@ page import="edu.school.cinema.services.FileService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<% User user = (User) request.getSession().getAttribute("user");%>
<% UserService userService = (UserService) request.getSession().getAttribute("userService");%>
<% user = userService.getUserByPhoneNumber(user.getPhoneNumber());%>
<% File pathToPic = (File) session.getAttribute("pathImages");%>
<table width="50%" border="1">
    <thead>
    <tr>
        <td>Date and time</td>
        <td>IP</td>
    </tr>
    </thead>
    <tbody>

    <% for (Authentication auth : user.getAuthentications()) {%>
    <tr>
        <td><%=auth.getDate()%>
        </td>
        <td><%=auth.getIp()%>
        </td>
    </tr>
    <%}%>

    <br>
    </tbody>
</table>

<form enctype="multipart/form-data" action="/profile" method="post">

    <input type="file" name="file" size="100"/>
    <input type="submit" value="Upload new Pic"/>
    <br/>

</form>

<%List<File> list = FileService.getFiles(pathToPic);%>

<table width="50%" border="1">
    <thead>
    <tr>
        <td>File name</td>
        <td>Size</td>
        <td>MIME</td>
    </tr>
    </thead>
    <tbody>

    <% if (list != null) { %>
        <% for (File file : list) {%>
        <tr>
            <td><a href="images/<%=file.getName()%>"><%=file.getName()%>
            </a></td>
            <td><%=file.length()%>
            </td>
            <td><%=Files.probeContentType(file.toPath())%>
            </td>
        </tr>
        <%}%>
    <%}%>

    <br>
    </tbody>
</table>

<br>
<a href="/logout">logout</a>
<br>
<a><%pathToPic.getAbsolutePath(); %></a>
</body>
</html>
