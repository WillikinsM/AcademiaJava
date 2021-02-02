package control;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Fornecedor;

public class FornecedorDAO {
	
	public ArrayList<Fornecedor> pesquisar(String pesq) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		
		Query qe = em.createQuery(
				"SELECT obj FROM Fornecedor obj WHERE obj.razaoSocial LIKE :parametro");
		qe.setParameter("parametro", "%"+pesq+"%");
		
		@SuppressWarnings("unchecked")
		ArrayList<Fornecedor> obj=(ArrayList<Fornecedor>) qe.getResultList();
		
		em.close();
		fa.close();
		
		return obj;
	}
	
	public Fornecedor getById(Integer id) {
		EntityManagerFactory fa = Persistence.createEntityManagerFactory("aula");
		EntityManager em = fa.createEntityManager();
		Fornecedor obj = em.find(Fornecedor.class, id);	
		em.close();
		fa.close();
		return obj;
	}

	public void gravar(Fornecedor obj) {
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
		Fornecedor obj = em.find(Fornecedor.class, id);
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
		em.close();
		fa.close();
	}

	
}
