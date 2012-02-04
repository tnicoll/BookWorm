package com.tnicoll.apps.bookworm;

import javax.swing.table.DefaultTableModel;

public final class BookModel extends DefaultTableModel
{
	

	/**
	 *  Custom Table Model
	 */
	private static final long serialVersionUID = 8791779464111015767L;

	public BookModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
    public Class<?> getColumnClass(int colNum) {
		return getValueAt(0, colNum).getClass();
    } 
	public boolean isCellEditable(int row, int col) {
        return false;
    }
}
