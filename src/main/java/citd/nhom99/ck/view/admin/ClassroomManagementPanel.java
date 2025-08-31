package citd.nhom99.ck.view.admin;

import citd.nhom99.ck.dao.ClassroomDAO;
import citd.nhom99.ck.dao.impl.ClassroomDAOImpl;
import citd.nhom99.ck.model.Classroom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClassroomManagementPanel extends JPanel {
    private JTable classroomTable;
    private DefaultTableModel tableModel;
    private final ClassroomDAO classroomDAO;
    private JButton addButton, editButton, deleteButton, refreshButton;

    public ClassroomManagementPanel() {
        this.classroomDAO = new ClassroomDAOImpl();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table Panel
        add(createTablePanel(), BorderLayout.CENTER);

        // Button Panel
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadClassroomData();
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {"ID Lớp", "Tên lớp", "Sĩ số"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classroomTable = new JTable(tableModel);
        classroomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(classroomTable);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Thêm lớp học");
        editButton = new JButton("Sửa thông tin");
        deleteButton = new JButton("Xóa lớp học");
        refreshButton = new JButton("Làm mới");

        addButton.addActionListener(e -> handleAddClassroom());
        editButton.addActionListener(e -> handleEditClassroom());
        deleteButton.addActionListener(e -> handleDeleteClassroom());
        refreshButton.addActionListener(e -> loadClassroomData());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void loadClassroomData() {
        tableModel.setRowCount(0);
        try {
            List<Classroom> classrooms = classroomDAO.getAllClassrooms();
            for (Classroom classroom : classrooms) {
                Object[] rowData = {
                        classroom.getClassId(),
                        classroom.getClassName(),
                        classroom.getStudents() != null ? classroom.getStudents().size() : 0
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tải dữ liệu lớp học: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddClassroom() {
        JTextField classIdField = new JTextField();
        JTextField classNameField = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("ID Lớp"),
                classIdField,
                new JLabel("Tên lớp"),
                classNameField
        };
        int result = JOptionPane.showConfirmDialog(this, inputs, "Thêm lớp học mới", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String classId = classIdField.getText();
            String className = classNameField.getText();
            if (classId != null && !classId.trim().isEmpty() && className != null && !className.trim().isEmpty()) {
                // Creating a classroom with null for Teacher and empty student list.
                // This might need adjustment depending on your DAO and DB constraints.
                Classroom newClassroom = new Classroom(classId, className, null, new java.util.ArrayList<>());
                classroomDAO.addClassroom(newClassroom);
                loadClassroomData();
            } else {
                JOptionPane.showMessageDialog(this, "ID và tên lớp không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleEditClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow >= 0) {
            String classId = (String) tableModel.getValueAt(selectedRow, 0);
            Classroom classroom = classroomDAO.getClassroomById(classId);

            if (classroom != null) {
                String newClassName = JOptionPane.showInputDialog(this, "Nhập tên lớp mới:", classroom.getClassName());
                if (newClassName != null && !newClassName.trim().isEmpty()) {
                    classroom.setClassName(newClassName);
                    classroomDAO.updateClassroom(classroom);
                    loadClassroomData();
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin lớp học.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lớp học để sửa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleDeleteClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa lớp học này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String classId = (String) tableModel.getValueAt(selectedRow, 0);
                classroomDAO.deleteClassroom(classId);
                loadClassroomData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lớp học để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
}
