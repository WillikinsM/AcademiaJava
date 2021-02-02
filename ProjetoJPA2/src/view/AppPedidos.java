package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import net.sf.jasperreports.swing.JRViewer;
import relatorios.RodarRelatorio;

import javax.swing.JDesktopPane;
import java.awt.BorderLayout;

public class AppPedidos implements ActionListener, MenuListener {

	private JFrame frmSistenaDeGesto;
	private JDesktopPane desktopPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppPedidos window = new AppPedidos();
					window.frmSistenaDeGesto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppPedidos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistenaDeGesto = new JFrame();
		frmSistenaDeGesto.setTitle("Sistema de gestão de pedidos (JPA)");
		frmSistenaDeGesto.setBounds(100, 100, 1200, 800);
		frmSistenaDeGesto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSistenaDeGesto.setJMenuBar(menuBar);
		
		JMenu mnCadastros = new JMenu("Cadastros");
		menuBar.add(mnCadastros);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(this);
		mnCadastros.add(mntmClientes);
		
		JMenuItem mntmFornecedores = new JMenuItem("Fornecedores");
		mntmFornecedores.addActionListener(this);
		mnCadastros.add(mntmFornecedores);
		
		JMenuItem mntmProdutos = new JMenuItem("Produtos");
		mntmProdutos.addActionListener(this);
		mnCadastros.add(mntmProdutos);
		
		JMenu mnPedidos = new JMenu("Pedidos");
		mnPedidos.addMenuListener(this);
		menuBar.add(mnPedidos);
		
		JMenu mnRelatrios = new JMenu("Relatórios");
		menuBar.add(mnRelatrios);
		
		JMenuItem mntmListagemDeClientes = new JMenuItem("Listagem de Clientes");
		mntmListagemDeClientes.addActionListener(this);
		mnRelatrios.add(mntmListagemDeClientes);
		
		JMenuItem mntmListagemDeFornecedores = new JMenuItem("Listagem de Fornecedores");
		mntmListagemDeFornecedores.addActionListener(this);
		mnRelatrios.add(mntmListagemDeFornecedores);
		
		JMenuItem mntmProdutosMaisVendidos = new JMenuItem("Produtos Mais Vendidos");
		mntmProdutosMaisVendidos.addActionListener(this);
		mnRelatrios.add(mntmProdutosMaisVendidos);
		
		JMenuItem mntmVendasPorMs = new JMenuItem("Vendas por mês");
		mnRelatrios.add(mntmVendasPorMs);
		
		desktopPane = new JDesktopPane();
		frmSistenaDeGesto.getContentPane().add(desktopPane, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()=="Clientes") {
			CrudClientes cl = new CrudClientes();
			this.desktopPane.add(cl);
			cl.setVisible(true);
		} else if (e.getActionCommand()=="Fornecedores") {
			CrudFornecedores fo = new CrudFornecedores();
			this.desktopPane.add(fo);
			fo.setVisible(true);
		} else if (e.getActionCommand()=="Produtos") {
			CrudProdutos fo = new CrudProdutos();
			this.desktopPane.add(fo);
			fo.setVisible(true);
		} else if (e.getActionCommand()=="Listagem de Clientes") {
			JInternalFrame jif = 
					new JInternalFrame("Listagem de Clientes", true, true);
			jif.setBounds(100, 100, 986, 517);
			jif.setVisible(true);
			this.desktopPane.add(jif);

			RodarRelatorio rr = new RodarRelatorio();
			JRViewer jrv = rr.executar("RelClientes.jrxml");
			if (jrv != null) {
				jif.setContentPane(jrv);				
			}
		} else if (e.getActionCommand()=="Listagem de Fornecedores") {
			RodarRelatorio rr = new RodarRelatorio();
			JRViewer jrv = rr.executar("RelFornecedores.jrxml");
			if (jrv != null) {
				JInternalFrame jif = 
						new JInternalFrame("Listagem de Fornecedores", true, true);
				jif.setBounds(100, 100, 986, 517);
				jif.setVisible(true);
				jif.setContentPane(jrv);
				this.desktopPane.add(jif);
			}
			
		} else if (e.getActionCommand()=="Produtos Mais Vendidos") {
			RodarRelatorio rr = new RodarRelatorio();
			JRViewer jrv = rr.executar("RelProdutosMaisVendidos.jrxml");
			if (jrv != null) {
				JInternalFrame jif = 
						new JInternalFrame("Produtos Mais Vendidos", true, true);
				jif.setBounds(100, 100, 986, 517);
				jif.setVisible(true);
				jif.setContentPane(jrv);
				this.desktopPane.add(jif);
			}
			
		}		
	
	}

	@Override
	public void menuSelected(MenuEvent e) {
		// TODO Auto-generated method stub
		CrudPedidos pe = new CrudPedidos();
		this.desktopPane.add(pe);
		pe.setVisible(true);
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

}
