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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.FornecedorDAO;
import model.Fornecedor;

public class CrudFornecedores extends JInternalFrame 
                       implements ActionListener, MouseListener 
                        {
	private JTable table;
	private JTextField tfPesquisa;
	private JTextField tfCodigo;
	private JTextField tfRazaoSocial;
	private JTextField tfTelefone;
	
	private Fornecedor objAtual;
	private FornecedorDAO objDAO;

	public CrudFornecedores() {
		setClosable(true);
		
		this.objAtual = new Fornecedor();
		this.objDAO = new FornecedorDAO();
		
		setResizable(true);
		setTitle("Cadastro de Fornecedores");
		setBounds(100, 100, 986, 517);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		tfPesquisa = new JTextField();
		panel.add(tfPesquisa);
		tfPesquisa.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		panel.add(btnPesquisar);
		
		JButton btnNovo = new JButton("Novo Fornecedor");
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
		
		JLabel lblRazaoSocial = new JLabel("Razao Social");
		GridBagConstraints gbc_lblRazaoSocial = new GridBagConstraints();
		gbc_lblRazaoSocial.fill = GridBagConstraints.BOTH;
		gbc_lblRazaoSocial.insets = new Insets(0, 0, 5, 5);
		gbc_lblRazaoSocial.gridx = 0;
		gbc_lblRazaoSocial.gridy = 1;
		panel_1.add(lblRazaoSocial, gbc_lblRazaoSocial);
		
		tfRazaoSocial = new JTextField();
		GridBagConstraints gbc_tfRazaoSocial = new GridBagConstraints();
		gbc_tfRazaoSocial.gridwidth = 3;
		gbc_tfRazaoSocial.fill = GridBagConstraints.BOTH;
		gbc_tfRazaoSocial.insets = new Insets(0, 0, 5, 0);
		gbc_tfRazaoSocial.gridx = 1;
		gbc_tfRazaoSocial.gridy = 1;
		panel_1.add(tfRazaoSocial, gbc_tfRazaoSocial);
		tfRazaoSocial.setColumns(10);
		
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
		table.addMouseListener(this);
		getContentPane().add(table, BorderLayout.CENTER);
		
		pesquisar();
		
	}	

	public void pesquisar() {			
		ArrayList<Fornecedor> objetos=this.objDAO.pesquisar(this.tfPesquisa.getText());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Razão Social");
		model.addColumn("Telefone");
		this.table.setModel(model);
		
		for (Fornecedor objeto : objetos) {
			model.addRow(new Object[]{objeto.getId(),
									  objeto.getRazaoSocial(),
					                  objeto.getTelefone()});			
		}
	}
	
	public void limpar() {
		this.objAtual = new Fornecedor();
		setObj();
	}
	
	public void setObj() {
		this.tfCodigo.setText(objAtual.getId().toString());
		this.tfRazaoSocial.setText(objAtual.getRazaoSocial());
		this.tfTelefone.setText(objAtual.getTelefone());
	}
	
	public void getObj() {
		objAtual.setId(Integer.parseInt(this.tfCodigo.getText()));
		objAtual.setRazaoSocial(this.tfRazaoSocial.getText());
		objAtual.setTelefone(this.tfTelefone.getText());
	}
	
	public void editar() {
		int linha = this.table.getSelectedRow();
		if (linha>=0) {
			this.objAtual = new Fornecedor();
			objAtual.setId((Integer)this.table.getValueAt(linha, 0));
			objAtual.setRazaoSocial((String) this.table.getValueAt(linha, 1));
			objAtual.setTelefone((String) this.table.getValueAt(linha, 2));
			setObj();
		} else limpar();
	}
	
	public void cancelar() {
		editar();
	}
	
	public void gravar() {
		getObj();
		this.objDAO.gravar(objAtual);
		pesquisar();
	}
	
	public void excluir() {
		getObj();
		this.objDAO.excluir(objAtual.getId());
		pesquisar();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		if      (e.getActionCommand()=="Pesquisar") pesquisar();
		else if (e.getActionCommand()=="Gravar") 	gravar();
		else if (e.getActionCommand()=="Cancelar") 	cancelar();
		else if (e.getActionCommand()=="Novo Fornecedor") 	limpar();
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
