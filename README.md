# Desafio Fluid - Analista de integrações
> Desafio para ingressar como Analista de Integrações na Fluid

## Proposta da solução
A solução apresentada busca resolver de forma simplificada o mapeamento e contabilização de casos e mortes causadas pelo COVID-19 de acordo com cada mês. Esta solução simula o recebimento de um arquivo ```csv``` com um compilado de dados, e emite um arquivo ```json``` com a soma de casos e mortes causadas pela doença, separados pelos meses e anos, através do mapeamento dos meses, e subsequente soma dos referidos casos e mortes para cada um, através do uso das funções .map() e .reduce() do Javascript.

## Como rodar a aplicação
- Faça o clone do projeto em uma pasta de sua preferência
- Acesse a pasta ```node``` via command e execute o comando npm install para instalar dependências
- Execute o comando ```node integration.js```
- O arquivo de resultado será gerado na data ```data``` com o nome ```output-result.json```
- Para executar testes unitários, acesse a pasta nome e execute o comando ```npm test```

## Executar a aplicação em Docker
- Certifique que o Docker esteja instalado em sua máquina
- Acesse a pasta ```node``` via cmd
- Execute o comando ```docker build -t node-app .```
- Após a criação da imagem, execute o comando ```docker run -dp 3000:3000 node-app``` para rodar a aplicação.
- O arquivo de resultado estará disponível na pasta ```data```

## Funcionalidades
- Leitura de arquivo CSV
- Processamento de dados obtidos no CSV
- Criação de arquivo JSON

## Melhorias Futuras
[ ] Leitura dinâmica do caminho do arquivo CSV