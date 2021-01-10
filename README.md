# Desafio Fluid - Analista de integrações
> Desafio para ingressar como Analista de Integrações na Fluid

## Testando a aplicação

Para Testar a aplicação foi criado um script bash que executa o método http `POST` no ambiente de teste implantado no heroku e apresenta o resultado no console.
Exemplo de utilização do script:

```bash
$ bash test.sh
```

> Url da documentação com openapi http://covid-fluidapi-spring.herokuapp.com/swagger-ui.html

## Sobre o desafio
Este desafio simula uma integracao de dois sistemas, um deles fornecendo uma estrutura de dados no formato ```.csv``` com informações diárias sobre o Covid19 de 30/01/2020 à 30/03/2020, e a saída com informações acumuladas mensalmente, esperando os dados em uma estrutura no formato ```.json```. Para isso precisamos realizar o tratamento desses dados, verificando o tipo, e realizando o de/para para a estrutura de saída.

## Ações
- Teremos duas estruturas de dados.
- A estrutura em ```.csv``` será a estrutura de entrada na aplicação.
- A estrutura em ```.json``` o exemplo da estrutura de saida.
- Fornecer um ambiente de teste (Docker, Heroku, etc).

## Pré-requisitos
- Testes unitários.
- Pode utilizar a linguagem de programação de sua preferencia.

## Dicas
- [JsonLogic](http://jsonlogic.com/).

## Diferenciais
- Código bem escrito;
- Testes unitários bem feitos;
- Uso de Design Patterns;
- SOLID;
- Fornecer um ambiente de teste (Docker, Heroku, etc).

## Pronto para começar o desafio?
- Faça um "fork" desse repositório na sua conta do Github
- Crie uma branch com o seu nome e sobrenome ex: ```fulano-de-tal```
- Após completar o desafio, crie um "pull request" nesse repositório comparando a sua branch com a master
- Receberemos uma notificação do seu pull request, faremos a correção da sua solução e entraremos em contato com o email da conta do github em que foi executada o desafio

## FAQ
- Aonde estão os assets?

**Os assets estão neste mesmo reposotório na pasta ```data/```**

- Tenho mais dúvidas, com quem posso entrar em contato?

**Entre em contato com Lucas França (lucas.franca@fluidapi.io)**
