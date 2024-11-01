# payment-uol
desafio técnico UOL

## antes de executar a aplicação:
execute o docker compose presente no diretorio /uol, responsável por disponibilizar o localstack, no qual a aplicação criará as filas necessárias. 

## para chamar os endpoints
acesse: http://localhost:8080/swagger-ui/index.html#/

Este repositório contém uma aplicação de processamento de pagamentos desenvolvida como parte de um desafio técnico. A solução implementa funcionalidades 
para verificação e validação de pagamentos com uma abordagem simplificada de Domain-Driven Design (DDD), garantindo uma estrutura modular e organizada.

### Funcionalidades
A aplicação recebe uma requisição de pagamento contendo:

Código do vendedor
Lista de pagamentos realizados
E realiza as seguintes operações:

- Valida a existência do vendedor e do código de cobrança.
- Identifica o tipo de pagamento (parcial, total ou excedente).
- Envia o objeto de pagamento para a fila SQS correspondente.
- Retorna o objeto com o status de pagamento atualizado.

### Requisitos Técnicos
- Spring Boot: Framework principal para desenvolvimento.
- H2 Database: Banco de dados em memória para simular persistência.
- Amazon SQS: Integração com filas para processamento de mensagens.
- JUnit e Mockito: Testes unitários e mocking.
- Swagger: Documentação da API e fácil acesso aos recursos

### Estrutura de Design e Arquitetura
Este projeto foi desenvolvido com uma arquitetura de camadas inspirada em DDD, mantendo uma clara separação de responsabilidades.

1. Camadas da Arquitetura
- Application Layer: Responsável por orquestrar as chamadas entre o domínio e a infraestrutura. Utiliza um Handler (ou Facade) para consolidar a lógica de processamento de pagamento em um único ponto de entrada.
- Domain Layer: Contém as entidades e regras de negócios, incluindo a validação de tipos de pagamento (parcial, total, ou excedente).
- Infrastructure Layer: Cuida da integração com tecnologias externas, como persistência no banco de dados H2 e filas SQS.

2. Facade/Handler de Processamento de Pagamento
- Foi utilizado um Facade/Handler para consolidar as operações, centralizando o fluxo de chamada:

- Validação de Entrada: Confere a validade do código do vendedor e dos pagamentos.
- Identificação do Tipo de Pagamento: Identifica se o pagamento é parcial, total ou excedente, utilizando lógica de domínio.
- Encaminhamento para Filas SQS: Dependendo do tipo de pagamento, envia o objeto para uma fila SQS específica.
- Resposta e Status do Pagamento: Atualiza e retorna o objeto com o status de pagamento atualizado.

3. Strategy Pattern para Processamento de Pagamentos
- Dentro do Handler, utilizamos um Strategy Pattern para definir estratégias de processamento para cada tipo de pagamento. Cada tipo possui sua lógica própria, permitindo a adição de novos tipos com facilidade.

4. Repository Pattern
- O acesso ao banco de dados H2 é abstraído pelo Repository Pattern, facilitando a manutenção e futura substituição do banco de dados.

5. Testes e Mocking
Para os testes, utilizamos JUnit e Mockito:
- Testes unitários para validar a lógica de pagamento e o envio para SQS.
- Mocking para simular dependências e garantir que o comportamento esperado ocorra.

6. Tratamento de Erros e Status Code
- Erros são tratados no Handler, que retorna status HTTP apropriados, garantindo que o cliente entenda a natureza do erro.
