# 🌩️ StormTrack

Sistema inteligente de monitoramento climático, desenvolvido em Java com Spring Boot, que coleta dados de sensores ambientais e emite alertas automáticos para a população em caso de situações críticas relacionadas à temperatura.

---

## 📖 Descrição

O **StormTrack** é um sistema de backend que coleta leituras de sensores instalados em locais estratégicos. Com base em dados como **temperatura** e **umidade**, o sistema é capaz de emitir alertas com diferentes níveis de criticidade. Usuários autenticados podem cadastrar sensores e acompanhar leituras e alertas gerados em tempo real.

---

## 🧱 Estrutura do Sistema

### 👤 Usuários (`User`)
- Autenticam-se por e-mail e senha.
- Possuem papéis: `ADMIN` ou `USER`.
- Associam-se a sensores.

### 📡 Sensores (`Sensors`)
- Representam dispositivos físicos.
- Armazenam informações como nome, localização e status de atividade.
- Estão associados a um usuário.

### 🌡️ Leituras dos Sensores (`SensorReading`)
- Armazenam medições de **temperatura** e **umidade**.
- Associadas a sensores e registram data/hora da leitura.

### 🚨 Alertas (`Alert`)
- Gerados automaticamente com base nas leituras dos sensores.
- Contêm uma **mensagem**, um nível de **classificação** e **data/hora** do alerta.
- Classificações possíveis:
  - `ALERTA_VERMELHO`
  - `CUIDADO`
  - `MODERADO`
  - `BOM_TEMPO`

---

## 🔐 Autenticação

- O sistema usa **JWT (JSON Web Token)** para autenticação.
- A autenticação ocorre via endpoint de login com email e senha.
- Usuários autenticados recebem um token com tempo de expiração, tipo e papel.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia         | Uso Principal                                      |
|--------------------|----------------------------------------------------|
| **Java 17**        | Linguagem base do projeto                         |
| **Spring Boot**    | Framework principal                               |
| **Spring Security**| Controle de autenticação com JWT                  |
| **Spring Data JPA**| Persistência de dados ORM                         |
| **Maven**          | Gerenciador de dependências e build               |
| **MySQL / Oracle** | Bancos de dados utilizados                        |
| **Swagger**        | Documentação automática da API                    |
| **Docker**         | Containerização da aplicação e banco de dados     |

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17
- Maven
- Docker

### 1. Clone o projeto

```bash
git clone https://github.com/FelipeSants08/stormtrack.git
cd stormtrack
```

### 2. Configure o banco de dados para rodar localmente

application.properties

spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL
spring.datasource.username=rm558916
spring.datasource.password=081105
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

3. Build e execução

```bash
mvn clean install
mvn spring-boot:run
```
http://localhost:8080/swagger-ui.html

4. Login JWT = POST= auth/login

{ "email": "felipe@email.com",
 "password": "1234" }

 E pegar o token na resposta!
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Poderão tambem testar no seguinte link, sem precisar baixar o projeto:

 http://48.217.50.130:8080/swagger-ui/index.html#/

Login - ADMIN
 {
  "email": "felipe.admin@fiap.com",
  "password": "0810"
}

