<%@page import="relatorios.RodarRelatorio"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.boxRelatorio {
	width: 100%;
	float: left;
	height: calc(100vh - 60px);
}
</style>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<%
	RodarRelatorio rr = new RodarRelatorio();
	int auxRand = (int) (1000000 + Math.random() * (9999999 - 1000000));
	rr.executar("RelClientes.jrxml", request.getRealPath("/"), "RelClientes" + auxRand + ".pdf");
	%>
	<div>
		<div class='boxRelatorio'>
			<object data='./<%out.print("./RelClientes" + auxRand + ".pdf");%>'
				type='application/pdf' width='100%' height='100%'> </object>
		</div>
	</div>
</body>
</html>