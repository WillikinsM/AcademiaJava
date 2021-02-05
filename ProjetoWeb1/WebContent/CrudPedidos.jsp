<%@page import="java.util.Date"%>
<%@page import="java.util.Arrays"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="java.util.Collections"%>
<%@page import="control.ProdutoDAO"%>
<%@page import="model.PedidoItem"%>
<%@page import="model.Cliente"%>
<%@page import="control.ClienteDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Produto"%>
<%@page import="control.FornecedorDAO"%>
<%@page import="model.Fornecedor"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="model.Pedido"%>
<%@page import="java.util.ArrayList"%>
<%@page import="control.PedidoDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pedidos</title>
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
<jsp:useBean id="cd" class="control.PedidoDAO"></jsp:useBean>
<jsp:useBean id="cp" class="control.ProdutoDAO"></jsp:useBean>
<jsp:useBean id="fc" class="control.ClienteDAO"></jsp:useBean>

<% 
//PedidoDAO cd =  new PedidoDAO();

boolean mostraProduto=false;

String auxPesquisaPedido=request.getParameter("pesquisaPedido");
String auxPedidoSelecionado=request.getParameter("pedidoSelecionado");
if (auxPesquisaPedido == null) auxPesquisaPedido="";
if (auxPedidoSelecionado == null) auxPedidoSelecionado="";

String auxGravar=request.getParameter("gravar");
String auxCancelar=request.getParameter("cancelar");
String auxApagar=request.getParameter("apagar");
String auxIncluir=request.getParameter("btIncluir");
String auxCliente=request.getParameter("cliente"); 

Pedido pedidoAtual =  new Pedido();
if (auxIncluir !=null) { 
	Cliente clienteAtual=null;
	if ((auxCliente!=null)&&(auxCliente !="")) clienteAtual=fc.getById(Integer.parseInt(auxCliente));
	if (clienteAtual==null) clienteAtual=new Cliente();
	
	pedidoAtual.setCliente(clienteAtual);
	pedidoAtual.setDtVenda(new Date());
	cd.gravar(pedidoAtual);
}

if (auxPedidoSelecionado != "") {
	pedidoAtual = cd.getById(Integer.parseInt(auxPedidoSelecionado));
}
if (pedidoAtual == null) pedidoAtual =  new Pedido();

String auxAddProdutoId=request.getParameter("addProdutoId");
String auxAddProdutoValor=request.getParameter("addProdutoValor");
String auxAddProdutoAcao=request.getParameter("addProdutoAcao");
if (auxAddProdutoAcao !=null) {
	mostraProduto=true;
	
	boolean flag=false;
	for (PedidoItem item : pedidoAtual.getItens()) {
		if (item.getProduto().getId() == Integer.parseInt(auxAddProdutoId)) {
			flag=true;
			if (auxAddProdutoAcao =="ADD") 
				item.setQuantidade(item.getQuantidade()+1);
			else {
				item.setQuantidade(item.getQuantidade()-1);
				if (item.getQuantidade()<1) {
					pedidoAtual.getItens().remove(item);
				}
			}
			break;
		}
	}
	if (auxAddProdutoAcao =="ADD") {
		//pedidoAtual.getItens().add( );
		if (!flag) {
			Produto np=cp.getById(Integer.parseInt(auxAddProdutoId));
			PedidoItem npi = new PedidoItem();
			npi.setProduto(np);
			npi.setValor(np.getValor());
			npi.setQuantidade(1);
			pedidoAtual.getItens().add(npi);				
		}		
	}
	cd.gravar(pedidoAtual);
	pedidoAtual=cd.getById(pedidoAtual.getId());
	
}

String auxPesquisaProduto=request.getParameter("pesquisaProduto");
if (auxPesquisaProduto != null) mostraProduto=true;

if (auxPesquisaProduto == null) auxPesquisaProduto="";

String auxId=request.getParameter("id");
String auxNome=request.getParameter("nome");
String auxEstoque=request.getParameter("estoque");
String auxValor=request.getParameter("valor");
String auxFornecedorId=request.getParameter("fornecedor");

String auxAnteriorPedido=request.getParameter("anteriorPedido");
String auxProximoPedido=request.getParameter("proximoPedido");

String auxAnteriorProduto=request.getParameter("anteriorProduto");
String auxProximoProduto=request.getParameter("proximoProduto");

String auxPedido=request.getParameter("paginaPedido");
int auxPaginaPedido=1;
if (auxPedido!=null) auxPaginaPedido=Integer.parseInt(auxPedido);
if (auxPaginaPedido<1) auxPaginaPedido=1;

String auxProduto=request.getParameter("paginaProduto");
int auxPaginaProduto=1;
if (auxProduto!=null) auxPaginaProduto=Integer.parseInt(auxProduto);
if (auxPaginaProduto<1) auxPaginaProduto=1;

if (auxAnteriorPedido !=null) {
	if (auxPaginaPedido>1) cd.setPageNumber(auxPaginaPedido-1);
}
if (auxProximoPedido !=null) {		
	cd.setPageNumber(auxPaginaPedido+1);
}		

if (auxAnteriorProduto !=null) {
	if (auxPaginaProduto>1) cp.setPageNumber(auxPaginaProduto-1);
}
if (auxProximoProduto !=null) {		
	cp.setPageNumber(auxPaginaProduto+1);
}		

if (auxAnteriorProduto !=null) mostraProduto=true; 
if (auxProximoProduto !=null) mostraProduto=true; 

String auxMethod=request.getMethod();

SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

if (auxGravar!=null) {
	if (auxId==null) auxId="0";
	if (auxId=="") auxId="0";
	
	Pedido paux=new Pedido();
	paux.setId(Integer.parseInt(auxId));
	
	//cd.gravar(paux);
} else if (auxCancelar!=null) {
	
} else if (auxApagar!=null) {
	if (auxId==null) auxId="0";
	if (auxId=="") auxId="0";
	if (auxId!="0")
		cd.excluir(Integer.parseInt(auxId));
}

%>

<h1>Pedidos</h1>
<div class="container">

  <div class="row">
    <div class="col-sm">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link  <% if (!mostraProduto) out.print("active"); %>" 
		    	id="listaPedidos-tab" 
		    	data-toggle="tab" 
		    	href="#listaPedidos" 
		    	role="tab" aria-controls="listaPedidos" 
		    	aria-selected="true">Pedidos</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link <% if (mostraProduto) out.print("active"); %>" 
		    	id="listaProdutos-tab" 
		    	data-toggle="tab" 
		    	href="#listaProdutos" role="tab" 
		    	aria-controls="listaProdutos" 
		    	aria-selected="false">Produtos</a>
		  </li>
		</ul>
		<div class="tab-content" id="myTabContent">
		  <div class="tab-pane fade  <% if (!mostraProduto) out.print("show active"); %>" 
		  	id="listaPedidos" 
		  	role="tabpanel" 
		  	aria-labelledby="listaPedidos-tab">
			<nav class="navbar navbar-light bg-light justify-content-between">
			  <form id="fPesqPedido"
			  		name="fPesqPedido"
			 	    method="get" class="form-inline">
			    <input class="form-control mr-sm-2" 
			    		id="pesquisaPedido"
			    		type="search" 
			    		placeholder="Pesquisar Pedido..." 
			    		aria-label="Pesquisar"
			    		name="pesquisaPedido"
			    		value="<% out.print(auxPesquisaPedido); %>">
			    <input style="display: none;" type="text" 
			    			id="pedidoSelecionado" 
			    			name="pedidoSelecionado">
			  </form>
		    </nav>
			<table id="tabelaPedidos" class="table">
			  <tr>
			    <th>Pedido</th>
			    <th>Data</th>
			    <th>Cliente</th>
			  </tr>
	
				<%
				ArrayList<Pedido> pedidos = cd.pesquisar(auxPesquisaPedido);
								
				for (Pedido pedido : pedidos) {		
					out.print("<tr class='rows");
					if ((pedidoAtual != null)&&(pedidoAtual.getId()==pedido.getId())) {
						out.print(" active");
					}
					out.println("'>");
					out.println("<td>"+pedido.getId().toString()+"</td>");
					out.println("<td>"+df.format(pedido.getDtVenda())+"</td>");
					out.print("<td cid="+'"');
					out.print(pedido.getCliente().getId());
					out.print('"'+">"+pedido.getCliente()+"</td>");
					out.println("</tr>");
				}
				%>		  
			  
			  </tr>
			</table>
			<form method="post" 
				id="formPaginatePedido" name="formPaginatePedido">
				<nav aria-label="Page navigation example">
				  <ul class="pagination">
				    <li class="page-item">
				    <button name="anteriorPedido" 
				    	type="submit" class="page-link"><i class="fas fa-backward"></i>&nbsp;Anterior</button></li>
				    <input type="text" class="form-control" 
			    		id="paginaPedido"
			    		name="paginaPedido"
			    		value="<% out.print(cd.getPageNumber()); %>"
			    		readonly>
				    <li class="page-item">
				    <button name="proximoPedido" type="submit" class="page-link" >Próxima&nbsp;<i class="fas fa-forward"></i></button></li>
				  </ul>
				</nav>
			</form>
		  </div>
		  <div class="tab-pane fade  <% if (mostraProduto) out.print("show active"); %>" 
		  	id="listaProdutos" 
		  	role="tabpanel" 
		  	aria-labelledby="listaProdutos-tab">
			<nav class="navbar navbar-light bg-light justify-content-between">
			  <form method="get" class="form-inline">
			    <input class="form-control mr-sm-2" 
			    		type="search" 
			    		placeholder="Pesquisar Produto..." 
			    		aria-label="Pesquisar"
			    		name="pesquisaProduto"
			    		value="<% out.print(auxPesquisaProduto); %>">
			  </form>
		    </nav>
		<table id="tabelaProdutos" class="table">
		  <tr>
		    <th>ID</th>
		    <th>Nome</th>
		    <th>Estoque</th>
		    <th>Valor</th>
		    <th>Fornecedor</th>
		  </tr>

			<%
			
			ArrayList<Produto> pro = cp.pesquisar(auxPesquisaProduto);
			
			for (Produto produto : pro) {		
				out.println("<tr class='rows'>");
				out.println("<td>"+produto.getId().toString()+"</td>");
				out.println("<td>"+produto.getNome()+"</td>");
				out.println("<td>"+produto.getEstoque()+"</td>");
				out.println("<td>"+produto.getValor()+"</td>");
				out.print("<td fid="+'"');
				out.print(produto.getFornecedor().getId().toString().trim()+'"'+">");
				out.println(produto.getFornecedor()+"</td>");
				out.println("<td class='text-success'><i class='fas fa-plus-circle'></i></td>");
				out.println("</tr>");
			}
			%>		  
		  </tr>
		</table>
		
		<form style="display:none" method="post" 
			id="formAddProdutos" name="formAddProdutos">
			<input id="addProdutoId" name="addProdutoId">
			<input id="addProdutoValor" name="addProdutoValor">
			<input id="addProdutoAcao" name="addProdutoAcao">
		</form>
			
		<form method="post" 
			id="formPaginateProduto" name="formPaginateProduto">
			<nav aria-label="Page navigation example">
			  <ul class="pagination">
			    <li class="page-item"><button name="anteriorProduto" type="submit" class="page-link"><i class="fas fa-backward"></i>&nbsp;Anterior</button></li>
			    <input type="text" class="form-control" 
		    		id="paginaProduto"
		    		name="paginaProduto"
		    		value="<% out.print(cp.getPageNumber()); %>"
		    		readonly>
			    <li class="page-item"><button name="proximoProduto" type="submit" class="page-link" >Próxima&nbsp;<i class="fas fa-forward"></i></button></li>
			  </ul>
			</nav>
		</form>
		  </div>
		</div>
    
    </div>
    <div class="col-sm">
		<form method="post" 
			id="formPedido" name="formPedido">
		    <nav class="navbar navbar-light bg-light justify-content-between">
			  <button type="submit" 
			  		  class="btn btn-primary"
			  		  id="btIncluir"
			  		  name="btIncluir"><i class="fas fa-user-plus"></i>&nbsp;Incluir Pedido</button>
			  <button id="novoApagar" class="btn btn-danger btn-right"><i class="fas fa-trash-alt"></i>&nbsp;Apagar</button>
			  <button id="apagar" name="apagar" type="submit" style="display:none" ></button>
			</nav>
		  <div class="row">
			  <div class="form-group col-sm-4">
			    <label for="codigoInput">Pedido</label>
			    <input type="text" class="form-control" 
			    		id="codigoInput"
			    		name="id" 
			           placeholder="Código do Pedido"
			           value="<% out.print(pedidoAtual.getId()); %>"
			           readonly>
			  </div>
			  <div class="form-group col-sm-8">
			    <label for="dataInput">Data</label>
			    <input type="text" class="form-control" 
			    		id="dataInput"
			    		name="data"
			    		value="<% out.print(df.format(pedidoAtual.getDtVenda())); %>" 
			           placeholder="Data do Pedido">
			  </div>			    
		  </div>
		  <div class="form-group">
		    <label for="clienteInput">Cliente</label>
			<select id="clienteInput"
				name="cliente" 
				class="form-control">
				<%				
		    	fc.setPageSize(200);
		    	fc.setPageNumber(1);	
				ArrayList<Cliente> cliList = fc.pesquisar("");
				
				for (Cliente cliente : cliList) {	
					String aux="<option value="+'"'+
							cliente.getId().toString()+'"';
					if ((pedidoAtual != null) && (pedidoAtual.getCliente() != null)) {
						if (pedidoAtual.getCliente().getId() == cliente.getId() ) aux +="selected";
					}
					aux +=">"+cliente.getNome()+
							" ("+cliente.getId()+")</option>";
					out.println(aux);
				}
				%>					
			</select>
			<div class="row">
				<table id="tbItens" class="table">
					<tr>
					  <th>ID</th>
					  <th>Produto</th>
					  <th>Valor</th>
					  <th>Quantidade</th>
					  <th></th>
					</tr>
					
			<%
			if ((pedidoAtual != null) && (pedidoAtual.getItens() != null)) {
		
				//ArrayList<PedidoItem> auxItens=new ArrayList<PedidoItem>();
				//auxItens.addAll(pedidoAtual.getItens());								
				//Collections.sort(auxItens);		
				
				for (PedidoItem item: pedidoAtual.getItens()) {		
					out.println("<tr class='rows'>");
					out.println("<td>"+item.getId().toString()+"</td>");
					out.println("<td>"+item.getProduto()+"</td>");
					out.print("<td>");
					out.print("<input name='vlrItem' type='number' class='form-control' style='text-align:right;' value='" +item.getValor()+"'>");
					out.print("</td>");
					out.println("<td style='text-align:right;'>"+item.getQuantidade()+"</td>");
					out.println("<td class='text-danger'><i class='fas fa-minus-circle'></i></td>");
					out.println("</tr>");
				}
				
			}
			%>		  
						
				</table>
			</div>
			
			<div class="row">
				<div class="col-sm-4">
					<label>TOTAL</label>
				</div>
				<div class="col-sm-8">
				    <input type="text" class="form-control" 
				    		id="totalInput"
				    		name="total" 
				    		readonly
				            placeholder="Total do pedido">
				</div>
			</div>
 		  </div>	  
		</form>
    </div>
  </div>
</div>
</body>
<script>
$( document ).ready(function() {
	calculaTotal();
	$("input[name=vlrItem]").change(function() {
		calculaTotal();
	});
});

function calculaTotal(){
	var total=0.0;
	$("#tbItens tr.rows").each(function(i){
		let vl=$($(this).find("td")[2]).find("input").val();
		let qtd=$($(this).find("td")[3]).text();
		total +=(vl*qtd); 		
	});
	$("#totalInput").val(total);
}

$("#pesquisaPedido").keypress(function(event){
	var tecla=event.keyCode;
	/// verifica se teclou ENTER
	if (tecla=='13') {
		console.log("ENTER ... SUBMIT")
		$("#fPesqPedido").submit();
		
	}
});

$("#tabelaPedidos tr.rows").click(function(evento){
	var id=$($(this).find("td")[0]).text();
	$("#pedidoSelecionado").val(id);
	$("#fPesqPedido").submit();
	//$("tr.rows").removeClass("active");
	//$(this).addClass("active");
	//var dt=$($(this).find("td")[1]).text();
	//var clienteId=$($(this).find("td")[2]).attr("cid");
	//$("#codigoInput").val(id);
	//$("#dataInput").val(dt);
	//$("#clienteInput").val(clienteId);
});

$("#tabelaProdutos tr.rows").click(function(evento){
	var addid=$($(this).find("td")[0]).text();
	var addvalor=$($(this).find("td")[3]).text();
	$("#addProdutoId").val(addid);
	$("#addProdutoValor").val(addvalor);
	$("#addProdutoAcao").val("ADD");
	$("#formAddProdutos").submit();
});

$("#tbItens tr.rows").click(function(evento){
	var addid=$($(this).find("td")[0]).text();
	var addvalor=$($(this).find("td")[2]).text();
	$("#addProdutoId").val(addid);
	$("#addProdutoValor").val(addvalor);
	$("#addProdutoAcao").val("REMOVE");
	$("#formAddProdutos").submit();
});

$("#listaPedidos-tab").on("click", function(){
	$("#listaProdutos-tab").removeClass("active");
	$("#listaProdutos").removeClass("show active");
	$("#listaPedidos-tab").addClass("active");
	$("#listaPedidos").addClass("show active");
});
$("#listaProdutos-tab").on("click", function(){
	$("#listaPedidos").removeClass("show active");
	$("#listaPedidos-tab").removeClass("active");
	$("#listaProdutos-tab").addClass("active");
	$("#listaProdutos").addClass("show active");
});
//$("#btIncluir").click(function(evento){
//	$("tr.rows").removeClass("active");
//	$("#codigoInput").val("");
//	$("#nomeInput").val("");
//	$("#estoqueInput").val("");
//});

$("#novoApagar").click(function(evento){
	evento.preventDefault();
	confirmaApagar();
});
function confirmaApagar(){
	$.confirm({
	    title: 'Exclusão de Pedido',
	    content: 'Deseja realmente apagar este Pedido?',
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