# Desafio TQI

Criação de API REST com CRUD de cliente, login, cadastro de empréstimo, obter empréstimos e obter detalhe de empréstimo por cliente.

### Descrição do projeto

Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:

#### I. Cadastro de clientes
- O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.

##### II. Login
- A autenticação será realizada por e-mail e senha.

##### III. Solicitação de empréstimo
- Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas.
- O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.

##### IV. Acompanhamento das solicitações de empréstimos
- O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
- Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
- No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

### 1. Requisitos do projeto

Para executar o projeto é necessária a instalação dos seguintes softwares:

- Java JDK 11
- Docker

### 2. Execução do projeto

No Terminal ou CMD para instalar o banco de dados PostgreSQL com as configurações necessárias, executar o seguinte comando:

``docker run -d --name dev-postgres -e POSTGRES_USER=tqi -e POSTGRES_PASSWORD=tqi -e POSTGRES_DB=analisecredito -p 5432:5432 postgres``

Próximo passo ir para pasta raiz do projeto, executar o seguinte comando no Terminal:

```shell
$ ./gradlew bootRun -DDB_USERNAME=tqi -DDB_PASSWORD=tqi
```

Caso esteja em uma máquina com SO Windows 10 ou 11 na pasta raiz do projeto, executar o seguinte comando no CMD:

```shell
gradlew.bat bootRun -DDB_USERNAME=tqi -DDB_PASSWORD=tqi
```

Aplicação é executada no endereço http://localhost:8080

Fazer o download da coleção abaixo de descompactar, importar no Postman para fazer testes dos endpoints da API:

[analise-credito-endpoints.postman_collection.json.zip](https://github.com/samreis/tqi_evolution_backend_2021/files/7832411/analise-credito-endpoints.postman_collection.json.zip)
