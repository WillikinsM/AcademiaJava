package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Produto;

public class ProdutoDAO {
	
	private int pageSize=5;
	private int pageNumber=1;
	
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
	
	public ArrayList<Produto> pesquisar(String pesq) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT obj FROM Produto obj WHERE obj.nome LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
		qe.setMaxResults(this.pageSize);
		qe.setFirstResult((this.pageNumber-1)*this.pageSize);
		
		@SuppressWarnings("unchecked")
		ArrayList<Produto> obj=(ArrayList<Produto>) qe.getResultList();
		
		em.close();
		fa.close();
		
		return obj;
	}
	
	public Produto getById(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		Produto obj = em.find(Produto.class, id);	
		em.close();
		fa.close();
		return obj;
	}

	public void gravar(Produto obj) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	public void excluir(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		Produto obj = em.find(Produto.class, id);
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	
}
