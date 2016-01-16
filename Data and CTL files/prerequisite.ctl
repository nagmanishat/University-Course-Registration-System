load data 
infile 'prerequisite.csv' "str '\r\n'"
append
into table PREREQUISITE
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( COURSE_NUMBER CHAR(4000),
             PREREQUISITE_NUMBER CHAR(4000)
           )
