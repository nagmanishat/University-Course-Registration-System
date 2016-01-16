Step 1: Create database
In Univeristy_schema.sql first all the tables will be dropped if it exists, and then all the tables will be created.


Step 2: To execute:
Enter student number. Student number is unique. For each student we can add, drop or see the schedule of the class.

Add a class:
1.Enter course number and section number which is available for Spring 2007 , you can check table SECTION for the available courses.Also take courses belonging to student's department.
2.To add a course please check if there is any prerequisite course, if the prerequisite course is not taken earlier, you will not be able to enroll for the course.
3.You will not be able to add the same course more than once for a student.
4. Table GRADE_REPORT is updated if a student is enrolled for the class with grade as null.


Drop a class:
1. Enter valid course and section number for the student which needs to be dropped.
2. In order to drop a class, student must be enrolled in the class for Spring 2007.
3.Please enter valid section for the course .Section and course number can be found in table SECTION.

See my schedule:
1. You can see the schedule for the student entered.