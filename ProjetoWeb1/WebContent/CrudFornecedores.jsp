<%@page import="model.Fornecedor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="control.FornecedorDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Fornecedores</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.2/css/all.css" rel="stylesheet" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<style type="text/css">
tr.rows {
	cursor: pointer;
}
tr.rows.active td {
	background-color: #0d6efd;
}
tr.rows:hover {
	background-color: #0d6efd80;
}
</style>
</head>
<body>
<jsp:include page="Menu.jsp"></jsp:include>
<jsp:useBean id="cd" class="control.FornecedorDAO"></jsp:useBean>

<script type="text/javascript" src="jquery-3.5.1.min.js"></script>
<h1>Cadastro de Fornecedors</h1>
<div class="container">
<nav class="navbar navbar-light bg-light justify-content-between">
  <form method="get" class="form-inline">
  <% 
	//FornecedorDAO cd =  new FornecedorDAO();
  
	String auxPesquisa=request.getParameter("pesquisa");
	if (auxPesquisa == null) auxPesquisa="";
	
	String auxId=request.getParameter("id");
	String auxRazaoSocial=request.getParameter("razaosocial");
	String auxCidade=request.getParameter("cidade");
	String auxTelefone=request.getParameter("telefone");

	String auxGravar=request.getParameter("gravar");
	String auxCancelar=request.getParameter("cancelar");
	String auxApagar=request.getParameter("apagar");

	String auxAnterior=request.getParameter("anterior");
	String auxProximo=request.getParameter("proximo");

	String aux=request.getParameter("pagina");
	int auxPagina=1;
	if (aux!=null) auxPagina=Integer.parseInt(aux);
	if (auxPagina<1) auxPagina=1;
	
	if (auxAnterior !=null) {
		if (auxPagina>1) cd.setPageNumber(auxPagina-1);
	}
	if (auxProximo !=null) {		
		cd.setPageNumber(auxPagina+1);
	}
	
	String auxMethod=request.getMethod();
	
	if (auxGravar!=null) {
		if (auxId==null) auxId="0";
		if (auxId=="") auxId="0";
		cd.gravar(new Fornecedor(Integer.parseInt(auxId),auxRazaoSocial,auxTelefone,auxCidade));
	} else if (auxCancelar!=null) {
		
	} else if (auxApagar!=null) {
		if (auxId==null) auxId="0";
		if (auxId=="") auxId="0";
		if (auxId!="0")
			cd.excluir(Integer.parseInt(auxId));
	}
	
	%>
    <input class="form-control mr-sm-2" 
    		type="search" 
    		placeholder="Pesquisar Fornecedor..." 
    		aria-label="Pesquisar"
    		name="pesquisa"
    		value="<% out.print(auxPesquisa); %>">
  </form>
  <button type="button" 
  		  class="btn btn-primary"
  		  id="btIncluir"
  		  onclick="limpar()"><i class="fas fa-user-plus"></i>&nbsp;Incluir Fornecedor</button>
</nav>
  <div class="row">
    <div class="col-sm">
		<table id="tabelaFornecedors" class="table">
		  <tr>
		    <th>ID</th>
		    <th>Razao Social</th>
		    <th>Telefone</th>
		    <th>Cidade</th>
		  </tr>

			<%
			
			ArrayList<Fornecedor> cli = cd.pesquisar(auxPesquisa);
			
			for (Fornecedor Fornecedor : cli) {		
				out.println("<tr class='rows'>");
				out.println("<td>"+Fornecedor.getId().toString()+"</td>");
				out.println("<td>"+Fornecedor.getRazaoSocial()+"</td>");
				out.println("<td>"+Fornecedor.getTelefone()+"</td>");
				out.println("<td>"+Fornecedor.getCidade()+"</td>");
				out.println("</tr>");
			}
			%>		  
		  
		  </tr>
		</table>
		<form method="post" 
			id="formPaginate" name="formPaginate">
			<nav aria-label="Page navigation example">
			  <ul class="pagination">
			    <li class="page-item"><button name="anterior" type="submit" class="page-link"><i class="fas fa-backward"></i>&nbsp;Anterior</button></li>
			    <input type="text" class="form-control" 
		    		id="pagina"
		    		name="pagina"
		    		value="<% out.print(cd.getPageNumber()); %>"
		    		readonly>
			    <li class="page-item"><button name="proximo" type="submit" class="page-link" >Próxima&nbsp;<i class="fas fa-forward"></i></button></li>
			  </ul>
			</nav>
		</form>
    </div>
    <div class="col-sm">
		<form method="post" 
			id="formFornecedor" name="formFornecedor">
		  <div class="form-group">
		    <label for="codigoInput">Código</label>
		    <input type="text" class="form-control" 
		    		id="codigoInput"
		    		name="id" 
		           placeholder="Código do Fornecedor"
		           readonly>
		  </div>
		  <div class="form-group">
		    <label for="razaoSocialInput">Razao Social</label>
		    <input type="text" class="form-control" 
		    		id="razaoSocialInput"
		    		name="razaosocial" 
		           placeholder="Razão Social do Fornecedor">
		  </div>
		  <div class="form-group">
		    <label for="telefoneInput">Telefone</label>
		    <input type="text" class="form-control" 
		    	   id="telefoneInput"
		    	   name="telefone" 
		           aria-describedby="telefoneHelp" placeholder="Telefone do Fornecedor">
 			<small id="telefoneHelp" class="form-text text-muted">Informe o telefone no formato (##) # ####-####</small>
 		  </div>
		  <div class="form-group">
		    <label for="cidadeInput">Cidade</label>
		    <input type="text" class="form-control" 
		    	   id="cidadeInput"
		    	   name="cidade" 
		           placeholder="Cidade do Fornecedor">
 		  </div>
			<nav class="navbar navbar-light bg-light">
			  <button name="gravar" type="submit" class="btn btn-success"><i class="fas fa-save"></i>&nbsp;Gravar</button>
			  <button name="cancelar" type="submit" class="btn btn-secondary"><i class="fas fa-window-close"></i>&nbsp;Cancelar</button>
			  <button id="novoApagar" class="btn btn-danger btn-right"><i class="fas fa-trash-alt"></i>&nbsp;Apagar</button>
			  <button id="apagar" name="apagar" type="submit" style="display:none" ></button>
			</nav> 		  
		</form>
    </div>
  </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<script>
$("tr.rows").click(function(evento){
	$("tr.rows").removeClass("active");
	$(this).addClass("active");
	var id=$($(this).find("td")[0]).text();
	var razaosocial=$($(this).find("td")[1]).text();
	var telefone=$($(this).find("td")[2]).text();
	var cidade=$($(this).find("td")[3]).text();
	$("#codigoInput").val(id);
	$("#razaoSocialInput").val(razaosocial);
	$("#telefoneInput").val(telefone);
	$("#cidadeInput").val(cidade);
});
//$("#btIncluir").click(function(evento){
//	$("tr.rows").removeClass("active");
//	$("#codigoInput").val("");
//	$("#nomeInput").val("");
//	$("#telefoneInput").val("");
//});
function limpar(){
	$("tr.rows").removeClass("active");
	$("#codigoInput").val("");
	$("#razaoSocialInput").val("");
	$("#telefoneInput").val("");
	$("#cidadeInput").val("");
}
$("#novoApagar").click(function(evento){
	evento.preventDefault();
	confirmaApagar();
});
function confirmaApagar(){
	$.confirm({
	    title: 'Exclusão de Fornecedor',
	    content: 'Deseja realmente apagar este Fornecedor?',
	    buttons: {
	        confirma: {
	            text: 'Apagar',
	            btnClass: 'btn-danger',
	            action: function(){
	            	//$("#apagar").click();
	            	let auxUrl=window.location;
	            	let auxId=$("#codigoInput").val();
	            	let auxDados={
	            			id: auxId,
	            			apagar:''
	            	}
	            	$.post(auxUrl,auxDados, function(data){
	            		location.reload();
	            	});
	            }
	        },
	        cancela: {
	            text: 'Não Apagar',
	            btnClass: 'btn-secondary',
	            action: function(){
	                
	            }
	        }
	    }
	});
}
</script>
</html>