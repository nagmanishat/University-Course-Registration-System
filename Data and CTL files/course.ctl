load data 
infile 'course.csv' "str '\r\n'"
append
into table COURSE
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( COURSE_NAME CHAR(4000),
             COURSE_NUMBER CHAR(4000),
             CREDIT_HOURS CHAR(4000),
             DEPARTMENT CHAR(4000)
           )
