# RestAPI desafio Softplan (softplan-andre-cabral-api)

RestAPI com todos os endpoints necessário para cumprir o desafio
técnico da Softplan

---

## Rodar aplicação Spring Boot
```
mvn spring-boot:run
```

## Configurar Spring Datasource, JPA, App properties
Abrir `src/main/resources/application.properties`
- Este projeto já está configurado para se comunicar com um banco de testes
da AWS (para simplificar os teste da api).
```
spring.datasource.url=jdbc:mysql://database-1.cvdrvan6iiux.us-east-2.rds.amazonaws.com:3306/softplan?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=3271512a

spring.jpa.show-sql=true

# App Properties
softplan.app.jwtSecret= softplanSecretKey
softplan.app.jwtExpirationMs= 86400000

project.version=@project.version@
project.name=@project.name@
project.description=@project.description@
```
#### Tavez você não queira usar o banco de dados de teste
O banco de dados de teste da AWS é lento e talvez não esteja disponível quando você
precisar. Pensando nisso, esta api foi projeta para ser rodada em seu próprio servidor MySQL. Sendo assim, basta alterar as variáveis de conexão para seu próprio servidor. A api irá instânciar um novo banco com todas as tabelas necessárias e também irá cadastrar um usuário Admin para teste.

## Usuário Admin inicial
Para acessar qualquer recurso da api (com exceção do login) é necessáro ter privilégios. Para evitar que o usuário que irá testar a aplicação tenha que cadastrar um usuário manualmente, a aplicação cadastra um usuário Admin com os seguintes dados:

- Nome de usuário: admin
- Senha: 123456

---

## Suposições e caracteristicas do projeto

### Entidades e relacionamentos
- Cada usuário (User) pode estar relacionado com um ou mais tipo de usuário (Role)
- Cada processo (Procedure) deve estar relacionado a um usuário (User de cadastro). E pode estar relacionado a vários pareceres.
- Cada parecer (Opinion) deve estar relacionado a um usuário (User) e um processo (Procedure)

### Ferramentas utilizadas

#### Spring Security
Para atender a proposta do desafio, este projeto conta com restrição de acesso a rotas e recursos tanto no front-end quanto no back-end.

- Toda autenticação é feita através de JWT
- Foi utilizado o conceito de privilégios, para servir determinado recurso a determinado usuário. Para isso, foi criado uma entity chamada Role (tipo de usuário) que é responsável por representar esses privilégios. Cada usuário pode estar vinculado a um ou mais Roles, permitindo assim que ele acesse diferentes níveis de recursos.
> Roles disponíveis
> - ROLE_ADMIN
> - ROLE_TRIADOR
> - ROLE_FINALIZADOR

Desta forma, para cada Role nós temos um nível diferente de acesso aos recursos:
> ROLE_ADMIN

Tem acesso ao serviço de usuário:
- Listar todos
- Listar por ID
- Cadastrar
- Atualizar
- Remover por ID

> ROLE_TRIADOR

Tem acesso ao serviço de processos:
- Listar todos
- Listar por ID
- Cadastrar
- Atualizar lista de usuários

> ROLE_FINALIZADOR

Tem acesso ao serviço de processos e pareceres:
- Listar todos os processos sem parecer pelo id do usuário
- Cadastar um parecer


### Documentação da api com Swagger
A documentação de todos endpoints da api pode ser encontrada na seguinte URL:

```
http://localhost:8080/swagger-ui.html#/
```

Caso a api esteja rodando localmente na porta 8080 que é a porta padrão deste projeto

> Atenção: é necessário autenticar com um usuário ADMIN para ter acesso a todos endpoints

> Ex.: Clique no botão Authorize e no campo Value insira um token de autenticação válido, depois clique em Authorize novamente
> Exemplo de token válido: Bearer + token_gerado_ao_fazer_login

### Flyway
Ferramenta responsável por fazer os migrations do banco de dados, criando e atualizando o mesmo conforme o aplicativo evolui. É atravéz dele que é realizado o cadastro do usuário inicial Admin e os respectivos Roles: ROLE_ADMIN, ROLE_TRIADOR e ROLE_FINALIZADOR

### ModelMapper
Todas as entidades do domínio da aplicação são isoladas do recusos da mesma através do ModelMapper. Com ele é possível usar o conceito de Data Transfer Object (DTO), trazendo maior segurança a aplicação, versatilidade, manutenibilidade e evitar o uso da notação @JsonIgnore

### Validation com Spring Boot Starter Validation
O projeto como um todo conta com validação do campos tanto no front-end quanto aqui na api. Para isso foi utilizado a ferramenta Spring Boot Starter Validation

## Referências
> https://spring.io/projects/spring-security

> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

> https://bezkoder.com/spring-boot-jwt-authentication/


