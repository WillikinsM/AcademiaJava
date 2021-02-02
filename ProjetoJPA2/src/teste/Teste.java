package teste;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import control.ClienteDAO;
import model.Cliente;

public class Teste {

	public static void main(String[] args) {
		
		ClienteDAO cd =  new ClienteDAO();
		
		ArrayList<Cliente> cli = cd.pesquisar("ro");
		
		for (Cliente cliente : cli) {
			System.out.println(cliente.getNome());
		}
		
	}

}
