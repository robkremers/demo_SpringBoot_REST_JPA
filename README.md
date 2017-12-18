# demo_SpringBoot_REST_JPA
Create a Spring Boot application using REST, JPA, basic Authorization (Spring Security), Actuator, H2 database.

Date	: 2017-09-21.
Purpose	: Application in Spring Boot, resulting in a RESTful web service on top of a MySQL / H2 database.

===================================================================================================
Maak een Spring Boot Applicatie welke voldoet aan de volgende uitgangspunten:
- Gebruik Maven en Java 8
	- Standaard. Done.
- Maak een entiteit met daarin 3 verschillende type velden (Spring Data JPA + in memory database)
	- Standaard. Done.
		- Entities:
			- Topic
			- Course
				- Many-To-One relation with Topic.
- Maak een Rest API voor het maken en ophalen van deze entiteit (hint @RestController)
	- Standaard. Done.
- Valideer de input op je RestController bij het maken van de entiteit (JSR303 Bean validation, hint @Valid annotation)
	- https://docs.oracle.com/javaee/7/api/
		- javax.validation
			Annotation Type Valid
	- https://www.mkyong.com/spring-mvc/spring-rest-api-validation/
		- In Spring MVC, just annotate a @Valid on the @RequestBody to fire the validation process.
- Beveilig met Basic Auth (Spring Security)
	- Opzoeken:
		- Spring Security.
			- https://spring.io/guides/gs/securing-web/
			- https://github.com/spring-projects/spring-data-examples/tree/master/rest/security
			- https://www.javacodegeeks.com/2017/10/secure-spring-boot-rest-api-using-basic-authentication.html
			- Youtube: Spring Security in a Spring Boot App with Example | Tech Primers: https://www.youtube.com/watch?v=3s2lSD50-JI
			- https://www.javatips.net/api/spring-data-examples-master/rest/security/src/main/java/example/springdata/rest/security/Application.java
			- https://code.tutsplus.com/tutorials/wp-rest-api-setting-up-and-using-basic-authentication--cms-24762
	- Done. Zie details (username / password, implementatie) beneden.
- Activeer production health en metrics endpoints (Actuator)
		https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html
		https://spring.io/guides/gs/actuator-service/
	- Done:
		http://localhost:9001/health # etc.
- Maak gebruik van Github of Bitbucket als source control.
	- Standaard. Aan het eind.

Optioneel:
- Maak een Docker Image met daarin de Spring Boot Applicatie (Dockerfile)
- Genereer documentatie van de REST API met Spring REST Docs
	- When generating the project choose:
		- Web
			- REST Docs

===================================================================================================

Sources:
- Java Brains: Spring Boot Quick Start
	- Part 8: https://www.youtube.com/watch?v=bDtZvYAT5Sc&index=8&list=PLmbC-xnvykcghOSOJ1ZF6ja3aOgZAgaMO
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-using-springbootapplication-annotation
	- etc.
- https://docs.spring.io/spring-boot/docs/current/api/index.html?org/springframework/boot/SpringApplication.html	
	- etc.
- https://start.spring.io/
- http://mvnrepository.com/

Related to (H2) in memory database:
- https://memorynotfound.com/spring-boot-spring-data-jpa-hibernate-h2-web-console/
- http://zetcode.com/articles/springbootresth2/
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html
- Dan Vega: Spring Boot H2 Database Console: https://www.youtube.com/watch?v=tSJW5NKPhcM

This application has started as a Spring Starter project.
Choosen is: Actuator, H2 database, JPA, WEB, Basic Authorization (Security).

Alternatives:
- Start as a maven quick start project and add dependencies.
- https://start.spring.io/
	- Generate a maven project with the necessary functionality. Download and open with an IDE (e.g. Eclipse).

- Starting Spring Boot:
	- Sets up default configuration.
	- Starts Spring application context.
	- Performs class path scan.
	- Starts the embedded Tomcat server.
	
Starting the application and using the following in the chrome or IE browser:
	http://localhost:1024/hello <enter>
	
	Shows:
	Hi


@RestController
- The use of this annotation on top of a controller class means that default all content will be handled as JSON.


Bill of Materials:
This is the list of libraries (jars) that will be added to a spring boot application and that are considered to work well (approved by Spring Boot).

The advantage of using an embedded Tomcat Server:
- Convenience (e.g. with regards to compatibility).
- Servlet container config is now application config (see resource file application.properties).
- Standalone application.
- Useful for microservices architecture.

---------------------------------------------------------------------------------------------------

Resources / Model:

A Topic can have multiple Courses and a Course can have multiple Lessons.


Using IE11 I have difficulties showing /topics/all.
Using Chrome I don't have problems.
I am using Postman as an addon to Chrome in order to add topics defined using json.
- The POST method in the controller will receive a @RequestBody Topic topic because the content is not a primitive value but consists of an instance of a class; can not be treated like a primitive.
- In case you want to POST a json defined instance:
	- Add the instance to the body
	- Add the following information to the header:
		- Key:
			- Content-type
		- Value:
			- application/json
	- Choose: POST
	- Click Send.
	- If everything is processed correctly the result will be response code 200.
	- Otherwise a different response code will be given (400 or 500 range).
	  And in the logging of the RESTful application some meaningful error will have been logged.
	  
Questions:
- How to show in as xml?


---------------------------------------------------------------------------------------------------
Tests with Courses, that have a many-to-one relationship with topics.

Postman:

POST	http://localhost:1024/topics
One by one, because post as defined in TopicController takes only one topic at a time:

    {

        "name": "Unix",
        "detail": "Ellen Quigley",
        "description": "Unix Description"
    }
	
    {

        "name": "Java",
        "detail": "Java SE",
        "description": "Java Description"
    }
	
    {

        "name": "JavaScript",
        "detail": "JavaScript",
        "description": "JavaScript Description"
    }

GET 	http://localhost:1024/topics

[
    {
        "id": 1,
        "name": "Unix",
        "detail": "Ellen Quigley",
        "description": "Unix Description"
    },
    {
        "id": 2,
        "name": "Java",
        "detail": "Java SE",
        "description": "Java Description"
    },
    {
        "id": 3,
        "name": "JavaScript",
        "detail": "JavaScript",
        "description": "JavaScript Description"
    }
]

Now look for courses, connected to the topic, identified by id = 1 (Unix).

GET http://localhost:1024/topics/1/courses

Result, because I have to add courses, connected to this topic: 
[]

POST http://localhost:1024/topics/1/courses
(Put it in the body; with Headers: Content-Type: application/json

{
	"name": "Unix Basics",
	"description": "Unix Basics Description" 
}

GET	http://localhost:1024/topics/1/courses

[
    {
        "id": 1,
        "name": "Unix Basics",
        "description": "Unix Basics Description",
        "topic": {
            "id": 1,
            "name": "Unix",
            "detail": "Ellen Quigley",
            "description": "Unix Description update"
        }
    }
]

POST http://localhost:1024/topics/1/courses

{
	"name": "Unix Advanced",
	"description": "Unix Advanced Description" 
}

GET	http://localhost:1024/topics/1/courses

[
    {
        "id": 1,
        "name": "Unix Basics",
        "description": "Unix Basics Description",
        "topic": {
            "id": 1,
            "name": "Unix",
            "detail": "Ellen Quigley",
            "description": "Unix Description update"
        }
    },
    {
        "id": 2,
        "name": "Unix Advanced",
        "description": "Unix Advanced Description",
        "topic": {
            "id": 1,
            "name": "Unix",
            "detail": "Ellen Quigley",
            "description": "Unix Description update"
        }
    }
]

GET	http://localhost:1024/topics/1/courses/2

{
    "id": 2,
    "name": "Unix Advanced",
    "description": "Unix Advanced Description",
    "topic": {
        "id": 1,
        "name": "Unix",
        "detail": "Ellen Quigley",
        "description": "Unix Description update"
    }
}

PUT http://localhost:1024/topics/1/courses/2

{
	"name": "Unix Advanced",
	"description": "Unix Advanced Description Upgrade" 
}



---------------------------------------------------------------------------------------------------
Populate the H2 database:

Chrome:
http://localhost:1024/console/
- Log in using the standard credentials (check also application.properties).

- Create a table with content:
create table topic (id bigint primary key auto_increment, topic varchar(50), name varchar(50), description varchar(100) ); 	
			  
insert into topic (topic, name, description) values ( 'spring', 'Spring Framework', 'Spring Framework Description');
insert into topic (topic, name, description) values ( 'java', 'Core Java', 'Core Java Descriptio');
insert into topic (topic, name, description) values ( 'javascript', 'JavaScript', 'JavaScript Description');
commit;

Result:
The table and sequence will now be visible in the console.

Note:
- I wanted to do this using data.sql / schema.sql but this was not successful. Somehow Eclipse does not pick this up.
  (placed in src/main/resources)

Until Spring Boot Quick Start 30.
Adding additional entities.


---------------------------------------------------------------------------------------------------
Run as an application:

$ mvn clean install <enter>

Resullt:
[INFO] --- maven-jar-plugin:2.6:jar (default-jar) @ ProjectSpringBootRESTJPAH2 ---
[INFO] Building jar: E:\JavaDevelopment\WebServiceWorkspace\ProjectSpringBootRESTJPAH2\target\ProjectSpringBootRESTJPAH2-0.0.1-SNAPSHOT.jar


LTAdmin@LTAdmin-PC MINGW64 /e/JavaDevelopment/WebServiceWorkspace/ProjectSpringBootRESTJPAH2
$ java -jar ./target/ProjectSpringBootRESTJPAH2-0.0.1-SNAPSHOT.jar

2017-12-17 21:54:15.515  INFO 3092 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-12-17 21:54:15.531  INFO 3092 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 0
2017-12-17 21:54:15.695  INFO 3092 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 1024 (http)
2017-12-17 21:54:15.703  INFO 3092 --- [           main] com.rkremers.rest.Application            : Started Application in 8.844 seconds (JVM running for 9.471)


---------------------------------------------------------------------------------------------------
Using the actuator:

pom.xml:

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		
application.properties:

###
# Actuator port.
###
management.port=9001
management.health.defaults.enabled=true
management.health.diskspace.enabled=true
management.security.enabled=true

Setting the last property to 'false' allows the use of the actuator without security.
Examples:
http://localhost:9001/env
http://localhost:9001/health
http://localhost:9001/beans

---------------------------------------------------------------------------------------------------
Basic Authorization:

I took the basics from:
			- https://www.javatips.net/api/spring-data-examples-master/rest/security/src/main/java/example/springdata/rest/security/Application.java
			- https://code.tutsplus.com/tutorials/wp-rest-api-setting-up-and-using-basic-authentication--cms-24762
			
Added:
- package com.rkremers.rest.security
- package com.rkremers.rest
	- Security part.
		- Normal user: test / password
		- Admin      : rob / kremers
		- POST and PUT localhost:1024/topics and sub-calls are protected.
		  But actually GET is also affected.
		  
When using Chrome:
> http://localhost:1024/topics <enter>
	- Username / Password will be asked.
	
When using Postman:
- Tab Authorization:
	- Username: test
	- Password: password
- Update request.

In Application.java:

withUser("rob").password("kremers").roles("USER", "ADMIN", "ACTUATOR");

The admin user is not only admin for the application but has also received the role "ACTUATOR" to enable this user to see the actuator information.

Now:
GET http://localhost:1024/topics <enter>
will show nothing (because first content has to be inserted into the runtime H2 database) instead of an error.

GET http://localhost:9001/env <enter>
will show the environment properties.

GET http://localhost:9001/health <enter>

{
    "status": "UP",
    "diskSpace": {
        "status": "UP",
        "total": 500121161728,
        "free": 410911494144,
        "threshold": 10485760
    },
    "db": {
        "status": "UP",
        "database": "H2",
        "hello": 1
    }
}

---------------------------------------------------------------------------------------------------
Validation:

javax.validation
Annotation Type Valid
public @interface Valid

Marks a property, method parameter or method return type for validation cascading. 
Constraints defined on the object and its properties are be validated when the property, method parameter or method return type is validated. 

This behavior is applied recursively.

Case:
@Entity
public class Topic {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private long id;
	@NotEmpty(message="The topic name can not be empty.")
	@Length(max=100)
	private String name;
	private String detail;
	private String description;

- So it's now illegal to POST a topic with an empty name.

If @RestController	
@RequestMapping("/topics")
public class TopicController {

...
	
	@RequestMapping(method=RequestMethod.POST)
	public void addTopic(@RequestBody Topic topic) {
		topicService.addTopic(topic);
	}

...
}

And I POST:

    {

        "name": "",
        "detail": "Ellen Quigley",
        "description": "Unix Description"
    }

The result is:
{
    "timestamp": 1513554752269,
    "status": 500,
    "error": "Internal Server Error",
    "exception": "javax.validation.ConstraintViolationException",
    "message": "Validation failed for classes [com.rkremers.rest.model.Topic] during persist time for groups [javax.validation.groups.Default, ]\nList of constraint violations:[\n\tConstraintViolationImpl{interpolatedMessage='The topic name can not be empty.', propertyPath=name, rootBeanClass=class com.rkremers.rest.model.Topic, messageTemplate='The topic name can not be empty.'}\n]",
    "path": "/topics"
}

If I add @Valid:

	@RequestMapping(method=RequestMethod.POST)
	public void addTopic(@Valid @RequestBody Topic topic) {
		topicService.addTopic(topic);
	}

The result is as below:
(also notice that the normal logging now does not show an exception trace. Meaning that the validation is properly handled).

{
    "timestamp": 1513554879830,
    "status": 400,
    "error": "Bad Request",
    "exception": "org.springframework.web.bind.MethodArgumentNotValidException",
    "errors": [
        {
            "codes": [
                "NotEmpty.topic.name",
                "NotEmpty.name",
                "NotEmpty.java.lang.String",
                "NotEmpty"
            ],
            "arguments": [
                {
                    "codes": [
                        "topic.name",
                        "name"
                    ],
                    "arguments": null,
                    "defaultMessage": "name",
                    "code": "name"
                }
            ],
            "defaultMessage": "The topic name can not be empty.",
            "objectName": "topic",
            "field": "name",
            "rejectedValue": "",
            "bindingFailure": false,
            "code": "NotEmpty"
        }
    ],
    "message": "Validation failed for object='topic'. Error count: 1",
    "path": "/topics"
}
