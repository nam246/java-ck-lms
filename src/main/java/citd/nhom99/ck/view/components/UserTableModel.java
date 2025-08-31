package citd.nhom99.ck.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserTableModel extends JPanel {

    public UserTableModel(String[] columnNames, int rowCount) {
        DefaultTableModel tableModel = new DefaultTableModel();

        JTable teacherTable = new JTable(tableModel);
        teacherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        add(scrollPane, BorderLayout.CENTER);
    }
}
