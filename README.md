## Projeto Library

#### Executar o projeto:

    git clone https://github.com/ricardorv/spring-boot-library.git
    cd spring-boot-library
    ./mvnw spring-boot:run
    
#### Usuários disponíveis para login

 - admin:admin
 - dev:dev (possui acesso ao swagger e h2)    

#### Documentação da API:

    http://localhost:8080/swagger-ui.html
    
#### Acesso banco de dados em memória

    http://localhost:8080/h2
    JDBC URL: jdbc:h2:mem:testdb
    USERNAME: sa
    PASSWORD: 