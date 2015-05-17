<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GetInfo</title>
</head>
<body>
     <form method="get" action="<%= request.getContextPath()%>/GetInfo">
        <input type="text" id="id" name="sid" placeholder="学号"/>
        <input type="submit" id="subbot" name="subbot" value="查询"></input>        
    </form>
</body>
</html>