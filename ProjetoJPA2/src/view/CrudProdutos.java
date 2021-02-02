package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.ClienteDAO;
import control.FornecedorDAO;
import control.ProdutoDAO;
import model.Cliente;
import model.Fornecedor;
import model.Produto;
import view.componente.MeuModelo;
import javax.swing.JComboBox;

public class CrudProdutos extends JInternalFrame 
                       implements ActionListener, MouseListener 
                        {
	private JTable table;
	private JTextField tfPesquisa;
	private JTextField tfCodigo;
	private JTextField tfNome;
	private JTextField tfEstoque;
	private JComboBox cbFornecedor; 
	
	private Produto objAtual;
	private ProdutoDAO objDAO;
	private JTextField tfValor;
	
	private FornecedorDAO fornecedorDAO;
	private ArrayList<Fornecedor> fornecedores;

	public CrudProdutos() {
		setClosable(true);
		
		this.objAtual = new Produto();
		this.objDAO = new ProdutoDAO();
		
		this.fornecedorDAO = new FornecedorDAO();
		
		setResizable(true);
		setTitle("Cadastro de Produtos");
		setBounds(100, 100, 986, 517);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		tfPesquisa = new JTextField();
		panel.add(tfPesquisa);
		tfPesquisa.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		panel.add(btnPesquisar);
		
		JButton btnNovo = new JButton("Novo Produto");
		btnNovo.addActionListener(this);
		panel.add(btnNovo);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
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
		gbc_tfCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_tfCodigo.gridx = 1;
		gbc_tfCodigo.gridy = 0;
		panel_1.add(tfCodigo, gbc_tfCodigo);
		tfCodigo.setColumns(10);
		
		JLabel lblRazaoSocial = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.fill = GridBagConstraints.BOTH;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panel_1.add(lblRazaoSocial, gbc_lblNome);
		
		tfNome = new JTextField();
		GridBagConstraints gbc_tfNome = new GridBagConstraints();
		gbc_tfNome.gridwidth = 3;
		gbc_tfNome.fill = GridBagConstraints.BOTH;
		gbc_tfNome.insets = new Insets(0, 0, 5, 0);
		gbc_tfNome.gridx = 1;
		gbc_tfNome.gridy = 1;
		panel_1.add(tfNome, gbc_tfNome);
		tfNome.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Estoque:");
		GridBagConstraints gbc_lblEstoque = new GridBagConstraints();
		gbc_lblEstoque.fill = GridBagConstraints.BOTH;
		gbc_lblEstoque.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstoque.gridx = 0;
		gbc_lblEstoque.gridy = 2;
		panel_1.add(lblTelefone, gbc_lblEstoque);
		
		tfEstoque = new JTextField();
		GridBagConstraints gbc_tfEstoque = new GridBagConstraints();
		gbc_tfEstoque.gridwidth = 3;
		gbc_tfEstoque.insets = new Insets(0, 0, 5, 0);
		gbc_tfEstoque.fill = GridBagConstraints.BOTH;
		gbc_tfEstoque.gridx = 1;
		gbc_tfEstoque.gridy = 2;
		panel_1.add(tfEstoque, gbc_tfEstoque);
		tfEstoque.setColumns(10);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(this);
		
		JLabel lblValor = new JLabel("Valor:");
		GridBagConstraints gbc_lblValor = new GridBagConstraints();
		gbc_lblValor.anchor = GridBagConstraints.WEST;
		gbc_lblValor.insets = new Insets(0, 0, 5, 5);
		gbc_lblValor.gridx = 0;
		gbc_lblValor.gridy = 3;
		panel_1.add(lblValor, gbc_lblValor);
		
		tfValor = new JTextField();
		GridBagConstraints gbc_tfValor = new GridBagConstraints();
		gbc_tfValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfValor.gridwidth = 3;
		gbc_tfValor.insets = new Insets(0, 0, 5, 0);
		gbc_tfValor.gridx = 1;
		gbc_tfValor.gridy = 3;
		panel_1.add(tfValor, gbc_tfValor);
		tfValor.setColumns(10);
		
		JLabel lblFornecedor = new JLabel("Fornecedor:");
		GridBagConstraints gbc_lblFornecedor = new GridBagConstraints();
		gbc_lblFornecedor.insets = new Insets(0, 0, 5, 5);
		gbc_lblFornecedor.anchor = GridBagConstraints.EAST;
		gbc_lblFornecedor.gridx = 0;
		gbc_lblFornecedor.gridy = 4;
		panel_1.add(lblFornecedor, gbc_lblFornecedor);
		
		this.cbFornecedor = new JComboBox();
		pesquisarForcedores();
		GridBagConstraints gbc_cbFornecedor = new GridBagConstraints();
		gbc_cbFornecedor.gridwidth = 3;
		gbc_cbFornecedor.insets = new Insets(0, 0, 5, 0);
		gbc_cbFornecedor.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbFornecedor.gridx = 1;
		gbc_cbFornecedor.gridy = 4;
		panel_1.add(cbFornecedor, gbc_cbFornecedor);
		GridBagConstraints gbc_btnGravar = new GridBagConstraints();
		gbc_btnGravar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGravar.gridx = 0;
		gbc_btnGravar.gridy = 5;
		panel_1.add(btnGravar, gbc_btnGravar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 5;
		panel_1.add(btnCancelar, gbc_btnCancelar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(this);
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.gridx = 3;
		gbc_btnExcluir.gridy = 5;
		panel_1.add(btnExcluir, gbc_btnExcluir);
		
		table = new JTable();
		table.addMouseListener(this);
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		
		pesquisar();
		
	}	

	public void pesquisar() {			
		ArrayList<Produto> objetos=this.objDAO.pesquisar(this.tfPesquisa.getText());
		DefaultTableModel model = new MeuModelo();
		model.addColumn("ID");
		model.addColumn("Nome");
		model.addColumn("Estoque");
		model.addColumn("Valor");
		model.addColumn("Fornecedor");
		this.table.setModel(model);
		
		for (Produto objeto : objetos) {
			model.addRow(new Object[]{objeto.getId(),
									  objeto.getNome(),
					                  objeto.getEstoque(),
					                  objeto.getValor(),
					                  objeto.getFornecedor()});			
		}
	}
	
	public void limpar() {
		this.objAtual = new Produto();
		setObj();
	}
	
	public void setObj() {
		if (objAtual.getId() != null)
			this.tfCodigo.setText(objAtual.getId().toString());
		else this.tfCodigo.setText("0");
		this.tfNome.setText(objAtual.getNome());
		if (objAtual.getEstoque()!=null)
			this.tfEstoque.setText(objAtual.getEstoque().toString());
		else this.tfEstoque.setText("0");
		if (objAtual.getValor()!=null)
			this.tfValor.setText(objAtual.getValor().toString());
		else this.tfValor.setText("0");
		if (this.objAtual.getFornecedor() != null) {
			for (int i = 0; i < this.cbFornecedor.getItemCount(); i++) {
				Fornecedor item=(Fornecedor)this.cbFornecedor.getItemAt(i);
				if (item.getId() == this.objAtual.getFornecedor().getId()) {
					this.cbFornecedor.setSelectedIndex(i);
				}
			}			
		}
	}
	
	public void getObj() {
		objAtual.setId(Integer.parseInt(this.tfCodigo.getText()));
		objAtual.setNome(this.tfNome.getText());
		objAtual.setEstoque(Integer.parseInt(this.tfEstoque.getText()));
		objAtual.setValor(new BigDecimal(this.tfValor.getText()));
		objAtual.setFornecedor((Fornecedor)this.cbFornecedor.getSelectedItem());
	}
	
	public void editar() {
		int linha = this.table.getSelectedRow();
		if (linha>=0) {
			this.objAtual = new Produto();
			objAtual.setId((Integer)this.table.getValueAt(linha, 0));
			objAtual.setNome((String) this.table.getValueAt(linha, 1));
			objAtual.setEstoque((Integer) this.table.getValueAt(linha, 2));			
			objAtual.setValor((BigDecimal) this.table.getValueAt(linha, 3));
			objAtual.setFornecedor((Fornecedor) this.table.getValueAt(linha, 4));			
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
	
	public void pesquisarForcedores() {
		fornecedores=this.fornecedorDAO.pesquisar("");
		DefaultComboBoxModel md = new DefaultComboBoxModel();
		for (Fornecedor fornecedor : fornecedores) {
			md.addElement(fornecedor);
		}		
		
		this.cbFornecedor = new JComboBox();
		this.cbFornecedor.setModel(md);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		if      (e.getActionCommand()=="Pesquisar") pesquisar();
		else if (e.getActionCommand()=="Gravar") 	gravar();
		else if (e.getActionCommand()=="Cancelar") 	cancelar();
		else if (e.getActionCommand()=="Novo Produto") 	limpar();
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
