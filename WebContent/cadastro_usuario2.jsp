<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Usuários</title>
</head>
<body>
<%

		if(request.getParameter("msg") != null){
			out.println("Cadastrado com Sucesso.");
		}

%>


	<form method="POST" action="Usuario"> <!-- A servlet chamada é a Usuario.java-->
		Login:<br><input type="text" name="txtLogin">
		<br>
		Senha:<br><input type="text" name="txtSenha">
		<br>
		<input type="submit" value="Cadastrar">
		
	</form>

</body>
</html>