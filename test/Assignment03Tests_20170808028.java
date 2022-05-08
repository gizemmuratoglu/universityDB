
/**
 * @since 05/13/2021
 * @author gizem
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment03Tests_20170808028 {

    Department dept_comp = new Department("CSE", "Computer Engineering");
    Department dp_math = new Department("MATHS", "Mathematic");
    Student st = new Student("gizem", "gg@gmail.com", 455L, dept_comp);
    Teacher teacher1 = new Teacher(1, "Joseph Ledet", "joseph@akdeniz.edu.tr", 123L, dept_comp);
    Course c1 = new Course(dept_comp, 102, "Programming1", 5, teacher1);
    GradStudent grd_st = new GradStudent("ABC", "yaren", "yaren@gmail.com", 111L, dept_comp);

    @Test
    public void constructor_test() {
        assertEquals("CSE", dept_comp.getID());
        assertEquals(1, teacher1.getRank());
        assertEquals(5, c1.getAkts());
        assertEquals("gizem", st.getName());
        assertEquals("ABC", grd_st.getThesis());
    }

    @Test
    public void chair_test() {
        Teacher teacher2 = new Teacher(2, "Murat Bey", "muratbey@akdeniz.edu.tr", 103L, dp_math);

        dept_comp.setChair(teacher1);
        dept_comp.setChair(teacher2);
        dept_comp.setChair(teacher1);
        assertEquals(teacher1, dept_comp.getChair());
       // Teacher teacher2=new Teacher(2, "Murat Ak", "murat@gmail.com", 111L, dp_math);

        teacher1.setDepartment(dp_math);
        assertEquals(dp_math, teacher1.getDepartment());
        dp_math.setChair(teacher1);
        assertEquals(teacher1, dp_math.getChair());

        try {
            dept_comp.setChair(teacher1);

        } catch (DepartmentMismatchException e) {
            System.out.println(e.toString());
        }

    }

    @Test
    public void dept_test() {
        assertEquals(dept_comp, c1.getDepartment());
        assertEquals(dept_comp, teacher1.getDepartment());
        assertEquals(dept_comp, st.getDepartment());
        teacher1.setDepartment(dp_math);
        assertEquals(dp_math, teacher1.getDepartment());
        assertEquals(dept_comp, grd_st.getDepartment());
    }

    @Test
    public void akts_test() {

        Course c2 = new Course(dept_comp, 100, "physics", 6, teacher1);

        st.addCourse(c1, 90);
        assertEquals(5, st.getAkts());
        st.addCourse(c2, 20);
        assertEquals(11, st.getAttemptAkts());
        assertEquals("AA", st.courseGradeLetter(c1));
        assertEquals("Failed", st.courseResult(c2));
        assertEquals(0.0, st.courseGPApoints(c2), 0.0);
        assertEquals(1.8, st.getGPA(), 0.1);

        Course c3 = new Course(dept_comp, 100, "physics", 6, teacher1);
        grd_st.addCourse(c3, 25);
        assertEquals("DC", grd_st.courseGradeLetter(c3));
        assertEquals(1.5, grd_st.courseGPApoints(c3), 0.0);
        assertEquals(1.5, grd_st.getGPA(), 0.0);
        assertEquals("Failed", grd_st.courseResult(c3));
        assertEquals(6, grd_st.getAttemptAkts());
        st.addCourse(c3, 60);
        assertEquals("CC", st.courseGradeLetter(c3));
        assertEquals(1.69, st.getGPA(), 0.2);

    }

    @Test
    public void tch_test() {
        Department dp_math = new Department("MATHS", "Mathematic");
        Teacher teacher2 = new Teacher(2, "Murat Ak", "murat@gmail.com", 111L, dp_math);

    }

    @Test
    public void exception_test() {
        try {
            Course c3 = new Course(dept_comp, 201, "algorithm", 5, teacher1);
            st.courseResult(c3);
        } catch (CourseNotFoundException e) {
            System.out.println(e.toString());

        }
        try {
            Department dp = new Department("medicine", "maths");
            dp.setChair(teacher1);
        } catch (DepartmentMismatchException e) {
            System.out.println(e.toString());

        }
        try {
            Department dp = new Department("medicine", "maths");
            Course c = new Course(dp, 220, "calculus", 4, teacher1);
             assertEquals(teacher1, c.getTeacher());        
        } catch (DepartmentMismatchException e) {
            System.out.println(e);
        }
        try {
            st.addCourse(c1, 200);
        } catch (InvalidGradeException e) {
            System.out.println(e.toString());
        }
        try {
            teacher1.demote();
        } catch (InvalidRankException e) {
            System.out.println(e.toString());
        }
        try {
            Student s = new Student("selma", "selma.com", 101L, dept_comp);
        } catch (InvalidEmailFormat e) {
            System.out.println(e.toString());
        }
        try {
            Student student = new Student("cem", "cem@gmail.com", 102L, dept_comp);
        } catch (Exception e) {
            System.out.println("Even student number ." + e.toString());
        }
        try {
            Course new_course = new Course(dept_comp, 1000, "DB", 4, teacher1);
        } catch (Exception e) {
            System.out.println("Invalid course id. " + e.toString());

            /*  First ı could not throw exception for invalid course id.
             Because ı do nothing in setId method in course class.
             I created runtime exception for else situation.
              
             */
        }
        try {
            Course def = new Course(dept_comp, 201, "ABCD", -2, teacher1);
        } catch (Exception e) {
            System.out.println("Invalid akts . " + e.toString());
        }

    }

    @Test
    public void course_test() {
        assertEquals("Joseph Ledet", c1.getTeacher().getName());
        dept_comp.setChair(teacher1);
        assertEquals("Lecturer", dept_comp.getChair().getTitle());

    }

    @Test
    public void rank_test() {
        teacher1.promote();
        assertEquals(2, teacher1.getRank());
        teacher1.demote();
        assertEquals(1, teacher1.getRank());
    }

}
