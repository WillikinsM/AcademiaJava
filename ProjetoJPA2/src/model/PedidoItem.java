package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="pedidos_itens")
public class PedidoItem {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="quantidade")
	private Integer quantidade;
		
	@Column(name="valor")
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name="produto_id", nullable=true)
	private Produto produto;
		
	@ManyToOne
	@JoinColumn(name="pedido_id", nullable=true)
	private Pedido pedido;

	public PedidoItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PedidoItem(Integer id, Integer quantidade, BigDecimal valor, Produto produto, Pedido pedido) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valor = valor;
		this.produto = produto;
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	

}
