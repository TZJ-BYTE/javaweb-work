import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyDrawGUI extends JFrame {

    private JComboBox<String> genderComboBox;
    private JTextField numDrawTextField;
    private JTextArea resultTextArea;
    private ArrayList<String> excludeIds;
    private ArrayList<Student> students;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> excludedListModel;

    public LuckyDrawGUI() {
        students = new ArrayList<>();
        students.add(new Student("1", "陈浩鑫", "男生"));
        students.add(new Student("2", "段虹毓", "男生"));
        students.add(new Student("3", "范学洋", "男生"));
        students.add(new Student("4", "黄维德", "男生"));
        students.add(new Student("5", "黄熙", "男生"));
        students.add(new Student("6", "李晓璞", "男生"));
        students.add(new Student("7", "刘权", "男生"));
        students.add(new Student("8", "马有道", "男生"));
        students.add(new Student("9", "秦烯尧", "男生"));
        students.add(new Student("10", "沙书鑫", "男生"));
        students.add(new Student("11", "邵嘉怡", "女生"));
        students.add(new Student("12", "邵子木", "男生"));
        students.add(new Student("13", "孙建鑫", "男生"));
        students.add(new Student("14", "孙萌", "女生"));
        students.add(new Student("15", "孙瑞琪", "女生"));
        students.add(new Student("16", "孙艺瑄", "女生"));
        students.add(new Student("17", "唐中界", "男生"));
        students.add(new Student("18", "王瑞", "女生"));
        students.add(new Student("19", "王斯羽", "女生"));
        students.add(new Student("20", "王妍", "女生"));
        students.add(new Student("21", "韦家诚", "男生"));
        students.add(new Student("22", "吴文喆", "男生"));
        students.add(new Student("23", "杨思婷", "女生"));
        students.add(new Student("24", "于湘", "男生"));
        students.add(new Student("25", "余子达", "男生"));
        students.add(new Student("26", "张杰", "男生"));
        students.add(new Student("27", "张乃夫", "男生"));
        students.add(new Student("28", "张祎喆", "男生"));


        excludeIds = new ArrayList<>();

        setTitle("幸运抽奖");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1));

        JPanel topPanel = new JPanel();
        JLabel genderLabel = new JLabel("选择范围:");
        topPanel.add(genderLabel);

        String[] genders = {"全部", "男生", "女生"};
        genderComboBox = new JComboBox<>(genders);
        topPanel.add(genderComboBox);

        JLabel numDrawLabel = new JLabel("抽取数量:");
        topPanel.add(numDrawLabel);

        numDrawTextField = new JTextField(5);
        topPanel.add(numDrawTextField);

        resultTextArea = new JTextArea(10, 30);
        topPanel.add(resultTextArea);

        panel.add(topPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        listModel = new DefaultListModel<>();
        JList<String> studentList = new JList<>(listModel);
        for (Student student : students) {
            listModel.addElement(student.getName() + " (ID: " + student.getId() + ")");
        }
        studentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = studentList.getSelectedIndex();
                    if (index != -1) {
                        String selectedStudent = listModel.getElementAt(index);
                        String studentId = selectedStudent.substring(selectedStudent.indexOf("(ID: ") + 5, selectedStudent.length() - 1);
                        excludeIds.add(studentId);
                        listModel.remove(index);
                        excludedListModel.addElement(selectedStudent);
                    }
                }
            }
        });

        excludedListModel = new DefaultListModel<>();
        JList<String> excludedStudentList = new JList<>(excludedListModel);
        excludedStudentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = excludedStudentList.getSelectedIndex();
                    if (index != -1) {
                        String selectedStudent = excludedListModel.getElementAt(index);
                        String studentId = selectedStudent.substring(selectedStudent.indexOf("(ID: ") + 5, selectedStudent.length() - 1);
                        excludeIds.remove(studentId);
                        excludedListModel.remove(index);
                        listModel.addElement(selectedStudent);
                    }
                }
            }
        });

        bottomPanel.add(new JScrollPane(studentList));
        bottomPanel.add(new JScrollPane(excludedStudentList));

        panel.add(bottomPanel);

        JButton drawButton = new JButton("抽取");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLuckyStudents();
            }
        });
        panel.add(drawButton);

        add(panel);
    }

    private void drawLuckyStudents() {
    String excludeGender = (String) genderComboBox.getSelectedItem();
    int numDraw = Integer.parseInt(numDrawTextField.getText());

    List<Student> availableStudents = new ArrayList<>();
    for (Student student : students) {
        if (("全部".equals(excludeGender) || excludeGender.equalsIgnoreCase(student.getGender())) && !excludeIds.contains(student.getId())) {
            availableStudents.add(student);
        }
    }

    if (availableStudents.size() < numDraw) {
        resultTextArea.setText("Not enough eligible students for lucky draw.");
    } else {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numDraw; i++) {
            int index = random.nextInt(availableStudents.size());
            Student luckyStudent = availableStudents.get(index);
            result.append("Lucky Student ").append(i + 1).append(": ").append(luckyStudent.getName()).append(" (ID: ").append(luckyStudent.getId()).append(")\n");
            availableStudents.remove(index);
        }
        resultTextArea.setText(result.toString());
    }
}



    public static void main(String[] args) {
        LuckyDrawGUI luckyDrawGUI = new LuckyDrawGUI();
        luckyDrawGUI.setVisible(true);
    }
}

class Student {
    private String id;
    private String name;
    private String gender;

    public Student(String id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}