# Desafio técnico - Plathanus

<h3>

[//]: # (<img src="https://img.shields.io/badge/React-61DAFB.svg?style=for-the-badge&logo=React&logoColor=black"/>)
<img src="https://img.shields.io/badge/Java-C71A00?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
<img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=Flyway&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white"/>

</h3>

Backend em Java 17 com spring-boot 3.2.3</br>
Base de dados PostgreSQL 15 </br>
Versionamento de DB com Flyway 11 </br>
Serviços containerizados com Docker e orquestrados com docker-compose
</br>

## Executando em ambiente local com Docker 🐋
A partir da pasta raiz do projeto, onde se encontra o arquivo docker-compose.yml, execute o comando para iniciar o container:
> docker-compose up --build
>

</br>

🚧 trabalhando no frontend...

## REQUISITOS / FUNCIONALIDADES IMPLEMENTADAS 
### Autenticação
 Endpoint /auth/login que autentica com username + password </br>
 Geração de token JWT com assinatura HMAC-SHA256 </br>
 Token carrega com o username e roles com as permissões </br>
 Expiração do token configurável via jwt.expirationMs

### Validação de Token
 Endpoint /auth/validate que recebe token via Authorization </br>
 Rejeita tokens expirados ou malformados </br>
 Camada de segurança protegendo todas as rotas (exceto /auth/ login e /auth/validate)

### Segurança dos Dados
 Uso de BCryptPasswordEncoder para criptografar senhas </br>
 Campos cpf e name criptografados com AES GCM (@Convert) </br>
 AES Key centralizada via @Configuration e injetada corretamente no Converter </br>
 JWT Secret e AES Secret extraídos via application.properties com segurança

### Base de dados
 Docker + PostgreSQL com container postgres-auth </br>
 Configuração via .env root centralizado </br>
 Migrations com Flyway </br>
 Inserção de registros via scripts sql com senhas e campos criptografados

### Testes
 Testes unitários para geração, validade e rejeição de tokens (JwtUtilTest) </br>
 Testes de arqitetura