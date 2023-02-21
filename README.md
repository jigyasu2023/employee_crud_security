# employee_crud_security
Secure RESTful Web Service using spring-boot, spring-cloud-oauth2, JPA, MySQL and Hibernate Validator

**Create tables for users, groups, group authorities and group members**

```
create table if not exists  oauth_client_details (
  client_id varchar(255) not null,
  client_secret varchar(255) not null,
  web_server_redirect_uri varchar(2048) default null,
  scope varchar(255) default null,
  access_token_validity int(11) default null,
  refresh_token_validity int(11) default null,
  resource_ids varchar(1024) default null,
  authorized_grant_types varchar(1024) default null,
  authorities varchar(1024) default null,
  additional_information varchar(4096) default null,
  autoapprove varchar(255) default null,
  primary key (client_id)
);

create table if not exists permission (
  id int(11) not null auto_increment,
  name varchar(512) default null,
  primary key (id),
  unique key name (name)
) ;

create table if not exists role (
  id int(11) not null auto_increment,
  name varchar(255) default null,
  primary key (id),
  unique key name (name)
) ;

create table if not exists  user (
  id int(11) not null auto_increment,
  username varchar(100) not null,
  password varchar(1024) not null,
  email varchar(1024) not null,
  enabled tinyint(4) not null,
  accountNonExpired tinyint(4) not null,
  credentialsNonExpired tinyint(4) not null,
  accountNonLocked tinyint(4) not null,
  primary key (id),
  unique key username (username)
) ;

create table  if not exists permission_role (
  permission_id int(11) default null,
  role_id int(11) default null,
  key permission_id (permission_id),
  key role_id (role_id),
  constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
  constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
);

create table if not exists role_user (
  role_id int(11) default null,
  user_id int(11) default null,
  key role_id (role_id),
  key user_id (user_id),
  constraint role_user_ibfk_1 foreign key (role_id) references role (id),
  constraint role_user_ibfk_2 foreign key (user_id) references user (id)
);
```

`src/main/resources/data.sql`

```
INSERT INTO oauth_client_details (client_id, client_secret, web_server_redirect_uri, scope, access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, additional_information) VALUES ('mobile', '{bcrypt}$2a$10$gPhlXZfms0EpNHX0.HHptOhoFD1AoxSr/yUIdTqA8vtjeP4zi0DDu', 'http://localhost:8080/code', 'READ,WRITE', '3600', '10000', 'microservice', 'authorization_code,password,refresh_token,implicit', '{}');

 INSERT INTO permission (id,NAME) VALUES
 (1,'create_profile'),
 (2,'read_profile'),
 (3,'update_profile'),
 (4,'delete_profile');

 INSERT INTO role (NAME) VALUES
		('ROLE_admin'),('ROLE_editor'),('ROLE_operator');

  INSERT INTO permission_role (PERMISSION_ID, ROLE_ID) VALUES
     (1,1), /*create-> admin */
     (2,1), /* read admin */
     (3,1), /* update admin */
     (4,1), /* delete admin */
     (2,2),  /* read Editor */
     (3,2),  /* update Editor */
     (2,3);   /* read operator */


 insert into user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('1', 'admin','{bcrypt}$2a$12$xVEzhL3RTFP1WCYhS4cv5ecNZIf89EnOW4XQczWHNB/Zi4zQAnkuS', 'jigyasu2019india@gmail.com', '1', '1', '1', '1');
 insert into  user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('2', 'jig', '{bcrypt}$2a$12$udISUXbLy9ng5wuFsrCMPeQIYzaKtAEXNJqzeprSuaty86N4m6emW','jigyasu2019india@gmail.com', '1', '1', '1', '1');
 insert into  user (id, username,password, email, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked) VALUES ('3', 'user', '{bcrypt}$2a$12$udISUXbLy9ng5wuFsrCMPeQIYzaKtAEXNJqzeprSuaty86N4m6emW','jigyasu2019india@gmail.com', '1', '1', '1', '1');
 /*
 passowrds:
 admin - admin
 jig - user
 user - user
 */


INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES
    (1, 1), /* admin-admin */
    (2, 2); /* jig-editor */
    
```

### Test Authorization Service

**Get Access Token**

Let’s get the access token for `admin` by passing his credentials as part of header along with authorization details of appclient by sending `client_id` `client_pass` `username` `userpsssword`

Now hit the POST method URL via POSTMAN to get the OAUTH2 token.

**`http://localhost:8082/oauth/token`**

Now, add the Request Headers as follows −

* `Authorization` − Basic Auth with your Client Id and Client secret.

* `Content Type` − application/x-www-form-urlencoded 

![Screenshot from 2020-12-07 09-55-52](https://user-images.githubusercontent.com/31319842/101307887-b93ad580-3872-11eb-9c05-fecb8a9a0919.png)

Now, add the Request Parameters as follows −

- `grant_type` = password

- `username` = your username

- `password` = your password]

![Screenshot from 2020-12-07 09-55-58](https://user-images.githubusercontent.com/31319842/101307884-b809a880-3872-11eb-8e8a-f2f381c21468.png)

**HTTP POST Response**

```
{ 
  "access_token":"000ff762-414c-4605-858a-0ed7bee6f68e",
  "token_type":"bearer",
  "refresh_token":"79aabc70-f310-4c49-bf7e-516208b3bef4",
  "expires_in":999999,
  "scope":"read write"
}
```
## How to run spring-boot-secure-rest application?

### Build Project

Now, you can create an executable JAR file, and run the Spring Boot application by using the Maven shown below − For Maven, use the command as shown below −

**Project import in sts4 IDE** `File > import > maven > Existing maven project > Root Directory-Browse > Select project form root folder > Finish`

### Run project

After “BUILD SUCCESSFUL”, you can find the JAR file under the build/libs directory. Now, run the JAR file by using the following command −

```
Run As >Spring Boot App
```

Server Running on: `8082` port
