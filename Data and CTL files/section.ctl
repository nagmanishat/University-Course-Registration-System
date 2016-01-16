load data 
infile 'section.csv' "str '\r\n'"
append
into table SECTION
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( SECTION_IDENTIFIER CHAR(4000),
             COURSE_NUMBER CHAR(4000),
             SEMESTER CHAR(4000),
             YEAR CHAR(4000),
             INSTRUCTOR CHAR(4000)
           )
