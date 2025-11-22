import java.util.Scanner;

public class StudentGradesSystem {
    static final int MAX_STUDENTS = 50;
    static int[] rollNumbers = new int[MAX_STUDENTS];
    static String[] names = new String[MAX_STUDENTS];
    static int[][] marks = new int[MAX_STUDENTS][3];
    static int[] totals = new int[MAX_STUDENTS];
    static double[] averages = new double[MAX_STUDENTS];
    static int count = 0;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Student Grade Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Update Marks");
            System.out.println("3. Remove Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Highest Scorer");
            System.out.println("7. Class Average");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateMarks();
                case 3 -> removeStudent();
                case 4 -> viewAllStudents();
                case 5 -> searchStudent();
                case 6 -> highestScorer();
                case 7 -> classAverage();
                case 8 -> {
                    System.out.println("Exiting...");
                    System.out.println("Total students: " + count);
                    if (count > 0) {
                        System.out.println("Class average: " + calculateClassAverage());
                    }
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        if (count >= MAX_STUDENTS) {
            System.out.println("Cannot add more students (limit 50 reached).");
            return;
        }

        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        for (int i = 0; i < count; i++) {
            if (rollNumbers[i] == roll) {
                System.out.println("Roll number already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = input.next();

        int[] studentMarks = new int[3];
        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
                studentMarks[i] = input.nextInt();
                if (studentMarks[i] >= 0 && studentMarks[i] <= 100) break;
                System.out.println("Marks must be between 0 and 100. Try again.");
            }
        }

        rollNumbers[count] = roll;
        names[count] = name;
        marks[count] = studentMarks;
        totals[count] = studentMarks[0] + studentMarks[1] + studentMarks[2];
        averages[count] = totals[count] / 3.0;
        count++;

        System.out.println("Student added successfully!");
    }

    static void updateMarks() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.print("Enter new marks for Subject " + (i + 1) +
                        " (current: " + marks[idx][i] + "): ");
                int mark = input.nextInt();
                if (mark >= 0 && mark <= 100) {
                    marks[idx][i] = mark;
                    break;
                }
                System.out.println("Marks must be between 0 and 100. Try again.");
            }
        }

        totals[idx] = marks[idx][0] + marks[idx][1] + marks[idx][2];
        averages[idx] = totals[idx] / 3.0;
        System.out.println("Marks updated successfully!");
    }

    static void removeStudent() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = idx; i < count - 1; i++) {
            rollNumbers[i] = rollNumbers[i + 1];
            names[i] = names[i + 1];
            marks[i] = marks[i + 1];
            totals[i] = totals[i + 1];
            averages[i] = averages[i + 1];
        }
        count--;
        System.out.println("Student removed successfully!");
    }

    static void viewAllStudents() {
        if (count == 0) {
            System.out.println("No students!");
            return;
        }

        System.out.println("RollNo | Name | Sub1 | Sub2 | Sub3 | Total | Average");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < count; i++) {
            System.out.println(
                rollNumbers[i] + " | " + names[i] + " | " +
                marks[i][0] + " | " + marks[i][1] + " | " + marks[i][2] +
                " | " + totals[i] + " | " + averages[i]
            );
        }
    }

    static void searchStudent() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("RollNo | Name | Sub1 | Sub2 | Sub3 | Total | Average");
        System.out.println("---------------------------------------------------");
        System.out.println(
            rollNumbers[idx] + " | " + names[idx] + " | " +
            marks[idx][0] + " | " + marks[idx][1] + " | " + marks[idx][2] +
            " | " + totals[idx] + " | " + averages[idx]
        );
    }

    static void highestScorer() {
        if (count == 0) {
            System.out.println("No students!");
            return;
        }
        int topIdx = 0;
        for (int i = 1; i < count; i++) {
            if (totals[i] > totals[topIdx]) {
                topIdx = i;
            }
        }
        System.out.println("Highest Scorer:");
        System.out.println("RollNo | Name | Sub1 | Sub2 | Sub3 | Total | Average");
        System.out.println("---------------------------------------------------");
        System.out.println(
            rollNumbers[topIdx] + " | " + names[topIdx] + " | " +
            marks[topIdx][0] + " | " + marks[topIdx][1] + " | " + marks[topIdx][2] +
            " | " + totals[topIdx] + " | " + averages[topIdx]
        );
    }

    static void classAverage() {
        if (count == 0) {
            System.out.println("No students!");
            return;
        }
        System.out.println("Class Average: " + calculateClassAverage());
    }

    static double calculateClassAverage() {
        int totalMarks = 0;
        for (int i = 0; i < count; i++) {
            totalMarks += totals[i];
        }
        return (double) totalMarks / (count * 3);
    }

    static int findStudentIndex(int roll) {
        for (int i = 0; i < count; i++) {
            if (rollNumbers[i] == roll) return i;
        }
        return -1;
    }
}
