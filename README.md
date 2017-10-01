# spring-kotlin-jwt-sample
Example using Kotlin, MongoDB and JWT to authenticate Spring Boot web application on Undertow
 
#Demo
* Install and run MongoDB
* POST http://localhost:8080/user/login with {'username': 'quang', 'password', '1'}.
The server will return JWT token
* Include token in Authorization header for authenticated request (you can try GET /user/)