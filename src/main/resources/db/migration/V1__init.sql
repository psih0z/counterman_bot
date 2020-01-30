CREATE SEQUENCE seq_expense_id;
CREATE TABLE tbl_expense (
  id INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('seq_expense_id'),
  user_id int NOT NULL,
  chat_id bigint NOT NULL,
  amount numeric not null
);