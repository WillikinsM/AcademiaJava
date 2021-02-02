package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.ClienteDAO;
import model.Cliente;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;

public class CrudClientes extends JInternalFrame 
                       implements ActionListener, MouseListener 
                        {
	private JTable table;
	private JTextField tfPesquisa;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JTextField tfTelefone;
	
	private Cliente objAtual;
	private ClienteDAO objDAO;

	public CrudClientes() {
		setClosable(true);
		
		this.objAtual = new Cliente();
		this.objDAO = new ClienteDAO();
		
		setResizable(true);
		setTitle("Cadastro de Clientes");
		setBounds(100, 100, 986, 517);
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		tfPesquisa = new JTextField();
		menuBar.add(tfPesquisa);
		tfPesquisa.setColumns(50);
		
		JPanel panel = new JPanel();
		menuBar.add(panel);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		panel.add(btnPesquisar);
		
		JButton btnNovo = new JButton("Novo Cliente");
		btnNovo.addActionListener(this);
		panel.add(btnNovo);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Código:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		GridBagConstraints gbc_tfCodigo = new GridBagConstraints();
		gbc_tfCodigo.fill = GridBagConstraints.BOTH;
		gbc_tfCodigo.insets = new Insets(0, 0, 5, 0);
		gbc_tfCodigo.gridx = 1;
		gbc_tfCodigo.gridy = 0;
		panel_1.add(tfCodigo, gbc_tfCodigo);
		tfCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.fill = GridBagConstraints.BOTH;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panel_1.add(lblNome, gbc_lblNome);
		
		tfNome = new JTextField();
		GridBagConstraints gbc_tfNome = new GridBagConstraints();
		gbc_tfNome.gridwidth = 3;
		gbc_tfNome.fill = GridBagConstraints.BOTH;
		gbc_tfNome.insets = new Insets(0, 0, 5, 0);
		gbc_tfNome.gridx = 1;
		gbc_tfNome.gridy = 1;
		panel_1.add(tfNome, gbc_tfNome);
		tfNome.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		GridBagConstraints gbc_lblTelefone = new GridBagConstraints();
		gbc_lblTelefone.fill = GridBagConstraints.BOTH;
		gbc_lblTelefone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefone.gridx = 0;
		gbc_lblTelefone.gridy = 2;
		panel_1.add(lblTelefone, gbc_lblTelefone);
		
		tfTelefone = new JTextField();
		GridBagConstraints gbc_tfTelefone = new GridBagConstraints();
		gbc_tfTelefone.gridwidth = 3;
		gbc_tfTelefone.insets = new Insets(0, 0, 5, 0);
		gbc_tfTelefone.fill = GridBagConstraints.BOTH;
		gbc_tfTelefone.gridx = 1;
		gbc_tfTelefone.gridy = 2;
		panel_1.add(tfTelefone, gbc_tfTelefone);
		tfTelefone.setColumns(10);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(this);
		GridBagConstraints gbc_btnGravar = new GridBagConstraints();
		gbc_btnGravar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGravar.gridx = 0;
		gbc_btnGravar.gridy = 3;
		panel_1.add(btnGravar, gbc_btnGravar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 3;
		panel_1.add(btnCancelar, gbc_btnCancelar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(this);
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.insets = new Insets(0, 0, 0, 5);
		gbc_btnExcluir.gridx = 3;
		gbc_btnExcluir.gridy = 3;
		panel_1.add(btnExcluir, gbc_btnExcluir);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(this);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		pesquisar();
		
	}	

	public void pesquisar() {			
		ArrayList<Cliente> objetos=this.objDAO.pesquisar(this.tfPesquisa.getText());
		DefaultTableModel model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		model.addColumn("ID");
		model.addColumn("Nome");
		model.addColumn("Telefone");
		
		this.table.setModel(model);
		
		
		for (Cliente objeto : objetos) {
			model.addRow(new Object[]{objeto.getId(),
									  objeto.getNome(),
					                  objeto.getTelefone()});			
		}
	}
	
	public void limpar() {
		this.objAtual = new Cliente();
		setCliente();
	}
	
	public void setCliente() {
		this.tfCodigo.setText(objAtual.getId().toString());
		this.tfNome.setText(objAtual.getNome());
		this.tfTelefone.setText(objAtual.getTelefone());
	}
	
	public void getCliente() {
		objAtual.setId(Integer.parseInt(this.tfCodigo.getText()));
		objAtual.setNome(this.tfNome.getText());
		objAtual.setTelefone(this.tfTelefone.getText());
	}
	
	public void editar() {
		int linha = this.table.getSelectedRow();
		if (linha>=0) {
			this.objAtual = new Cliente();
			objAtual.setId((Integer)this.table.getValueAt(linha, 0));
			objAtual.setNome((String) this.table.getValueAt(linha, 1));
			objAtual.setTelefone((String) this.table.getValueAt(linha, 2));
			setCliente();
		} else limpar();
	}
	
	public void cancelar() {
		editar();
	}
	
	public void gravar() {
		getCliente();
		this.objDAO.gravar(objAtual);
		pesquisar();
	}
	
	public void excluir() {
		getCliente();
		this.objDAO.excluir(objAtual.getId());
		pesquisar();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		if      (e.getActionCommand()=="Pesquisar") pesquisar();
		else if (e.getActionCommand()=="Gravar") 	gravar();
		else if (e.getActionCommand()=="Cancelar") 	cancelar();
		else if (e.getActionCommand()=="Novo Cliente") 	limpar();
		else if (e.getActionCommand()=="Excluir") 	excluir();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		editar();
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
