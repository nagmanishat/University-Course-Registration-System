load data 
infile 'student.csv' "str '\r\n'"
append
into table STUDENT
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( NAME CHAR(4000),
             STUDENT_NUMBER CHAR(4000),
             CLASS CHAR(4000),
             MAJOR CHAR(4000)
           )
