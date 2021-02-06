package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="fornecedores")
public class Fornecedor {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="razao_social")
	private String razaoSocial;
	
	@Column(name="telefone")
	private String telefone;
		
	@Column(name="cidade")
	private String cidade;

	public Fornecedor() {
		super();
	}

	public Fornecedor(Integer id, String razao_social, String telefone, String cidade) {
		super();
		this.id = id;
		this.razaoSocial = razao_social;
		this.telefone = telefone;
		this.cidade = cidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razao_social) {
		this.razaoSocial = razao_social;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return getRazaoSocial()+" ("+getId()+")";
	}
	
}
