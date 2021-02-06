package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Hibernate;

import model.Pedido;

public class PedidoDAO {
	
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



	public ArrayList<Pedido> pesquisar(String pesq) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT obj FROM Pedido obj WHERE obj.cliente.nome LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
		qe.setMaxResults(this.pageSize);
		qe.setFirstResult((this.pageNumber-1)*this.pageSize);
		
		@SuppressWarnings("unchecked")
		ArrayList<Pedido> obj=(ArrayList<Pedido>) qe.getResultList();
		
		em.close();
		fa.close();
		
		return obj;
	}
	
	public Pedido getById(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Pedido obj = em.find(Pedido.class, id);	
		if (obj != null)
			Hibernate.initialize(obj.getItens());

		em.close();
		fa.close();
		return obj;
	}

	public void gravar(Pedido obj) {
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
		Pedido obj = em.find(Pedido.class, id);
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	
}
