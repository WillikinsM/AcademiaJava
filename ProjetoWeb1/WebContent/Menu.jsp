<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.2/css/all.css" rel="stylesheet" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script type="text/javascript" src="jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
    
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.jsp">Controle de Estoque</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#"
           	id="nvCadastros" 
           	role="button" 
            data-bs-toggle="dropdown" 
            aria-expanded="false">
            Cadastros
          </a>
          <ul class="dropdown-menu" aria-labelledby="nvCadastros">
            <li><a class="dropdown-item" href="./CrudClientes.jsp">Cadastro de Clientes</a></li>
            <li><a class="dropdown-item" href="./CrudFornecedores.jsp">Cadastro de Fornecedores</a></li>
            <li><a class="dropdown-item" href="./CrudProdutos.jsp">Cadastro de Produtos</a></li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="./CrudPedidos.jsp">Pedidos</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#"
           	id="nvRelatorios" 
           	role="button" 
           data-bs-toggle="dropdown" aria-expanded="false">
            Relatorios
          </a>
          <ul class="dropdown-menu" aria-labelledby="nvRelatorios">
            <li><a class="dropdown-item" href="#">Relatorio de Clientes</a></li>
            <li><a class="dropdown-item" href="#">Relatorio de Fornecedores</a></li>
            <li><a class="dropdown-item" href="#">Relatorio de Produtos</a></li>
          </ul>
        </li>
        
      </ul>
    </div>
  </div>
</nav>