package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Hibernate;

import antlr.collections.List;
import control.ClienteDAO;
import control.PedidoDAO;
import control.ProdutoDAO;
import model.Cliente;
import model.Pedido;
import model.PedidoItem;
import model.Produto;
import view.componente.MeuModelo;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class CrudPedidos extends JInternalFrame 
                       implements ActionListener, MouseListener 
                        {
	private JTextField tfPesquisa;
	
	private Pedido pedidoAtual;
	private PedidoDAO pedidoDAO;
	
	private Produto produtoAtual;
	private ProdutoDAO produtoDAO;
	
	private ClienteDAO clienteDAO;
	private ArrayList<Cliente> clientes;
	
	private JTable tbPedidos;
	private JTable tbProdutos;
	private JTable tbItensPedido;
	private JTextField tfProdutos;
	private JTextField tfPedidos;
	private JTextField tfPedidoId;
	private JTextField tfPedidoData;
	
	private JComboBox cbCliente;
	private JTextField tfTotal;

	public CrudPedidos() {
		setClosable(true);
		
		this.pedidoAtual = new Pedido();
		this.pedidoDAO = new PedidoDAO();
		
		this.produtoAtual = new Produto();
		this.produtoDAO = new ProdutoDAO();
		this.clienteDAO = new ClienteDAO();
		
		setResizable(true);
		setTitle("Cadastro de Pedidos");
		setBounds(100, 100, 986, 517);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(600);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane_1);
		
		JPanel painelPedidos = new JPanel();
		tabbedPane_1.addTab("Pedidos", null, painelPedidos, null);
		painelPedidos.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbPedidos = new JMenuBar();
		painelPedidos.add(mbPedidos, BorderLayout.NORTH);
		
		tfPedidos = new JTextField();
		mbPedidos.add(tfPedidos);
		tfPedidos.setColumns(10);
		
		JButton btPesquisarPedidos = new JButton("Pesquisar");
		mbPedidos.add(btPesquisarPedidos);
		
		tbPedidos = new JTable();
		tbPedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editarPedido();
			}
		});
		painelPedidos.add(new JScrollPane(tbPedidos), BorderLayout.CENTER);
		
		JPanel panelProdutos = new JPanel();
		tabbedPane_1.addTab("Produtos", null, panelProdutos, null);
		panelProdutos.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbProdutos = new JMenuBar();
		panelProdutos.add(mbProdutos, BorderLayout.NORTH);
		
		tfProdutos = new JTextField();
		tfProdutos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) pesquisarProdutos();
			}
		});
		mbProdutos.add(tfProdutos);
		tfProdutos.setColumns(10);
		
		JButton btPesquisarProdutos = new JButton("Pesquisar");
		btPesquisarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProdutos();
			}
		});
		mbProdutos.add(btPesquisarProdutos);
			
		tbProdutos = new JTable();
		tbProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				adicionarProduto();
			}
		});
		panelProdutos.add(new JScrollPane(tbProdutos), BorderLayout.CENTER);
		
		JPanel panelPedidoAtual = new JPanel();
		splitPane.setRightComponent(panelPedidoAtual);
		panelPedidoAtual.setLayout(new BorderLayout(0, 0));
		
		JMenuBar mbPedidoAtual = new JMenuBar();
		panelPedidoAtual.add(mbPedidoAtual, BorderLayout.NORTH);
		
		JButton btnNovoPedido = new JButton("Novo Pedido");
		btnNovoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparPedido();
			}
		});
		mbPedidoAtual.add(btnNovoPedido);
		
		JPanel panel_1 = new JPanel();
		panelPedidoAtual.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel pnTopoPedido = new JPanel();
		panel_1.add(pnTopoPedido, BorderLayout.NORTH);
		pnTopoPedido.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Pedido:");
		pnTopoPedido.add(lblNewLabel);
		
		tfPedidoId = new JTextField();
		pnTopoPedido.add(tfPedidoId);
		tfPedidoId.setColumns(10);
		
		JLabel lblData = new JLabel("Data:");
		pnTopoPedido.add(lblData);
		
		tfPedidoData = new JTextField();
		pnTopoPedido.add(tfPedidoData);
		tfPedidoData.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Cliente");
		pnTopoPedido.add(lblNewLabel_1);
		
		this.cbCliente = new JComboBox();
		pesquisarClientes();
		pnTopoPedido.add(cbCliente);
		
		JPanel pnTotalPedido = new JPanel();
		panel_1.add(pnTotalPedido, BorderLayout.SOUTH);
		pnTotalPedido.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblTotal = new JLabel("Total:");
		pnTotalPedido.add(lblTotal);
		
		tfTotal = new JTextField();
		pnTotalPedido.add(tfTotal);
		tfTotal.setColumns(10);
		
		tbItensPedido = new JTable();
		tbItensPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removerProduto();
			}
		});
		panel_1.add(new JScrollPane(tbItensPedido), BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		panelPedidoAtual.add(menuBar, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Gravar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravarPedido();
			}
		});
		menuBar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarEdicao();
			}
		});
		menuBar.add(btnNewButton_1);

		pesquisarPedidos();
		pesquisarProdutos();
	}	
	
	public void pesquisarClientes() {
		clientes=this.clienteDAO.pesquisar("");
		DefaultComboBoxModel md = new DefaultComboBoxModel();
		for (Cliente cliente : clientes) {
			md.addElement(cliente);
		}		
		
		this.cbCliente = new JComboBox();
		this.cbCliente.setModel(md);

	}
	
	public void pesquisarPedidos() {
		ArrayList<Pedido> objetos=this.pedidoDAO.pesquisar(this.tfPedidos.getText());
		DefaultTableModel model = new MeuModelo();
		model.addColumn("PEDIDO");
		model.addColumn("DATA");
		model.addColumn("CLIENTE");
		this.tbPedidos.setModel(model);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Pedido objeto : objetos) {
			model.addRow(new Object[]{objeto.getId(),
									  df.format(objeto.getDtVenda()),
					                  objeto.getCliente().getNome()});			
		}
		
	}
	
	public void pesquisarProdutos() {
		ArrayList<Produto> objetos=this.produtoDAO.pesquisar(this.tfProdutos.getText());
		DefaultTableModel model = new MeuModelo();
		model.addColumn("ID");
		model.addColumn("Nome");
		model.addColumn("Estoque");
		model.addColumn("Valor");
		model.addColumn("Fornecedor");
		this.tbProdutos.setModel(model);
		
		for (Produto objeto : objetos) {
			model.addRow(new Object[]{objeto.getId(),
									  objeto.getNome(),
					                  objeto.getEstoque(),
					                  objeto.getValor()});			
		}
		
	}
	
	public void cancelarEdicao() {
		this.pedidoAtual = pedidoDAO.getById(this.pedidoAtual.getId());
		setPedidoAtual();
	}
	
	public void editarPedido() {
		int linha = this.tbPedidos.getSelectedRow();
		if (linha>=0) {
			Integer id = (Integer) this.tbPedidos.getValueAt(linha, 0);
			this.pedidoAtual = pedidoDAO.getById(id);
			setPedidoAtual();
		} else limparPedido();
	}
	
	public void setPedidoAtual () {
		this.tfPedidoId.setText(this.pedidoAtual.getId().toString());
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		this.tfPedidoData.setText(df.format(this.pedidoAtual.getDtVenda()));
		
		for (int i = 0; i < this.cbCliente.getItemCount(); i++) {
			Cliente item=(Cliente)this.cbCliente.getItemAt(i);
			if (item.getId() == this.pedidoAtual.getCliente().getId()) {
				this.cbCliente.setSelectedIndex(i);
			}
		}
			
		mostarItensPedido();
	}
	
	public void getPedidoAtual() {
		this.pedidoAtual.setId(Integer.parseInt(this.tfPedidoId.getText()));
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.pedidoAtual.setDtVenda(df.parse(this.tfPedidoData.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/// atribuir o cliente
		this.pedidoAtual.setCliente((Cliente)this.cbCliente.getSelectedItem());	

		/// atribuir os itens
		for (int i = 0; i < this.tbItensPedido.getModel().getRowCount(); i++) {
			
			Integer aId = (Integer)this.tbItensPedido.getModel().getValueAt(i, 0);
			Produto  aProduto = (Produto)this.tbItensPedido.getModel().getValueAt(i, 1);
			BigDecimal aValor = (BigDecimal)this.tbItensPedido.getModel().getValueAt(i, 2);
			Integer aQuantidade = (Integer)this.tbItensPedido.getModel().getValueAt(i, 3);
			
			if (aId != null) {
				/// atualizar o existente
				for (PedidoItem item : this.pedidoAtual.getItens()) {
					if (item.getId() == aId) {
						item.setQuantidade(aQuantidade);
						item.setValor(aValor);
					}
				}
			} else {
				/// incluir novo item
				this.pedidoAtual.getItens().add(
						new PedidoItem(null, aQuantidade, 
								aValor, aProduto, this.pedidoAtual));
			}
			
		}
		
	}
	
	public void calculaTotal() {
		float total=0;
		
		for (int i = 0; i < this.tbItensPedido.getModel().getRowCount(); i++) {
			BigDecimal aValor = (BigDecimal)this.tbItensPedido.getModel().getValueAt(i, 2);
			Integer aQuantidade = (Integer)this.tbItensPedido.getModel().getValueAt(i, 3);
			total = total+(aValor.floatValue() * aQuantidade.floatValue());
		}
		this.tfTotal.setText(Float.toString(total));
	}
	
	public void gravarPedido() {
		getPedidoAtual();
		this.pedidoDAO.gravar(this.pedidoAtual);
		pesquisarPedidos();
		this.pedidoAtual = pedidoDAO.getById(this.pedidoAtual.getId());
		setPedidoAtual();
	}

	
	public void limparPedido() {
		this.pedidoAtual = new Pedido();
		this.pedidoAtual.setId(0);		
		this.pedidoAtual.setDtVenda(new Date());;
		this.pedidoAtual.setItens(new HashSet<>());
		this.tfPedidoId.setText("");		
		this.tfPedidoData.setText("");
		mostarItensPedido();
	}
	
	public void mostarItensPedido() {
		DefaultTableModel model = new MeuModelo();
		model.addColumn("ID");
		model.addColumn("Produto");
		model.addColumn("Valor");
		model.addColumn("Quantidade");
		this.tbItensPedido.setModel(model);
				
		for (PedidoItem objeto : pedidoAtual.getItens()) {
			model.addRow(new Object[]{objeto.getId(),
									  objeto.getProduto(),
									  objeto.getValor(),
									  objeto.getQuantidade()});			
		}
		
		calculaTotal();
	}
		
	public void adicionarProduto() {
		int linha = this.tbProdutos.getSelectedRow();
		
		if (linha>=0) {
			Integer id = (Integer) this.tbProdutos.getValueAt(linha, 0);
			/// verificar se o produto ja esta na lista
			boolean flag=false;
			for (PedidoItem item : pedidoAtual.getItens()) {
				if (item.getProduto().getId() == id) {
					flag=true;
					item.setQuantidade(item.getQuantidade()+1);
					break;
				}
			}
			if (!flag) {
				Produto np = produtoDAO.getById(id);
				PedidoItem npi = new PedidoItem();
				npi.setProduto(np);
				npi.setValor(np.getValor());
				npi.setQuantidade(1);
				this.pedidoAtual.getItens().add(npi);				
			}
			setPedidoAtual();
		}
	}
	
	public void removerProduto() {
		int linha = this.tbItensPedido.getSelectedRow();
		
		if (linha>=0) {
			Integer qtd = (Integer) this.tbItensPedido.getValueAt(linha, 3);
			if (qtd>0) {
				this.tbItensPedido.setValueAt(qtd-1,linha, 3);
			} 
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
//		if      (e.getActionCommand()=="Pesquisar") pesquisar();
//		else if (e.getActionCommand()=="Gravar") 	gravar();
//		else if (e.getActionCommand()=="Cancelar") 	cancelar();
//		else if (e.getActionCommand()=="Novo Pedido") 	limpar();
//		else if (e.getActionCommand()=="Excluir") 	excluir();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println(e);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println(e);
		
	}

}
