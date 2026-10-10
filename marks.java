import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class marks {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details of Student " + (i + 1));

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter marks: ");
            int marks = sc.nextInt();
            sc.nextLine();

            Student s = new Student(name, marks);
            students.add(s);
        }

        // Display student details
        System.out.println("\n----- STUDENT DETAILS -----");

        for (Student s : students) {
            System.out.println("Name: " + s.name);
            System.out.println("Marks: " + s.marks);
            System.out.println();
        }

        // Calculate average
        int total = 0;

        for (Student s : students) {
            total = total + s.marks;
        }

        double average = (double) total / students.size();

        // Find highest and lowest
        Student highest = students.get(0);
        Student lowest = students.get(0);

        for (Student s : students) {

            if (s.marks > highest.marks) {
                highest = s;
            }

            if (s.marks < lowest.marks) {
                lowest = s;
            }
        }

        // Final result
        System.out.println("----- RESULT -----");

        System.out.println("Average Marks: " + average);

        System.out.println("Highest Marks: " 
                           + highest.marks + " (" + highest.name + ")");

        System.out.println("Lowest Marks: " 
                           + lowest.marks + " (" + lowest.name + ")");

        sc.close();
    }
}