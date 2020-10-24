#Desafio Fluid - Analista de integrações
###Ambiente
* Java 1.8
* Maven 3.6+
* Docker

###Execução
* mvn clean package
* docker build -t integration-analyst-test .
* docker run --rm -it -v "$(pwd)"/data:/opt/data integration-analyst-test --input=input.csv --output=output.json