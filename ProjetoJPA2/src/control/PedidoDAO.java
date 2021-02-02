package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Hibernate;

import model.Pedido;

public class PedidoDAO {
	
	public ArrayList<Pedido> pesquisar(String pesq) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT obj FROM Pedido obj WHERE obj.cliente.nome LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
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
