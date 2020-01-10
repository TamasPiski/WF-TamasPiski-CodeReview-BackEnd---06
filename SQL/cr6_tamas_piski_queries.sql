USE cr6_tamas_piski_schooldatabase;


SELECT name AS "Student name", c.idclasses AS "Class ID" 
FROM students AS s
JOIN classes AS c ON c.idclasses = s.idclass
WHERE c.idclasses = 2;

SELECT name AS "Student name", c.class_name AS "Class name" 
FROM students AS s
JOIN classes AS c ON c.idclasses = s.idclass
WHERE c.class_name = "1b";
