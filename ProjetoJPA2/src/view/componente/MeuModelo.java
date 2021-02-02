package view.componente;

import javax.swing.table.DefaultTableModel;

public class MeuModelo extends DefaultTableModel {
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
