CREATE TABLE User
  (id integer primary key not null auto_increment,
   mail varchar(80) UNIQUE,
   encrypted_password varchar(80) not null,
   salt varchar(10) not null,
   facebookmail varchar(30) unique,
   firstname varchar(20) not null,
   lastname varchar(40) not null,
   phonenumber varchar(30),
   created_at datetime,
   updated_at datetime null
  );

  CREATE TABLE Friend
  (id1 integer not null references User(id) on delete cascade,
  id2 integer not null references User on delete cascade,
   created_at datetime not null,
   primary key(id1, id2)
  );
  
  CREATE TABLE Challenge
  (id integer  primary key not null auto_increment,
  challenger integer not null references User(id) on delete set null,
  challenged integer not null references User(id) on delete set null,
  title varchar(100) not null,
  text varchar(1000) not null,
  completed integer default 0,
  started datetime not null,
  finished datetime,
  privacy integer,
  confirmed integer default 0,
  delivered integer default 0
  );
  
  CREATE TABLE Vote
  (id integer  primary key not null auto_increment,
  challenge integer not null references Challenge(id) on delete set null,
  voter integer not null references User(id) on delete set null,
  created_at datetime not null,
  type integer not null
  );
  
  create table Devices 
  (
  id integer primary key not null auto_increment,
  registration_id integer not null,
  owner integer not null references User(id) on delete cascade
  );