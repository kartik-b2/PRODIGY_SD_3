package projectCM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ContactManager {

    private JFrame frame;
    private JTextField nameField, phoneField  ;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Contact> Contactstobeadded;

    public ContactManager() {
        Contactstobeadded = new ArrayList<>();
        frame = new JFrame("Contact Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        panel.setBackground(Color.yellow);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 50, 80, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField(20);
        phoneField.setBounds(100, 50, 165, 25);
        panel.add(phoneField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 80, 80, 25);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(100, 80, 80, 25);
        panel.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(190, 80, 80, 25);
        panel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        tableModel = new DefaultTableModel(new String[]{"Name", "Phone" }, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.PINK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 120, 360, 200);
        panel.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                nameField.setText((String) tableModel.getValueAt(selectedRow, 0));
                phoneField.setText((String) tableModel.getValueAt(selectedRow, 1));
            }
        });
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        if (!name.isEmpty() && !phone.isEmpty() ) {
            Contact contact = new Contact(name, phone );
            Contactstobeadded.add(contact);
            tableModel.addRow(new Object[]{name, phone });
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill in both fields.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String name = nameField.getText();
            String phone = phoneField.getText();

            if (!name.isEmpty() && !phone.isEmpty() ) {
                Contact contact = Contactstobeadded.get(selectedRow);
                contact.setName(name);
                contact.setPhone(phone);
                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(phone, selectedRow, 1);

                clearFields();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in both fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void deleteContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Contactstobeadded.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}
