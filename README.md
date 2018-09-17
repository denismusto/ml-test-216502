# ml-test-216502

Como utilizar a API referente a MeLi Test

### URL Endpoint principal

https://ml-api-216502.appspot.com/mutant

### Detalhes Hospedagem

Hospedado no Google Cloud utilizando o framework Open-API para endpoints e app engine como backend.
Ambiente Flex do GCP, executando com Docker containers sobre VMs.

### Testando a aplicação

1. Faça uma chamada POST para https://ml-api-216502.appspot.com/mutant, informando no body em formato JSON a seguinte informação:
  {"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]} ou {"dna":["ATGCGA","CGGTGC","TTATGT","AGAATG","TCCCTA","TCACTG"]}
