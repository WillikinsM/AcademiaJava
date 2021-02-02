package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="produtos")
public class Produto {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="estoque")
	private Integer estoque;
		
	@Column(name="valor")
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id", 
					nullable=true, 
					insertable = false, 
					updatable = false)
	private Fornecedor fornecedor;
	
	@Column(name="fornecedor_id")
	private int fornecedorId;
	
	public Produto() {
		super();
	}

	public Produto(Integer id, String nome, Integer estoque, BigDecimal valor, Fornecedor fornecedor) {
		super();
		this.id = id;
		this.nome = nome;
		this.estoque = estoque;
		this.valor = valor;
		this.fornecedor = fornecedor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNome()+" ("+getId()+")";
	}
	
	public int getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(int fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
}
