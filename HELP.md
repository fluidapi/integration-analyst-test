#Desafio Fluid - Analista de integrações
## Marcelo Pedrazzani
###Ambiente

* Java 1.8
* Apache Maven 3.6.3
* Docker 19.03.8

###Execução
Na pasta raiz do projeto executar os seguintes comandos:
````
mvn clean package
docker build -t integration-analyst-test .
docker run --rm -it -v "$(pwd)"/data:/opt/data integration-analyst-test --input=input.csv --output=output.json