## SpringBoot 2.7 + MyBatis 3 + Oracle/MariaDB based web project template ##
This sample is a web project template created based on SpringBoot 2.7 + MyBatis 3 + Oracle/MariaDB (Maven).

We plan to grow into a small product in the future by implementing various functions that are often used in web development in advance.

A more detailed explanation can be obtained from [here] (http://forest71.tistory.com/78).
The framework has changed based on the referenced source.
The Oracle version may have errors in some functions after DB conversion.

3 projects shared on the blog have been merged into one.
project9 - 3> Multi Bulletin Board
pms9 - 2) project management
groupware9 - 1) Electronic payment

### Key Implementation Features ###
#### 1) Electronic payment
- Schedule: Monthly schedule
- Payment: drafting, documents to be approved, documents to be approved
- E-mail: new mail, received mail, sent mail
- notice board
#### 2) Project management
- Create assignments and assign them individually
- View task information (task, schedule, user-centered)
- Enter individual task progress
#### 3) Multi-Bulletin Board, User/Group Management
- Multi-Bulletin Board (Infinite Comments, Likes, etc.)
- Member function: All pages are accessible only to members. Login/Logout function (save log). Member management, etc.
- Security function: Divided into general users (U) and administrators (A), general users cannot access the admin page.
- User selection: department, user selection function (popup)
- Sample date selection and chart usage
- Excel download (jXLS) sample
- Multilingual processing
- Design: Bootstrap-based responsive web application (SB-Admin)
- Handling common error pages (404, 500)
- Log processing (logback, log4jdbc)

#### Source Provided
- CrossOrigin - DevkbilApplication.java, WebMvcConfig.java - cancel remark
- Banner Custom - mts-banner.txt, application.properties, DevkbilApplication.java

### Major LIBs ###
- JQuery-2.2.3
- CKEditor 4.5.10 
- SB-Admin 2, morris v0.5.0, DatePicker
- DynaTree 1.2.4
- jQuery EasyUI 1.4.3
- FullCalendar v5 

### Development Environment ###
    Programming Language - Java 1.8
    IDE - intelliJ
    DB - Oracle/MariaDB
    Framework - MyBatis, SpringBoot 2.7
    Build Tool - Maven

### installation ###
- Create a database (mts) in OracleDB (user_database_oracle.sql) and create tables and data by executing tables_oracle.sql and tableData_oracle.sql.
- Create a database (mts) in MariaDB (user_database_myriadb.sql) and create tables and data by executing tables_myriadb.sql and tableData_myriadb.sql.
- Enter appropriate connection information in applicationContext.xml.
- Run mts from Tomcat or Intellij/Eclipse
- Connect to http://localhost:9090/mts/
- ID/PW: admin/admin, user1/user1, user2/user2 ...
  The Oracle PW source is not changed, so the PW is entered as an ID.

### elasticsearch configuration ###
    1. Install the stemming analyzer nori provided by default in Elasticsearch
    ./elasticsearch/bin/elasticsearch-plugin install analysis-nori
    2. Pre-copy
    ./elasticsearch/stopwords.txt, synonym.txt, userdict.txt -> elasticsearch/config
    3. Create index
    curl -XPUT localhost:9200/mts -d @index_board.json -H "Content-Type: application/json"

### License ###
MIT
  
  
