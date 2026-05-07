AT2 | CRUD de Estoque com Kotlin Multiplatform e Supabase
Data de conclusão 18 de maio de 2026 às 18:00
Instruções
Objetivo
Desenvolver um servidor REST utilizando Kotlin Multiplatform para gerenciar um sistema de controle de estoque, com persistência de dados no Supabase (PostgreSQL). A aplicação deverá expor endpoints para operações completas de CRUD sobre produtos e seus estoques.

Contexto
Uma empresa chamada Estocadão precisa de uma API para controlar seu estoque de produtos. Você ficará responsável por projetar e implementar o servidor backend, conectado ao banco de dados na nuvem via Supabase.

Diagrama do Banco de Dados
O diagrama acima representa a estrutura esperada. Você deverá criar as seguintes tabelas no Supabase:




Tabela products
id	uuid (PK)	Identificador único do produto
name	varchar	Nome do produto
description	text	Descrição do produto
sku	varchar	Código único do produto (Stock Keeping Unit)
category	varchar	Categoria do produto
created_at	timestamp	Data de criação
updated_at	timestamp	Data da última atualização
Tabela stock_items
id	uuid (PK)	Identificador único do item de estoque
product_id	uuid (FK → products.id)	Referência ao produto
quantity	integer	Quantidade disponível em estoque
unit_price	decimal	Preço unitário
location	varchar	Localização no armazém
updated_at	timestamp	Data da última atualização
A relação é: um product pode ter zero ou muitos stock_items.

Requisitos Funcionais
A API deverá implementar os seguintes endpoints:

Produtos — /products
GET	/products	Listar todos os produtos
GET	/products/{id}	Buscar um produto pelo ID
POST	/products	Cadastrar um novo produto
PUT	/products/{id}	Atualizar os dados de um produto
DELETE	/products/{id}	Remover um produto
Estoque — /stock
GET	/stock	Listar todos os itens de estoque
GET	/stock/{id}	Buscar um item de estoque pelo ID
POST	/stock	Adicionar um item ao estoque
PUT	/stock/{id}	Atualizar um item do estoque
DELETE	/stock/{id}	Remover um item do estoque
Endpoint Especial
GET	/stock/summary	Retornar a quantidade total de cada produto em estoque
[
  {
    "product_id": "uuid-do-produto",
    "product_name": "Caneta Azul",
    "total_quantity": 350
  },
  {
    "product_id": "uuid-do-produto-2",
    "product_name": "Caderno A4",
    "total_quantity": 120
  }
]
Requisitos Técnicos
A aplicação deve ser desenvolvida com Kotlin Multiplatform utilizando o framework Ktor para o servidor HTTP
O banco de dados deve ser hospedado no Supabase (PostgreSQL)
A comunicação com o banco deve ser feita via cliente HTTP do Supabase
Utilizar serialização com kotlinx.serialization para os modelos de dados
O projeto deve ser organizado com separação de responsabilidades: rotas, serviços e modelos em camadas distintas
As respostas devem ser em formato JSON
Tratar corretamente os códigos de status HTTP (200, 201, 204, 404, 400)
Critérios de Avaliação
Configuração correta do projeto KMP e Ktor	15%
Criação das tabelas no Supabase conforme o diagrama	10%
Implementação correta dos endpoints CRUD de produtos	25%
Implementação correta dos endpoints CRUD de estoque	25%
Implementação e corretude do endpoint /stock/summary	15%
Organização do código e boas práticas	10%
Entregáveis
Repositório no GitHub com o código-fonte do projeto
Arquivo README.md com instruções de como configurar e executar a aplicação localmente
Variáveis de ambiente necessárias documentadas (URL e chave do Supabase)
Print ou export do schema criado no Supabase
Observações
Não exponha a chave de API do Supabase no repositório; utilize variáveis de ambiente (.env ou similar)
O endpoint /stock/summary deve agregar os dados via query SQL (GROUP BY + SUM), não via código Kotlin
Atenção à integridade referencial: ao deletar um produto, verifique o comportamento dos stock_items vinculados