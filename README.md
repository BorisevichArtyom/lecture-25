Home task #21
1. git repo and best practices
2. base on #20
3. add DAO and hibernate tiers with spring integration
4. all configurations in Java code (don't use xml)
5. deploy to servlet container per student 
___
## How to use?
    To run environment:  docker-compose up -d
    To run liquibase and fill in database: mvn -pl lecture-20-primary-persistance liquibase:update 
    mvn clean install to build project and run tests
    Copy war file into Tomcat  directory (/webapps)
    Run Tomcat bat file bin/startup.bat
___
