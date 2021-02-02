package model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name ="pedidos")
public class Pedido {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="dt_venda")
	private Date dtVenda;
	
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable=true)
	private Cliente cliente;
	
	@OneToMany(mappedBy="pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PedidoItem> itens;
	
//	private Collection<PedidoItem> pedidoItem;

	public Pedido() {
		super();
	}
	
	public Pedido(Integer id, Date dtVenda, Cliente cliente) {
		super();
		this.id = id;
		this.dtVenda = dtVenda;
		this.cliente = cliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtVenda() {
		return dtVenda;
	}

	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<PedidoItem> getItens() {
		return itens;
	}

	public void setItens(Set<PedidoItem> itens) {
		this.itens = itens;
	}

//	public Collection<PedidoItem> getPedidoItem() {
//		return pedidoItem;
//	}
//
//	public void setPedidoItem(Collection<PedidoItem> pedidoItem) {
//		this.pedidoItem = pedidoItem;
//	}
	
}
