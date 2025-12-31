import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.*; 
import javax.swing.border.EmptyBorder; 

public class StudentDBManagerGUI extends JFrame {

    private final DatabaseManager dbManager;

    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField ageField;
    private final JTextField emailField;
    private final JTextField searchIdField;

    private final JTable studentTable;
    private final DefaultTableModel tableModel;
    private final JTextArea statusArea;
    
    // --- Color Constants (Used only for statusArea fallback/initial text, 
    // but the main feedback will now be dialogs) ---
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34); 
    private static final Color ERROR_COLOR = new Color(220, 20, 60);   
    private static final Color INFO_COLOR = Color.BLUE;
    // ---------------------------

    public StudentDBManagerGUI() {
        super("StudentDB Manager");

        dbManager = new DatabaseManager();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));
        inputPanel.setLayout(new SpringLayout());

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        ageField = new JTextField(15);
        emailField = new JTextField(15);

        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);

        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);

        inputPanel.add(new JLabel("Age (INT):"));
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        SpringUtilities.makeCompactGrid(inputPanel, 4, 2, 6, 6, 6, 6);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton viewButton = new JButton("View Students");
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        JPanel searchPanel = new JPanel();
        searchIdField = new JTextField(5);
        JButton searchButton = new JButton("Search Student by ID");
        searchPanel.add(new JLabel("Student ID:"));
        searchPanel.add(searchIdField);
        searchPanel.add(searchButton);

        String[] columnNames = {"ID", "First Name", "Last Name", "Age", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        statusArea = new JTextArea(3, 40);
        statusArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        statusArea.setBorder(BorderFactory.createTitledBorder("Status Messages"));

        add(inputPanel);
        add(buttonPanel);
        add(searchPanel);
        add(tableScrollPane);
        add(statusScrollPane);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentAction();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudentsAction();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudentAction();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addStudentAction() {
        statusArea.setForeground(Color.BLACK); 
        statusArea.setText("Attempting to add student...\n");

        try {
            String fName = firstNameField.getText().trim();
            String lName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());

            if (fName.isEmpty() || lName.isEmpty() || email.isEmpty()) {
                statusArea.append("Error: All fields must be filled.\n");
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (age < 0 || age > 150) {
                statusArea.append("Error: Invalid age value.\n");
                JOptionPane.showMessageDialog(this, "Invalid age value (must be between 0 and 150).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student newStudent = new Student(fName, lName, age, email); 
            setButtonsEnabled(false);

            new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return dbManager.addStudent(newStudent); 
                }

                @Override
                protected void done() {
                    setButtonsEnabled(true);
                    try {
                        if (get()) {
                            statusArea.append("SUCCESS: Student added to database.\n");
                            // --- DIALOG BOX CHANGE ---
                            JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                                "Student '" + fName + " " + lName + "' added successfully.", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                            // --- END DIALOG BOX CHANGE ---
                            
                            firstNameField.setText("");
                            lastNameField.setText("");
                            ageField.setText("");
                            emailField.setText("");
                        } else {
                            statusArea.append("FAILURE: Could not add student.\n");
                            // --- DIALOG BOX CHANGE ---
                            JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                                "Operation Failed: Could not add student to the database.", 
                                "Database Error", 
                                JOptionPane.ERROR_MESSAGE);
                            // --- END DIALOG BOX CHANGE ---
                        }
                    } catch (Exception ex) {
                        String msg = "ERROR during Add: " + getRootExceptionMessage(ex);
                        statusArea.append(msg + "\n");
                        // --- DIALOG BOX CHANGE ---
                        JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                            "An unexpected error occurred during addition:\n" + getRootExceptionMessage(ex), 
                            "System Error", 
                            JOptionPane.ERROR_MESSAGE);
                        // --- END DIALOG BOX CHANGE ---
                    }
                }
            }.execute();

        } catch (NumberFormatException ex) {
            statusArea.append("ERROR: Age must be a valid integer.\n");
            // --- DIALOG BOX CHANGE ---
            JOptionPane.showMessageDialog(this, "Age must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            // --- END DIALOG BOX CHANGE ---
        }
    }

    private void viewStudentsAction() {
        statusArea.setForeground(Color.BLACK); 
        statusArea.setText("Fetching all student records...\n");
        setButtonsEnabled(false);

        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() throws Exception {
                return dbManager.getAllStudents();
            }

            @Override
            protected void done() {
                setButtonsEnabled(true);
                tableModel.setRowCount(0);
                try {
                    List<Student> students = get();
                    if (students.isEmpty()) {
                        statusArea.append("INFO: No students found.\n");
                        // --- DIALOG BOX CHANGE ---
                        JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                            "No student records found in the database.", 
                            "Information", 
                            JOptionPane.INFORMATION_MESSAGE);
                        // --- END DIALOG BOX CHANGE ---
                    } else {
                        for (Student s : students) {
                            tableModel.addRow(new Object[]{
                                    s.getId(),
                                    s.getFirstName(),
                                    s.getLastName(),
                                    s.getAge(),
                                    s.getEmail()
                            });
                        }
                        statusArea.append("SUCCESS: " + students.size() + " student(s) displayed.\n");
                        // --- DIALOG BOX CHANGE ---
                        JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                            students.size() + " student(s) successfully loaded and displayed.", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                        // --- END DIALOG BOX CHANGE ---
                    }
                } catch (Exception ex) {
                    String msg = "ERROR during View: " + getRootExceptionMessage(ex);
                    statusArea.append(msg + "\n");
                    // --- DIALOG BOX CHANGE ---
                    JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                        "An unexpected error occurred while viewing:\n" + getRootExceptionMessage(ex), 
                        "System Error", 
                        JOptionPane.ERROR_MESSAGE);
                    // --- END DIALOG BOX CHANGE ---
                }
            }
        }.execute();
    }

    private void searchStudentAction() {
        statusArea.setForeground(Color.BLACK); 
        statusArea.setText("Attempting to search for student...\n");
        tableModel.setRowCount(0);

        try {
            int searchId = Integer.parseInt(searchIdField.getText().trim());
            setButtonsEnabled(false);

            new SwingWorker<Student, Void>() {
                @Override
                protected Student doInBackground() throws Exception {
                    return dbManager.searchStudentById(searchId);
                }

                @Override
                protected void done() {
                    setButtonsEnabled(true);
                    try {
                        Student student = get();
                        if (student != null) {
                            tableModel.addRow(new Object[]{
                                    student.getId(),
                                    student.getFirstName(),
                                    student.getLastName(),
                                    student.getAge(),
                                    student.getEmail()
                            });
                            statusArea.append("SUCCESS: Student with ID " + searchId + " found.\n");
                            // --- DIALOG BOX CHANGE ---
                            JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                                "Student found: ID " + searchId + " (" + student.getFirstName() + ").", 
                                "Search Result", 
                                JOptionPane.INFORMATION_MESSAGE);
                            // --- END DIALOG BOX CHANGE ---
                        } else {
                            statusArea.append("INFO: Student with ID " + searchId + " not found.\n");
                            // --- DIALOG BOX CHANGE ---
                            JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                                "Student with ID " + searchId + " not found in the database.", 
                                "Search Result", 
                                JOptionPane.WARNING_MESSAGE);
                            // --- END DIALOG BOX CHANGE ---
                        }
                    } catch (Exception ex) {
                        String msg = "ERROR during Search: " + getRootExceptionMessage(ex);
                        statusArea.append(msg + "\n");
                        // --- DIALOG BOX CHANGE ---
                        JOptionPane.showMessageDialog(StudentDBManagerGUI.this, 
                            "An unexpected error occurred during search:\n" + getRootExceptionMessage(ex), 
                            "System Error", 
                            JOptionPane.ERROR_MESSAGE);
                        // --- END DIALOG BOX CHANGE ---
                    }
                }
            }.execute();

        } catch (NumberFormatException ex) {
            statusArea.append("ERROR: Search ID must be a valid integer.\n");
            // --- DIALOG BOX CHANGE ---
            JOptionPane.showMessageDialog(this, "Search ID must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            // --- END DIALOG BOX CHANGE ---
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        Container contentPane = getContentPane();
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JButton) {
                        innerComp.setEnabled(enabled);
                    }
                }
            }
        }
    }

    private String getRootExceptionMessage(Exception e) {
        Throwable t = e;
        while (t.getCause() != null && t.getCause() != t) {
            t = t.getCause();
        }
        return t.getMessage();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Fallback to default L&F if Nimbus is unavailable
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentDBManagerGUI();
            }
        });
    }
}

// NOTE: The 'SpringUtilities' class definition MUST be in a separate file (SpringUtilities.java)
// or placed here depending on your compilation method. 
// Assuming it is defined here for completeness:

class SpringUtilities {
    public static void makeCompactGrid(Container parent,
                                     int rows, int cols,
                                     int initialX, int initialY,
                                     int xPad, int yPad) {

        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("Parent must use SpringLayout.");
            return;
        }

        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(
                        width,
                        layout.getConstraints(parent.getComponent(r * cols + c)).getWidth()
                );
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints cons =
                        layout.getConstraints(parent.getComponent(r * cols + c));
                cons.setX(x);
                cons.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(
                        height,
                        layout.getConstraints(parent.getComponent(r * cols + c)).getHeight()
                );
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints cons =
                        layout.getConstraints(parent.getComponent(r * cols + c));
                cons.setY(y);
                cons.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        } 

        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}