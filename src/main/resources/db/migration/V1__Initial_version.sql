/* Create user openkm at localhost */
CREATE USER paytmdba@localhost IDENTIFIED BY 'softage@123';

/*Grant exclusive rights to openkm user*/
GRANT ALL ON paytmdb.* TO paytmdba@localhost WITH GRANT OPTION;