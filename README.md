# API em java

<h2>Tecnologias:</h2>
As tecnologias usada no porjeto sao :
Sprintg-boot
Spring-mvc
Spring-data
JPA
h2 database
thymeleaf
junit

Para executar o projeto, será necessário instalar os seguintes programas:

JDK 11: Necessário para executar o projeto Java
Maven 3.5.3: Necessário para realizar o build e as  importações das bibliotecas do projeto Java
Intellij: Para desenvolvimento do projeto

Construção
Para construir o projeto com o Maven, executar os comando abaixo:

```shell script
mvn clean install
```
O comando irá baixar todas as dependências do projeto e criar um diretório target com os
artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados
os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.

<h2>Subir servidor local </h2>

```shell script
mvn spring-boot:run
```

O comando ira levantar um servidor H2 um banco de dados relacional escrito em Java
a aplicação ira rodar na url: 

```shell script
 http://localhost:8080/
```



<h2>Testes </h2>
Para rodar os testes, utilize o comando abaixo:
mvn test


 <h3>Licença</h3>
Não se aplica.

<h3>Conclusão</h3>

Crie um sistema que permita o cadastro de anúncios. O anúncio contem os seguintes dados:
nome do anúncio
cliente
data de início
data de término
investimento por dia

O sistema fornecerá os relatórios de cada anúncio contendo:
valor total investido
quantidade máxima de visualizações
quantidade máxima de cliques
quantidade máxima de compartilhamentos
Os relatórios poderão ser filtrados por intervalo de tempo e cliente.
