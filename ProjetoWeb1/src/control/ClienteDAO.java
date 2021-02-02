package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import model.Cliente;

public class ClienteDAO {
	
	private int pageSize=5;
	private int pageNumber=1;
	private long maxPages=0;
	
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

	public ArrayList<Cliente> pesquisar(String pesq) {
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
