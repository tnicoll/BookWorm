package com.tnicoll.apps.bookworm;

import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

public final class BookModel extends DefaultTableModel
{
	

	/**
	 *  
	 */
	private static final long serialVersionUID = 8791779464111015767L;

	public BookModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	@Override
    public Class<?> getColumnClass(int colNum) {
		Object value = this.getValueAt(0,colNum);
		if(value==null)
			return String.class;
		else
		{
        switch (colNum) {
            case 0:
                return Integer.class;
            case 1:
                return Double.class;
            case 2:
                return Long.class;
            case 3:
                return Boolean.class;
            case 4:
                return String.class;
            case 5:
                return Icon.class;
            default:
                return String.class;
        }
        }
    } 
}
