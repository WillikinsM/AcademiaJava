<%@page import="control.FornecedorDAO"%>
<%@page import="model.Fornecedor"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Produto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="control.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de Produtos</title>
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
<jsp:useBean id="cd" class="control.ProdutoDAO"></jsp:useBean>
<h1>Cadastro de Produtos</h1>
<div class="container">
<nav class="navbar navbar-light bg-light justify-content-between">
  <form method="get" class="form-inline">
  <% 
	//ProdutoDAO cd =  new ProdutoDAO();
  
	String auxPesquisa=request.getParameter("pesquisa");
	if (auxPesquisa == null) auxPesquisa="";
	
	String auxId=request.getParameter("id");
	String auxNome=request.getParameter("nome");
	String auxEstoque=request.getParameter("estoque");
	String auxValor=request.getParameter("valor");
	String auxFornecedorId=request.getParameter("fornecedor");

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
		
		Produto paux=new Produto();
		paux.setId(Integer.parseInt(auxId));
		paux.setNome(auxNome);
		paux.setEstoque(Integer.parseInt(auxEstoque));
		paux.setValor(new BigDecimal(auxValor));
		paux.setFornecedorId(Integer.parseInt(auxFornecedorId));
		
		cd.gravar(paux);
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
    		placeholder="Pesquisar Produto..." 
    		aria-label="Pesquisar"
    		name="pesquisa"
    		value="<% out.print(auxPesquisa); %>">
  </form>
  <button type="button" 
  		  class="btn btn-primary"
  		  id="btIncluir"
  		  onclick="limpar()"><i class="fas fa-user-plus"></i>&nbsp;Incluir Produto</button>
</nav>
  <div class="row">
    <div class="col-sm">
		<table id="tabelaProdutos" class="table">
		  <tr>
		    <th>ID</th>
		    <th>Nome</th>
		    <th>Estoque</th>
		    <th>Valor</th>
		    <th>Fornecedor</th>
		  </tr>

			<%
			
			ArrayList<Produto> cli = cd.pesquisar(auxPesquisa);
			
			for (Produto produto : cli) {		
				out.println("<tr class='rows'>");
				out.println("<td>"+produto.getId().toString()+"</td>");
				out.println("<td>"+produto.getNome()+"</td>");
				out.println("<td>"+produto.getEstoque()+"</td>");
				out.println("<td>"+produto.getValor()+"</td>");
				out.print("<td fid="+'"');
				out.print(produto.getFornecedor().getId().toString().trim()+'"'+">");
				out.println(produto.getFornecedor()+"</td>");
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
			id="formProduto" name="formProduto">
		  <div class="form-group">
		    <label for="codigoInput">Código</label>
		    <input type="text" class="form-control" 
		    		id="codigoInput"
		    		name="id" 
		           placeholder="Código do Produto"
		           readonly>
		  </div>
		  <div class="form-group">
		    <label for="nomeInput">Nome</label>
		    <input type="text" class="form-control" 
		    		id="nomeInput"
		    		name="nome" 
		           placeholder="Nome do Produto">
		  </div>
		  <div class="form-group">
		    <label for="estoqueInput">Estoque</label>
		    <input type="text" class="form-control" 
		    	   id="estoqueInput"
		    	   name="estoque" 
		           placeholder="Estoque do Produto">
 		  </div>
		  <div class="form-group">
		    <label for="valorInput">Valor</label>
		    <input type="text" class="form-control" 
		    	   id="valorInput"
		    	   name="valor" 
		           placeholder="Valor do Produto">
 		  </div>
		  <div class="form-group">
		    <label for="forncedorInput">Fornecedor</label>
			<select id="forncedorInput"
				name="fornecedor" 
				class="form-control">
				<%				
				FornecedorDAO fd= new FornecedorDAO();
		    	fd.setPageSize(200);
		    	fd.setPageNumber(1);	
				ArrayList<Fornecedor> forne = fd.pesquisar("");
				
				for (Fornecedor fornecedor : forne) {		
					out.println("<option value="+'"'+
								fornecedor.getId().toString()+'"'+">"+
								fornecedor.getRazaoSocial()+
								" ("+fornecedor.getId()+")</option>");
				}
				%>					
			</select>
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
<script>
$("tr.rows").click(function(evento){
	$("tr.rows").removeClass("active");
	$(this).addClass("active");
	var id=$($(this).find("td")[0]).text();
	var nome=$($(this).find("td")[1]).text();
	var estoque=$($(this).find("td")[2]).text();
	var valor=$($(this).find("td")[3]).text();	
	var fornecedorId=$($(this).find("td")[4]).attr("fid");
	$("#codigoInput").val(id);
	$("#nomeInput").val(nome);
	$("#estoqueInput").val(estoque);
	$("#valorInput").val(valor);
	$("#forncedorInput").val(fornecedorId);
});
//$("#btIncluir").click(function(evento){
//	$("tr.rows").removeClass("active");
//	$("#codigoInput").val("");
//	$("#nomeInput").val("");
//	$("#estoqueInput").val("");
//});
function limpar(){
	$("tr.rows").removeClass("active");
	$("#codigoInput").val("");
	$("#nomeInput").val("");
	$("#estoqueInput").val("");
	$("#valorInput").val("");
}
$("#novoApagar").click(function(evento){
	evento.preventDefault();
	confirmaApagar();
});
function confirmaApagar(){
	$.confirm({
	    title: 'Exclusão de Produto',
	    content: 'Deseja realmente apagar este Produto?',
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