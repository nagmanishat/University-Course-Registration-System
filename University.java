import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class University {
	static Connection con;
	public static int id;

	public static void main(String args[]) throws SQLException {
		Scanner sc = new Scanner(System.in);
		int ch;
		// establishing connection.
		try {
			Class.forName
			("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {
			System.out.println("Could not load the driver");
		}
		// taking the login details of user
		
		 System.out.println("Please enter your login id"); 
		 String user = sc.nextLine(); 
		 System.out.println("Please enter your password");
		 String pass = sc.nextLine();
		 

		con = DriverManager.getConnection("jdbc:oracle:thin:@csoracle.utdallas.edu:1521:student", user , pass);

		// taking student id

		System.out.println("Please enter Student Number:");
		id = sc.nextInt();

		do {
			System.out.println("Please enter your choice");
			System.out.println("1: Add a Class");
			System.out.println("2: Drop a Class");
			System.out.println("3. See my Schedule");
			System.out.println("4: Exit");
			ch = sc.nextInt();

			switch (ch) {

			case 1:
				Add();
				break;
			case 2:
				drop();
				break;
			case 3:
				schedule();
				break;
			case 4:
				System.out.println("End of the session");
				con.close();
				break;
			default:
				System.out.println("Invalid choice");
				break;
			}
		} while (ch != 4 && ch < 4);
	}

	private static void schedule() throws SQLException {
		// Display schedule

		String query = "select s.course_number,g.section_identifier,c.course_name,s.instructor "
				+ "from GRADE_REPORT g,SECTION s,COURSE c,STUDENT st "
				+ "where g.section_identifier = s.section_identifier "
				+ "and s.course_number = c.course_number and st.student_number = g.student_number "
				+ "and g.student_number  =  " + id;
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		boolean hasRows = false;
		boolean flag = false;
		while (r.next()) {
			hasRows = true;
			if (flag == false){ 
			System.out.println("Your current schedule is:");
			flag = true;
			}
			String course_number = r.getString(1);
			String section_identifier = r.getString(2);
			String course_name = r.getString(3);
			String instructor = r.getString(4);
			System.out.println(course_number + " " + section_identifier + " " + course_name + " " + instructor);
		}
		if (!hasRows) {
			System.out.println("Schedule does not exist for student");
			r.close();
			return;
		}
	}

	private static void drop() throws SQLException {
		// taking input
		Scanner sc = new Scanner(System.in);
		String coursenumber;
		int section_number;
		System.out.println("Enter the Course number");
		coursenumber = sc.nextLine();
		// Check if course entered is valid
		String course = "select * from course where course_number = '"+coursenumber+"'";
		PreparedStatement cs = con.prepareStatement(course);
		ResultSet rcs= cs.executeQuery();
		boolean hasRows10 = false;
		while (rcs.next()) {
			hasRows10 = true;
		}

		if (!hasRows10) {
			System.out.println("Please enter valid course");
			cs.close();
			return;
		}
		
		System.out.println("Enter Section number");
		section_number = sc.nextInt();
		// Check if section entered belongs to the course
		String queryc = "select section_identifier from section " + "where course_number = '" + coursenumber
				+ "' and section_identifier = " + section_number;
		PreparedStatement c = con.prepareStatement(queryc);
		ResultSet rc = c.executeQuery();
		boolean hasRows = false;
		while (rc.next()) {
			hasRows = true;
		}

		if (!hasRows) {
			System.out.println("Section does not exist for the course entered");
			c.close();
			return;
		}

		// Drop class from table grade_report
		int rows;
		String queryd = "delete from grade_report where grade is null and student_number = ? and section_identifier = ?";
		PreparedStatement d = con.prepareStatement(queryd);
		d.clearParameters();
		d.setInt(1, id);
		d.setInt(2, section_number);
		rows = d.executeUpdate();

		if (rows == 0) {
			System.out.println(
					"No class dropped, please check if the student wants to drop class enrolled for Spring 2007");
		} else {
			System.out.println("Class dropped");
		}

	}

	private static void Add() throws SQLException {
		// taking input
		Scanner sc = new Scanner(System.in);
		String coursenumber;
		int section_number;

		//Enter course number
		System.out.println("Enter the Course number");
		coursenumber = sc.nextLine();
		
		// Check if the student is taking the course belonging to the department
		String dept = "select count(*) from course where '"+coursenumber+"' in (select c.course_number from course c, student s where s.major = c.department and s.student_number = "+id+")";
		PreparedStatement dp = con.prepareStatement(dept);
		ResultSet rdp = dp.executeQuery();
		int count = 0;
		while (rdp.next()) {
			count = rdp.getInt(1);
		}
			if (count == 0){
				System.out.println("Please take courses belonging to the student's department");
				dp.close();
				return;
			}

       //Enter section number
			System.out.println("Enter section number");
			section_number = sc.nextInt();
			
			// Check if section entered belongs to the course
			String queryc = "select section_identifier from section " + "where course_number = '" + coursenumber
					+ "' and section_identifier = " + section_number;
			PreparedStatement c = con.prepareStatement(queryc);
			ResultSet rc = c.executeQuery();
			boolean hasRows0 = false;
			while (rc.next()) {
				hasRows0 = true;
			}

			if (!hasRows0) {
				System.out.println("Section does not exist for the course entered");
				c.close();
				return;
			}

		// Check if course entered is in the database
		String query1 = "select Course_number from SECTION where Semester = 'Spring' "
				+ "and Year = '7' and Course_number= '" + coursenumber + "' " + "and Section_identifier = "
				+ section_number;
		PreparedStatement s1 = con.prepareStatement(query1);
		ResultSet r1;
		try {
			r1 = s1.executeQuery();
		} catch (SQLException e) {

			System.out.println("Could not execute query");
			s1.close();
			return;
		}
		boolean hasRows = false;
		while (r1.next()) {
			hasRows = true;
			//Major = r1.getString(1);
			//System.out.println(11);
		}

		if (!hasRows) {
			System.out.println("Course not available for Spring 2007,please check coursebook");
			s1.close();
			return;
		}

		// Check if the student has taken all prerequisite courses
		String prereq = "select prerequisite_number from prerequisite " 
		                + "where course_number = '" + coursenumber +"'";
		PreparedStatement pq = con.prepareStatement(prereq);
		ResultSet rp;
		rp = pq.executeQuery();
		boolean hasRows2 = false;
		while (rp.next()) {
			hasRows2 = true;
		}

		if (hasRows2 == true) {
			String prereq1 = "select course_number from section s1, grade_report g1 where s1.section_identifier = g1.section_identifier and g1.student_number = "+id+" and g1.grade is not null and s1.section_identifier in (select s.section_identifier from prerequisite p, section s where p.prerequisite_number = s.course_number and p.course_number = '"+coursenumber+"')";
			PreparedStatement pq1 = con.prepareStatement(prereq1);
			ResultSet rp1;
			rp1 = pq1.executeQuery();
			boolean hasRows3 = false;
			while (rp1.next()) {
				hasRows3 = true;
			}
			if (hasRows3 == false){
				System.out.println("Please take the prerequisite course first");
				pq1.close();
				return;
			}
		}
		
		// Check if entries already exist
		String check = "Select * from grade_report where student_number = "+id+" and section_identifier = "+section_number;
		PreparedStatement ck = con.prepareStatement(check);
		ResultSet rck;
		rck = ck.executeQuery();
		boolean hasRows4 = false;
		while (rck.next()) {
			hasRows4 = true;
		}
		if (hasRows4 == true){
			System.out.println("Student has already enrolled for this class");
			ck.close();
			return;
		}
		// Add class for the student
		int rows;
		String insert = "Insert into grade_report values (?,?,'')";
		PreparedStatement i = con.prepareStatement(insert);
		i.clearParameters();
		i.setInt(1, id);
		i.setInt(2, section_number);

		rows = i.executeUpdate();
		
		if (rows == 0) {
			System.out.println("Class not added");
		} else {
			System.out.println("Class added");
		}

	}

}


