package studentgradessystem;

import java.util.Scanner;

public class StudentGradesSystem {
    static final int MAX_STUDENTS = 50;
    static int[] rollNumbers = new int[MAX_STUDENTS];
    static String[] names = new String[MAX_STUDENTS];
    static int[][] marks = new int[MAX_STUDENTS][3];
    static int studentCount = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to Student Grade Management System");
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
                case 1: addStudent(); break;
                case 2: updateMarks(); break;
                case 3: removeStudent(); break;
                case 4: viewAllStudents(); break;
                case 5: searchStudent(); break;
                case 6: highestScorer(); break;
                case 7: classAverage(); break;
                case 8:
                    System.out.println("Exiting...");
                    System.out.println("Total students: " + studentCount);
                    if (studentCount > 0) {
                        System.out.println("Class average: " + calculateClassAverage());
                    }
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Cannot add more students (limit reached).");
            return;
        }

        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();

        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) {
                System.out.println("Roll number already exists!");
                return;
            }
        }

        rollNumbers[studentCount] = roll;

        System.out.print("Enter Name: ");
        names[studentCount] = input.next();

        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
                int mark = input.nextInt();
                if (mark >= 0 && mark <= 100) {
                    marks[studentCount][i] = mark;
                    break;
                } else {
                    System.out.println("Marks must be between 0 and 100. Try again.");
                }
            }
        }
        studentCount++;
        System.out.println("Student added successfully!");
    }

    static void updateMarks() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            while (true) {
                System.out.print("Enter new marks for Subject " + (i + 1) + " (current: " + marks[index][i] + "): ");
                int mark = input.nextInt();
                if (mark >= 0 && mark <= 100) {
                    marks[index][i] = mark;
                    break;
                } else {
                    System.out.println("Marks must be between 0 and 100. Try again.");
                }
            }
        }
        System.out.println("Marks updated successfully!");
    }

    static void removeStudent() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        for (int i = index; i < studentCount - 1; i++) {
            rollNumbers[i] = rollNumbers[i + 1];
            names[i] = names[i + 1];
            marks[i] = marks[i + 1];
        }
        studentCount--;
        System.out.println("Student removed successfully!");
    }

    static void viewAllStudents() {
        if (studentCount == 0) {
            System.out.println("No students!");
            return;
        }
        System.out.println("Roll No\tName\tSub1\tSub2\tSub3\tTotal\tAverage");
        for (int i = 0; i < studentCount; i++) {
            int total = marks[i][0] + marks[i][1] + marks[i][2];
            double average = total / 3.0;
            System.out.println(rollNumbers[i] + "\t" + names[i] + "\t" +
                               marks[i][0] + "\t" + marks[i][1] + "\t" + marks[i][2] +
                               "\t" + total + "\t" + average);
        }
    }

    static void searchStudent() {
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();
        int index = findStudent(roll);
        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }
        int total = marks[index][0] + marks[index][1] + marks[index][2];
        double average = total / 3.0;
        System.out.println("Roll No: " + rollNumbers[index]);
        System.out.println("Name: " + names[index]);
        System.out.println("Marks: " + marks[index][0] + ", " + marks[index][1] + ", " + marks[index][2]);
        System.out.println("Total: " + total);
        System.out.println("Average: " + average);
    }

    static void highestScorer() {
        if (studentCount == 0) {
            System.out.println("No students!");
            return;
        }
        int bestIndex = 0;
        int bestTotal = marks[0][0] + marks[0][1] + marks[0][2];
        for (int i = 1; i < studentCount; i++) {
            int total = marks[i][0] + marks[i][1] + marks[i][2];
            if (total > bestTotal) {
                bestTotal = total;
                bestIndex = i;
            }
        }
        double avg = bestTotal / 3.0;
        System.out.println("Highest Scorer: " + names[bestIndex] + " (Roll No: " + rollNumbers[bestIndex] + ")");
        System.out.println("Total: " + bestTotal + ", Average: " + avg);
    }

    static void classAverage() {
        if (studentCount == 0) {
            System.out.println("No students!");
            return;
        }
        System.out.println("Class average: " + calculateClassAverage());
    }

    static double calculateClassAverage() {
        int grandTotal = 0;
        for (int i = 0; i < studentCount; i++) {
            grandTotal += marks[i][0] + marks[i][1] + marks[i][2];
        }
        return (double) grandTotal / (studentCount * 3);
    }

    static int findStudent(int roll) {
        for (int i = 0; i < studentCount; i++) {
            if (rollNumbers[i] == roll) {
                return i;
            }
        }
        return -1;
    }
}
