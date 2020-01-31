
USE studypet;

INSERT INTO developers
VALUES (1, 'Sam',3),
       (2, 'Bob',2),
       (3, 'Hugo',1),
       (4, 'Bill',5),
       (5, 'Jane',4),
       (6, 'Mike',null)
;
INSERT INTO accounts
VALUES (1, 'ACTIVE'),
       (2, 'INACTIVE'),
       (3, 'BANNED'),
       (4, 'ACTIVE'),
       (5, 'INACTIVE')
;
INSERT INTO skills
VALUES (1, 'JavaCore'),
       (2, 'SQl'),
       (3, 'Spring'),
       (4, 'Hibernate'),
       (5, 'StreamApi')
;
INSERT INTO `developer-skills`
VALUES (1, 2),
       (2, 1),
       (3, 4),
       (4, 3),
       (5, 1),
       (5, 3),
       (2, 4),
       (1, 5)
;