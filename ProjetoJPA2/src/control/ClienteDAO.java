package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cliente;

public class ClienteDAO {
	
	public ArrayList<Cliente> pesquisar(String pesq) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT cli FROM Cliente cli WHERE cli.nome LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
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
