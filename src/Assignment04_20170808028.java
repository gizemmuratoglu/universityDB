/*
*@author Gizem Nur Muratoğlu
*@since 5/28/2021
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.management.RuntimeErrorException;

public class Assignment04_20170808028 {

    public static void main(String[] args) {
       Department cse=new Department("CSE", "Computer Engineering");
       Teacher teacher=new Teacher("Joseph Ledet","josephledet@akdeniz.edu.tr", 123L, cse,3);
        System.out.println(teacher);
        Student stu=new Student("Assisnment 4 Student", "me@somewhere.com", 465L, cse);
        Semester s1=new Semester(1, 2020);
        //System.out.println(s1);
        Course c101=new Course(cse, 101, "Programming 1", 6, teacher);
        Semester s2=new Semester(2, 2021);
        Course c102=new Course(cse, 102, "Programing 2", 4, teacher);
        Course c204=new Course(cse, 204, "Database Systems", 6, teacher);
        
        stu.addCourse(c101, s1, 80.0);
        stu.addCourse(c102, s2, 30.0);
        stu.addCourse(c204, s2, 70.0);
        System.out.println("List Grades for CSE 101:\n" +stu.listGrades(c101));
        System.out.println("List Grades for SPRING 2021:\n" +stu.listGrades(s2));
        System.out.println("Student Transkript :" +stu.transkript());
        
        GradStudent gs=new GradStudent("ASSIGNMENT 4 GS","me@somewhere.com",789L,cse,"MDE");
        gs.addCourse(c101, s1, 85);
        gs.addCourse(c102, s1, 40);
        gs.setTeachingAssistant(c101);
        System.out.println("Teaching Assistant : "+gs.getTeachingAssistant());
         gs.setTeachingAssistant(c102);
        System.out.println("Teaching Assistant : "+gs.getTeachingAssistant());
        
    }

}

class Department {

    private String ID;
    private String name;
    private Teacher chair;

    public Department(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        if (ID.length() == 3 || ID.length() == 4) {
            this.ID = ID;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChair(Teacher teacher) throws DepartmentMismatchException {
        if (!teacher.getDepartment().equals(this)) {
            throw new DepartmentMismatchException(this, teacher);
        } else {
            this.chair = teacher;
        }

    }

    public Teacher getChair() {
        return chair;
    }

    @Override
    public String toString() {
        return  ID ;
    }
    


}

class Course {

    private Department department;
    private Teacher teacher;
    private int number;
    private String title;
    private int akts;

    public Course(Department department, int number, String title, int akts, Teacher teacher) {
        setDepartment(department);
        setNumber(number);
        setTitle(title);
        setAkts(akts);
        setTeacher(teacher);
    }

    @Override
    public String toString() {
        return  getDepartment() + " " + getNumber()  ;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) throws DepartmentMismatchException {         //teacher ,this ve course aynı departmanda değilse EXCEPTION
        if (getDepartment() == teacher.getDepartment()) {
            this.teacher = teacher;
        } else {
            throw new DepartmentMismatchException(teacher, this);
        }
    }

    public String courseCode() {
        return getDepartment() + " " + getNumber();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws RuntimeException {
        if (number >= 100 && number <= 499) {
            this.number = number;
        } else if (number >= 5000 && number <= 5999) {
            this.number = number;
        } else if (number >= 7000 && number <= 7999) {
            this.number = number;
        } else {
            throw new RuntimeException();

        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAkts() {
        return akts;
    }

    public void setAkts(int akts) {

        if (akts > 0) {
            this.akts = akts;

        }

    }

}

abstract class Person {

    private String name;
    private String email;
    private long id;
    private Department department;

    public Person(String name, String email, long id, Department department) {
        setDepartment(department);
        setEmail(email);
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public float getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) throws InvalidEmailFormat {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        if (email.matches(regex)) {
            this.email = email;
        } else {
            throw new InvalidEmailFormat(email);
        }

    }

    public void setId(long id) {

        if (id % 2 == 1) {
            this.id = 5;

        } else {

            throw new RuntimeException();
        }

    }

    public void setDepartment(Department department) {

        this.department = department;

    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return name +" "+"("+id+")"+" - " +email;
    }

}

class Teacher extends Person {

    int rank;

    public Teacher(String name, String email, long id, Department department,int rank) {
        super(name, email, id, department);
        setRank(rank);
    }

    @Override
    public void setDepartment(Department department) {
        if (this != department.getChair()) {
            super.setDepartment(department);
        } else {
            department.setChair(null);
            super.setDepartment(department);

        }

    }

    public void setRank(int rank) throws InvalidRankException {
        if (rank < 1 || rank >= 5) {
            throw new InvalidRankException(rank);
        } else {
            this.rank = rank;
        }

    }

    public int getRank() {
        return rank;
    }

    public String getTitle() throws RuntimeException {
        if (getRank() == 1) {
            return "Adjunct Instructor";

        } else if (getRank() == 2) {
            return "Lecturer";
        } else if (getRank() == 3) {
            return "Assistant Professor";

        } else if (getRank() == 4) {
            return "Associate Professor";

        } else if (getRank() == 5) {
            return "Professor";
        } else {
            throw new RuntimeException("Invalid title.");
        }

    }

    public void promote() throws InvalidRankException {
        int newRank = getRank();

        if (newRank < 4 && newRank > 0) {
            newRank++;
            System.out.println("new rank is: " + newRank);
            this.rank = newRank;

        } else {
            throw new InvalidRankException(newRank);
        }

    }

    public void demote() throws InvalidRankException {
        int newRank = getRank();

        if (newRank < 5 && newRank > 1) {
            newRank--;
            System.out.println("new rank is " + newRank);
            this.rank = newRank;
        } else {
            throw new InvalidRankException(newRank);                       //exception invalid
        }
    }

    @Override
    public String toString() {
        return getTitle()+" "+super.toString();
    }
    

}

class Semester {

    private int season;
    private int year;

    public Semester(int season, int year) {
        this.season = season;
        this.year = year;
    }

    public String getSeason() throws RuntimeException {
        if (season == 1) {
            return "Fall";
        } else if (season == 2) {
            return "Spring";
        } else if (season == 3) {
            return "Summer";

        } else {
            throw new RuntimeException("Invalid season .");
        }
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return getSeason() + " - " + getYear();
    }

}

class Student extends Person {

    //HashMap<Course, Double> course_list = new HashMap<>();
    String listgradeRes = "";
    HashMap<Course, Double> passed_course = new HashMap<>();
    HashMap<Semester, HashMap<Course, Double>> courseWithSemester = new HashMap<>();

    public Student(String name, String email, long id, Department department) {
        super(name, email, id, department);

    }

    public int getAkts() {
        int totalAkts = 0;
        if (passed_course.size() == 0) {
            return 0;
        } else {
            for (Course i : passed_course.keySet()) {
                totalAkts += i.getAkts();
            }
            return totalAkts;

        }
    }

    public int getAttemptAkts() {
        int total = 0;

        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester semesters = entrySet.getKey();
            HashMap<Course, Double> courseAndGrades = entrySet.getValue();
            for (Map.Entry<Course, Double> entrySet1 : courseAndGrades.entrySet()) {
                Course course = entrySet1.getKey();
                Double grade = entrySet1.getValue();
                total += course.getAkts();

            }

        }
        return total;
    }

    public String listGrades(Semester semester) throws SemesterNotFoundException {
        HashMap<Course, Double> resHash1 = new HashMap<>();
        
        if (!courseWithSemester.containsKey(semester)) {
            throw new SemesterNotFoundException(this, semester);

        } else {
            if(courseWithSemester.containsKey(semester)){
                resHash1 = courseWithSemester.get(semester);
           
           
            for (Map.Entry<Course, Double> entrySet : resHash1.entrySet()) {
                
                Course key = entrySet.getKey();
                Double value = entrySet.getValue();
                //if (value >= 60) {
                    listgradeRes +=  key+" - " + courseGradeLetter(key) + "\n";

              //  }
            }
        }
             }
        return listgradeRes;
    }

    public void addCourse(Course course, Semester semester, double grade) throws InvalidGradeException {

        if (courseWithSemester.containsKey(semester) && grade > 0 && grade < 101) {
            courseWithSemester.get(semester).put(course, grade);
            //System.out.println(courseWithSemester.get(semester).get(course) +" -- -  ");
                  
            if (grade >= 60.0) {
                passed_course.put(course, grade);
            }

        } else if (!courseWithSemester.containsKey(semester) && grade > 0 && grade < 101) {
           HashMap<Course, Double> tempHash = new HashMap<>();
           tempHash.put(course, grade);
            courseWithSemester.put(semester, tempHash);
            //System.out.println(courseWithSemester.get(semester).get(course)+"    ----   ");
        } else {
            throw new InvalidGradeException(grade);
        }
        

    }
   

    public String listGrades(Course course) throws CourseNotFoundException {
        HashMap<Course, Double> tempHash = new HashMap<>();
        String result ="";
        boolean isContain = false;

        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester key = entrySet.getKey();
            tempHash=entrySet.getValue();
            if (tempHash.containsKey(course)) {
                result += key.toString() + " - " + courseGradeLetter(course) + "\n";
                isContain = true;
           }

        }
        if (!isContain) {
            throw new CourseNotFoundException(this, course);
        }

        return result;
    }

    public double courseGPApoints(Course course) throws CourseNotFoundException {
        HashMap<Course, Double> course_and_grade = new HashMap<>();
        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester key = entrySet.getKey();
            HashMap<Course, Double> nestedHash = new HashMap<>();
            nestedHash=entrySet.getValue();
            for (Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
                Course key1 = entrySet1.getKey();
                Double value = entrySet1.getValue();
                course_and_grade.put(key1, value);
             }
        }
        if (course_and_grade.containsKey(course)) {
            double x = course_and_grade.get(course);
            if (x >= 88 && x <= 100) {
                return 4.0;

            } else if (x >= 81 && x <= 87) {
                return 3.5;

            } else if (x >= 74 && x <= 80) {
                return 3.0;

            } else if (x >= 67 && x <= 73) {
                return 2.5;

            } else if (x >= 60 && x <= 66) {
                return 2.0;

            } else if (x >= 53 && x <= 59) {
                return 1.5;

            } else if (x >= 46 && x <= 52) {
                return 1.0;

            } else if (x >= 35 && x <= 45) {
                return 0.5;

            } else {
                return 0.0;

            }
        } else {
            throw new CourseNotFoundException(this, course);                                 //COURSENOTFOUND EXCEPTION
        }

    }

    public String courseGradeLetter(Course course) throws CourseNotFoundException {

        HashMap<Course, Double> course_and_grade = new HashMap<>();

        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester key = entrySet.getKey();
            HashMap<Course, Double> nestedHash = new HashMap<>();
            nestedHash=entrySet.getValue();
            for (Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
                Course key1 = entrySet1.getKey();
                Double value = entrySet1.getValue();
                course_and_grade.put(key1, value);
            }
        }
        if (course_and_grade.containsKey(course)) {
            double x = course_and_grade.get(course);
            if (x >= 88 && x <= 100) {
                return "AA";

            } else if (x >= 81 && x <= 87) {
                return "BA";

            } else if (x >= 74 && x <= 80) {
                return "BB";

            } else if (x >= 67 && x <= 73) {
                return "CB";

            } else if (x >= 60 && x <= 66) {
                return "CC";

            } else if (x >= 53 && x <= 59) {
                return "DC";

            } else if (x >= 46 && x <= 52) {
                return "DD";

            } else if (x >= 35 && x <= 45) {
                return "FD";

            } else {
                return "FF";

            }
        } else {
            throw new CourseNotFoundException(this, course);                                 //COURSENOTFOUND EXCEPTION
        }

    }

    public String courseResult(Course course) throws CourseNotFoundException {
        HashMap<Course, Double> course_and_grade = new HashMap<>();
        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester key = entrySet.getKey();
            HashMap<Course, Double> nestedHash = new HashMap<>();
            for (Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
                Course key1 = entrySet1.getKey();
                Double value = entrySet1.getValue();
                course_and_grade.put(key1, value);
            }
        }
        if (course_and_grade.containsKey(course)) {
            double x = course_and_grade.get(course);
            if (x >= 88 && x <= 100) {
                return "Passed";

            } else if (x >= 81 && x <= 87) {
                return "Passed";

            } else if (x >= 74 && x <= 80) {
                return "Passed";

            } else if (x >= 67 && x <= 73) {
                return "Passed";

            } else if (x >= 60 && x <= 66) {
                return "Passed";

            } else if (x >= 53 && x <= 59) {
                return "Conditionally Passed";

            } else if (x >= 46 && x <= 52) {
                return "Conditionally Passed";

            } else if (x >= 35 && x <= 45) {
                return "Failed";

            } else {
                return "Failed";

            }
        } else {
            throw new CourseNotFoundException(this, course);
        }

    }

    public double getGPA() {
        HashMap<Course, Double> course_and_grade = new HashMap<>();
        
        for (Map.Entry<Semester, HashMap<Course, Double>> entrySet : courseWithSemester.entrySet()) {
            Semester key = entrySet.getKey();
            HashMap<Course, Double> nestedHash = new HashMap<>();
            for (Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
                Course key1 = entrySet1.getKey();
                Double value = entrySet1.getValue();
                if (!course_and_grade.containsKey(key1)) {
                    course_and_grade.put(key1, value);
                } else {
                    if (value > course_and_grade.get(key1)) {
                        course_and_grade.put(key1, value);
                    }
                }
            }

        }
        Course[] keys = new Course[course_and_grade.size()];
        Double[] gpa_vl = new Double[course_and_grade.size()];
        int index = 0;

        for (HashMap.Entry<Course, Double> HashEntry : course_and_grade.entrySet()) { //keys of hashmap convert an array
            keys[index] = HashEntry.getKey();
            index++;
        }
        double akts = 0;
        for (int i = 0; i < keys.length; i++) {
            akts += keys[i].getAkts();

        }
        double tot = 0;
        for (int i = 0; i < keys.length; i++) {                          //gpavalues add into gpa_vl array
            gpa_vl[i] = courseGPApoints(keys[i]) * keys[i].getAkts();
        }
        for (int i = 0; i < gpa_vl.length; i++) {                      //we can add values that multiplied to find gpa
            tot += gpa_vl[i];

        }
        return tot / akts;               //we added now divided number of element

    }
    public double getSemesterGPA(Semester semester) throws SemesterNotFoundException{
        HashMap<Course, Double> courseGrade_semester = new HashMap<>();
        if(courseWithSemester.containsKey(semester)){
            courseGrade_semester=courseWithSemester.get(semester);
            
            Course[] keys = new Course[courseGrade_semester.size()];
        Double[] gpa_vl = new Double[courseGrade_semester.size()];
        int index = 0;

        for (HashMap.Entry<Course, Double> HashEntry : courseGrade_semester.entrySet()) { //keys of hashmap convert an array
            keys[index] = HashEntry.getKey();
            index++;
        }
        double akts = 0;
        for (int i = 0; i < keys.length; i++) {
            akts += keys[i].getAkts();

        }
        double tot = 0;
        for (int i = 0; i < keys.length; i++) {                          //gpavalues add into gpa_vl array
            gpa_vl[i] = courseGPApoints(keys[i]) * keys[i].getAkts();
        }
        for (int i = 0; i < gpa_vl.length; i++) {                      //we can add values that multiplied to find gpa
            tot += gpa_vl[i];

        }
        return tot / akts;           
            
        }
        else{
            throw new SemesterNotFoundException(this, semester);
            
        }
        
    }
    
    public String transkript(){
        String result="";
        HashMap<Course,Double> courseAndGrade=new HashMap<>();
        
        for (Map.Entry<Semester, HashMap<Course,Double>> entrySet : courseWithSemester.entrySet()) {
            Semester semester = entrySet.getKey();
            courseAndGrade = entrySet.getValue();
           
             HashMap<Course, Double> resHash1 = new HashMap<>();
            
                resHash1 = courseWithSemester.get(semester);
                result+=semester+"\n";
           
           
            for (Map.Entry<Course, Double> entrySet2 : resHash1.entrySet()) {
                
                Course key = entrySet2.getKey();
                Double value = entrySet2.getValue();
               
                   result +=  key+" - " + courseGradeLetter(key) + "\n";

        }
            result+=getSemesterGPA(semester)+"\n";
             
        }
        
        return result;
    }

    public String toString() {
        return super.toString() + "-GPA :" + getGPA();
    }

}

class GradStudent extends Student {

    String thesis;
    Course assistantForCourse;

    public GradStudent(String name, String email,long id, Department department,String thesis) {
        super(name, email, id, department);
        setThesis(thesis);
    }

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    @Override
    public double courseGPApoints(Course course) throws CourseNotFoundException {
        
          HashMap<Course,Double> course_and_grade=new HashMap<>(); 
          
          for(Map.Entry<Semester, HashMap<Course,Double>> entrySet :courseWithSemester.entrySet()) {
          Semester key = entrySet.getKey();
          HashMap<Course,Double> nestedHash=new HashMap<>(); 
          
          for(Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
              
          Course key1 = entrySet1.getKey(); Double value =
          entrySet1.getValue(); course_and_grade.put(key1, value); }
         }
         

        if (course_and_grade.containsKey(course)) {
            double x = course_and_grade.get(course);
            if (x >= 90 && x <= 100) {
                return 4.0;

            } else if (x >= 85 && x <= 89) {
                return 3.5;

            } else if (x >= 80 && x <= 84) {
                return 3.0;

            } else if (x >= 75 && x <= 79) {
                return 2.5;

            } else if (x >= 70 && x <= 74) {
                return 2.0;

            } else {
                return 0.0;

            }
        } else {
            throw new CourseNotFoundException(this, course);
        }

    }
    public void setTeachingAssistant(Course course) throws CourseNotFoundException{
         HashMap<Course,Double> course_and_grade=new HashMap<>(); 
          
          for(Map.Entry<Semester, HashMap<Course,Double>> entrySet :courseWithSemester.entrySet()) {
          Semester key = entrySet.getKey();
          HashMap<Course,Double> nestedHash=new HashMap<>(); 
          nestedHash=entrySet.getValue();
          
          
          for(Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
              
          Course key1 = entrySet1.getKey(); 
          Double value =entrySet1.getValue(); 
          course_and_grade.put(key1, value); }
         }
          if(course_and_grade.containsKey(course)&&course_and_grade.get(course)>80&&assistantForCourse==null){
              assistantForCourse=course;
          }
          else {
              throw new CourseNotFoundException(this, course);
          }
        
        
    }
    public Course getTeachingAssistant(){
        return assistantForCourse;
    }

    public String courseGradeLetter(Course course) throws CourseNotFoundException {
          HashMap<Course,Double> course_and_grade=new HashMap<>(); 
          
          for(Map.Entry<Semester, HashMap<Course,Double>> entrySet :courseWithSemester.entrySet()) {
          Semester key = entrySet.getKey();
          HashMap<Course,Double> nestedHash=new HashMap<>(); 
          
          for(Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
              
          Course key1 = entrySet1.getKey(); Double value =
          entrySet1.getValue(); course_and_grade.put(key1, value); }
         }
        if (course_and_grade.containsKey(course)) {
            double x = course_and_grade.get(course);
            if (x >= 90 && x <= 100) {
                return "AA";

            } else if (x >= 85 && x <= 89) {
                return "BA";

            } else if (x >= 80 && x <= 84) {
                return "BB";

            } else if (x >= 75 && x <= 79) {
                return "CB";

            } else if (x >= 70 && x <= 74) {
                return "CC";

            } else {
                return "FF";

            }
        } else {
            throw new CourseNotFoundException(this, course);                            //COURSENOTFOUND EXCEPTION
        }

    }

    public String courseResult(Course course) throws CourseNotFoundException {
          HashMap<Course,Double> course_and_grade=new HashMap<>(); 
          
          for(Map.Entry<Semester, HashMap<Course,Double>> entrySet :courseWithSemester.entrySet()) {
          Semester key = entrySet.getKey();
          HashMap<Course,Double> nestedHash=new HashMap<>(); 
          
          for(Map.Entry<Course, Double> entrySet1 : nestedHash.entrySet()) {
              
          Course key1 = entrySet1.getKey(); Double value =
          entrySet1.getValue(); course_and_grade.put(key1, value); }
         }
        if (course_and_grade.containsKey(course)) {
           double x = course_and_grade.get(course);
            if (x >= 90 && x <= 100) {
                return "Passed";

            } else if (x >= 85 && x <= 89) {
                return "Passed";

            } else if (x >= 80 && x <= 84) {
                return "Passed";

            } else if (x >= 75 && x <= 79) {
                return "Passed";

            } else if (x >= 70 && x <= 74) {
                return "Passed";

            } else {
                return "Failed";

            }
        } else {
            throw new CourseNotFoundException(this, course);                                       //COURSENOTFOUND EXCEPTION
        }

    }
}

class CourseNotFoundException extends RuntimeException {

    Student st;
    Course course;

    public CourseNotFoundException(Student student, Course course) {
        super();
        this.st = student;
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseNotFoundException: " + st.getName() + " has not yet taken " + course.getNumber();
    }

}

class SemesterNotFoundException extends RuntimeException {

    Student student;
    Semester semester;

    public SemesterNotFoundException(Student student, Semester semester) {
        super();
        this.student = student;
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Semester Not Found Exception" + student.getId() + " has not taken any courses in " + semester;
    }

}

class DepartmentMismatchException extends RuntimeException {

    Department dp;
    Person teacher;
    Course course;
    private boolean dpnull = false;
    private boolean crsnull = false;

    public DepartmentMismatchException(Person teacher, Course course) {
        super();
        this.dp = null;
        this.teacher = teacher;
        this.course = course;
        dpnull = true;
    }

    public DepartmentMismatchException(Department department, Person teacher) {
        super();
        this.dp = department;
        this.teacher = teacher;
        this.course = null;
        crsnull = true;
    }

    @Override
    public String toString() {
        if (dpnull) {
            return "DepartmentMismatchException: " + teacher.getName() + "(" + teacher.getId() + ") cannot teach " + course.getTitle()
                    + " because he/she is currently assigned to " + teacher.getDepartment().getID();

        } else {

            return "DepartmentMismatchException: " + teacher.getName() + "(" + teacher.getId() + ") cannot be chair of "
                    + dp.getID() + " because he/she is currently assigned to " + teacher.getDepartment().getID();
        }
    }

}

class InvalidGradeException extends RuntimeException {

    double grade;

    public InvalidGradeException(double grade) {
        super();
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "InvalidGradeException: " + grade;
    }

}

class InvalidRankException extends RuntimeException {

    int rank;

    public InvalidRankException(int rank) {
        super();
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "InvalidRankException: " + rank;
    }

}

class InvalidEmailFormat extends RuntimeException {

    String mail;

    public InvalidEmailFormat(String mail) {
        super();
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "InvalidEmailFormat{" + "mail=" + mail + '}';
    }

}
