<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>show one picture</title>
</head>
<body>
<img src="data:image/png;base64,<%=request.getSession().getAttribute("image")%>" height="800" width="1200">
</body>
</html>
