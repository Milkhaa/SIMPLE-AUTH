# Steps to setup a MySql DB locally
- Run MySql latest docker image
```
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=my-secret-pw -d -p 3306:3306 mysql:latest
```

This make the db available at port 3306 with username=root and password=my-secret-pw

- How to connect to the DB
```
mysql -h 127.0.0.1 -P 3306 -u root -p
```

- Create a database and a table
```
CREATE DATABASE IF NOT EXISTS auth_db;
USE auth_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255)
);

-- Insert test user (password: test123) -- not required where registration is enabled
INSERT INTO users (username, password, name, email) 
VALUES (
    'test', 
    '$2a$12$JOxPZ3BZ/lr7CAMADQ2lue.kClLnI/7gwSpH5CeU46aWIJMf1At8a',
    'Test User',
    'test@example.com'
);

```

# Steps to run the backend application
```
mvn clean install
mvn spring-boot:run
```
