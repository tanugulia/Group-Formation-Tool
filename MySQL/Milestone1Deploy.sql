-- users table

CREATE TABLE users(
   user_id  bigint auto_increment,
   first_name VARCHAR(40) NOT NULL,
   last_name VARCHAR(40) NOT NULL,
   password VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   banner_id VARCHAR(40) NOT NULL,
   PRIMARY KEY (user_id)
);
insert into users values(0,'Admin','Admin','$2a$10$SPOumChj3ONg.hu20UGTSOwbD4nCfV.wXMzl5HPU6cyx903X1TyoW','haard.shah@dal.ca','B00000000');

-- course
CREATE TABLE course(
   course_id VARCHAR(40) NOT NULL,
   course_name VARCHAR(40) NOT NULL,
   course_credits INT NOT NULL,
   course_details TEXT NOT NULL,
   PRIMARY KEY (course_id)
);

-- term
CREATE TABLE term(
   term_id INT NOT NULL,
   term VARCHAR(40) NOT NULL,
   PRIMARY KEY (term_id)
);
insert into term values(1,'Fall');
insert into term values(2,'Winter');
insert into term values(3,'Summer');

-- year_term
CREATE TABLE year_term(
   yt_id INT NOT NULL,
   year YEAR(4) NOT NULL,
   term_id INT NOT NULL,
   start_date Date NOT NULL,
   end_date Date NOT NULL,
   PRIMARY KEY (yt_id),
   FOREIGN KEY(term_id)
   REFERENCES term(term_id)
);
insert into year_term values(0, '1901', 1, '1901-01-01', '2050-01-01');

-- role
CREATE TABLE roles(
   role_id INT NOT NULL auto_increment,
   role_type VARCHAR(20) NOT NULL,
   PRIMARY KEY (role_id)
);
insert into roles values(0,'Instructor');
insert into roles values(0,'TA');
insert into roles values(0,'Student');

-- role_assignment
CREATE TABLE role_assignment(
   user_id bigint NOT NULL,
   course_id VARCHAR(40) NOT NULL,
   yt_id INT NOT NULL,
   role_id INT NOT NULL,
   PRIMARY KEY (user_id, course_id, yt_id, role_id),
   FOREIGN KEY(user_id) REFERENCES users(user_id),
   FOREIGN KEY(course_id) REFERENCES course(course_id),
   FOREIGN KEY(role_id) REFERENCES roles(role_id),
   FOREIGN KEY(yt_id) REFERENCES year_term(yt_id)
);

-- forgot password
CREATE TABLE forgot_password(
   user_id bigint NOT NULL,
   email VARCHAR(255) NOT NULL,
   temporary_lik VARCHAR(255) NOT NULL,
   PRIMARY KEY (user_id),
   FOREIGN KEY(user_id) REFERENCES users(user_id)   
);



