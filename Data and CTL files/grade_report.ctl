load data 
infile 'grade_report.csv' "str '\r\n'"
append
into table GRADE_REPORT
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( STUDENT_NUMBER CHAR(4000),
             SECTION_IDENTIFIER CHAR(4000),
             GRADE CHAR(4000)
           )
