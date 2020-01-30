create sequence seq_pos_id;
create table tbl_position (
  id int not null primary key default nextval('seq_pos_id'),
  user_id int not null,
  chat_id bigint not null,
  position int not null
);