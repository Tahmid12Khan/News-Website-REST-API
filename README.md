# News-Website
<h4>A simple internship assignment project.</h4><br>
This project has been made using IntelliJ IDEA. The database that is used in the project is MySQL. The database properties should be written in application.properties. For example- <br>
<b>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver <br>
spring.jpa.hibernate.ddl-auto=update<br>
spring.datasource.url=jdbc:mysql://localhost:3306/database_name?useSSL=false<br>
spring.datasource.username=username<br>
spring.datasource.password=password<br>
</b>

Then after running the project go to the root directory. For example: localhost:8080/. There the news will be shown if there is at least one present. User can also add news by selecting "Add News" in the navigation bar. After that he/she can entry the news by providing title (max 50 characters), name of the author (max 50 characters), body or description of the story (max 5000 characters) and date(must be entered). By pressing "submit" the news will be stored in the database if the conditions are fullfilled. The news can be later seen in the homepage (the news will be sorted by id (descending) and id is auto incremented everytime a news is entried.) User can also download xml or json format by clicking the corresponding buttons. In the homepage, maximum 50 characters of the body will be shown. To see the entire body, the user has to click the "View HTML" button colored in blue or click the title or author of the desired news and he/she will be redirected to that page.<br> 
The project may be updated further.
