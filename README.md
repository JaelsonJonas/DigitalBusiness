# IrisCare API
Olá! Este projeto tem como objetivo trazer uma API que gerencia os dados de um aplicativo que tem como o objetivo análises para diagnóstico de retinoblastoma.
O projeto foi desenvolvido utilizando JDK 17, Spring Framework, JPA, banco de dados relacional Oracle, Spring Security, OAuth2.

## Estrutura do Projeto

Este projeto foi estruturado realizando a separação em 7 packages , sendo elas:

### Controllers

Esta package tem por objetivo armazenar o controlador das requisições HTTP realizadas pelo usuário. Fazendo uma ligação direta com a camada de serviços, o controlador será responsável também por fornecer respostas ao usuário de acordo com as requisições e as regras de negócio implementadas na camada de serviço.

### Services

Esta package tem por objetivo armazenar a camada de serviço da aplicação. Nesta camada, todas as regras de negócio serão implementadas para que todas as requisições feitas pelo usuário possam ser respondidas de acordo com as regras impostas na ideação do projeto. Nesta camada os dados enviados são validados, tratados e a intermediação entre o controlador e o repositório é feita para que a requisição seja respondida.

### Repositories

Esta package tem por objetivo armazenar a camada de repositório da aplicação. Esta camada irá implementar uma interface que acessará e manipulará os dados do banco de dados da aplicação. Estando em contato direto com o banco de dados, esta interface
tornará o acesso mais simples e rápido.

### Entities

Esta package tem por objetivo armazenar a camada de entidades da aplicação. Esta camada será responsável por definir as estruturas de dados da aplicação e do banco de dados, e terá contato direto com a camada de repositório.

### DTO

Esta package tem por objetivo armazenar a camada de Data Transfer Objects da aplicação. A camada de DTO é responsável por definir as estruturas de dados usadas para transferir informações entre as diferentes camadas da aplicação ou entre a aplicação e outros sistemas externos. DTOs são projetados para serem simples e leves, contendo apenas os dados necessários para a transferência de informações. A camada de DTO ajuda a separar a lógica de negócios da lógica de transferência de dados e a melhorar o desempenho e a escalabilidade da aplicação.

### Exceptions

Esta package tem por objetivo armazenar as classes de implementação das exceções próprias do projeto. Estas exceções contextualizarão os possíveis erros de uma forma melhor e aprimorarão a experiência do usuário.

## Utils

Esta camada consiste em uma camada composta por classes que abrigam métodos que podem ser utilizados em diversos campos da aplicação. É uma camada de classes com métodos reutilizáveis e de funções diversas, como conversores e métodos que seriam repetitivos em certas partes do código.

## Instruções para Execução do Projeto

Para executar a aplicação, pode ser usado o botão de Run de cada IDE. Mas caso seja necessário outro método, deve-se localizar a classe "IriscareapiApplication", seleciona-lá com o botão direito do mouse e selecionar a opção "Run IriscareapiApplication".

## Configuração

Toda configuração de conexão e execução do banco de dados esta disponível no arquivo application.properties localizado na pasta "resources" do projeto. A aplicação será executado na porta "http://localhost:8080" e o acesso as tabelas será feito pelo programa SQL Developer com os dados disponibilizados no application.properties.

### Baixando arquivo .zip

Faça o download do arquivo zip e descompacte na workspace da IDE. Então importe e abra o arquivo descompactado localizado na workspace da IDE.

### Clonando via Github

Requisitos:
Git : https://git-scm.com
Permissão para acesso ao repositório da organização.
Na pasta onde se deseja salvar o clone do projeto, clique com o botão direito na área da pasta e selecione a opção "Git Bash Here". Quando o terminal for aberto copie, cole e execute o seguinte comando :

git clone [https://github.com/marcosbilobram/high-risk-cpf-api.git](https://github.com/IrisCareSoluctions/DigitalBusiness.git)

# Detalhamento dos endpoints

Para um detalhamento completo dos endpoints feito pelo swagger, após a execução da aplicação, acesse a interface do swagger da aplicação pelo link : http://localhost:8080/swagger-ui/index.html#/.
