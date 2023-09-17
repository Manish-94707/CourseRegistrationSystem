import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    int capacity;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return code + " - " + title + " | Capacity: " + capacity + " | Schedule: " + schedule;
    }
}

class Student {
    private int id;
    private String name;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (registeredCourses.size() < 3) { // Limiting to 3 courses per student
            registeredCourses.add(course);
            courseCapacityUpdate(course, -1); // Decrease course capacity
        } else {
            System.out.println("You have already registered for the maximum number of courses.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            courseCapacityUpdate(course, 1); // Increase course capacity
            System.out.println("Course dropped successfully.");
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void courseCapacityUpdate(Course course, int change) {
        course.capacity += change;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name;
    }
}

public class CourseRegistrationSystem {
    private List<Course> courseDatabase;
    private List<Student> studentDatabase;

    public CourseRegistrationSystem() {
        this.courseDatabase = new ArrayList<>();
        this.studentDatabase = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courseDatabase.add(course);
    }

    public void addStudent(Student student) {
        studentDatabase.add(student);
    }

    public void displayAvailableCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseDatabase) {
            System.out.println(course);
        }
    }

    public void displayStudents() {
        System.out.println("Registered Students:");
        for (Student student : studentDatabase) {
            System.out.println(student);
        }
    }

    public Student findStudentById(int id) {
        for (Student student : studentDatabase) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Course findCourseByCode(String code) {
        for (Course course : courseDatabase) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Add some courses
        system.addCourse(new Course("CSC101", "Introduction to Computer Science", "Basic CS concepts", 30, "MWF 9:00 AM"));
        system.addCourse(new Course("MAT201", "Calculus I", "Limits, derivatives, integrals", 25, "TTh 1:00 PM"));
        system.addCourse(new Course("ENG202", "Advanced Writing", "Academic writing skills", 20, "MWF 2:00 PM"));

        // Add some students
        system.addStudent(new Student(1001, "John Doe"));
        system.addStudent(new Student(1002, "Jane Smith"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Display Registered Students");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    system.displayAvailableCourses();
                    break;
                case 2:
                    system.displayStudents();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Student student = system.findStudentById(studentId);
                    if (student != null) {
                        system.displayAvailableCourses();
                        System.out.print("Enter course code to register: ");
                        String courseCode = scanner.nextLine();
                        Course course = system.findCourseByCode(courseCode);
                        if (course != null) {
                            student.registerCourse(course);
                            System.out.println("Course registered successfully.");
                        } else {
                            System.out.println("Invalid course code.");
                        }
                    } else {
                        System.out.println("Invalid student ID.");
                    }
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    student = system.findStudentById(studentId);
                    if (student != null) {
                        if (!student.getRegisteredCourses().isEmpty()) {
                            for (int i = 0; i < student.getRegisteredCourses().size(); i++) {
                                System.out.println(i + 1 + ". " + student.getRegisteredCourses().get(i));
                            }
                            System.out.print("Enter the number of the course to drop: ");
                            int courseNumber = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (courseNumber >= 1 && courseNumber <= student.getRegisteredCourses().size()) {
                                Course courseToDrop = student.getRegisteredCourses().get(courseNumber - 1);
                                student.dropCourse(courseToDrop);
                            } else {
                                System.out.println("Invalid course number.");
                            }
                        } else {
                            System.out.println("No courses registered.");
                        }
                    } else {
                        System.out.println("Invalid student ID.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
