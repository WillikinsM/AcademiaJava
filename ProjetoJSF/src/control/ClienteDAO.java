package control;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import model.Cliente;

@ManagedBean
@SessionScoped
public class ClienteDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int pageSize=5;
	private int pageNumber=1;
	private long maxPages=0;
	
	private ArrayList<Cliente>  lClientes;
	
	public ArrayList<Cliente> getlClientes() {
		if (lClientes == null) pesquisar();
		return lClientes;
	}

	public void setlClientes(ArrayList<Cliente> lClientes) {
		this.lClientes = lClientes;
	}

	private String pesq="";
	
	public String getPesq() {
		return pesq;
	}

	public void setPesq(String pesq) {
		this.pesq = pesq;
	}

	private Cliente clienteAtual;
	
	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	public void setClienteAtual(Cliente clienteAtual) {
		this.clienteAtual = clienteAtual;
	}

	public long getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(long maxPages) {
		this.maxPages = maxPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public ArrayList<Cliente> pesquisar() {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT cli FROM Cliente cli WHERE cli.nome LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
		/// para contar as paginas ----------------------------
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Cliente.class)));
		Long registros=em.createQuery(cq).getSingleResult();
		this.maxPages=(registros/pageSize);
		///----------------------------------------------------
		
		qe.setMaxResults(this.pageSize);
		qe.setFirstResult((this.pageNumber-1)*this.pageSize);
		@SuppressWarnings("unchecked")
		ArrayList<Cliente> obj=(ArrayList<Cliente>) qe.getResultList();
		
		em.close();
		fa.close();
		
		this.lClientes = obj;
		return obj;
	}
	
	public Cliente getById(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		Cliente obj = em.find(Cliente.class, id);	
		em.close();
		fa.close();
		return obj;
	}
	
	public void apaga() {
		if(this.clienteAtual != null) this.excluir(this.clienteAtual.getId());
		pesquisar();
	}
	public void grava() {
		this.gravar(this.clienteAtual);
		pesquisar();
	}
	
	public void novo() {
		this.clienteAtual = new Cliente();
	}
	
	
	public void gravar(Cliente clienteAtual) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		em.getTransaction().begin();
		em.merge(clienteAtual);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	public void excluir(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		Cliente obj = em.find(Cliente.class, id);
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	
}
