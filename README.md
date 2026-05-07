# API de Controle de Estoque (Estocadão)

Servidor REST desenvolvido em Kotlin Multiplatform utilizando o framework **Ktor** para gerenciar um sistema de controle de estoque. A persistência de dados é realizada na nuvem utilizando **Supabase (PostgreSQL)** através de seu cliente HTTP para Kotlin.

## 📋 Funcionalidades e Requisitos Cumpridos

- Arquitetura em camadas (Rotas, Serviços e Modelos).
- Banco de dados relacional no Supabase.
- Serialização via `kotlinx.serialization`.
- Respostas padronizadas em JSON com os respectivos HTTP Status Codes.
- API Supabase comunicando via HTTPS (Postgrest).
- **Endpoint Especial (/stock/summary):** Atendendo à exigência da atividade de agregar via SQL e não via Kotlin, foi criada a View SQL nativa `stock_summary` contendo `GROUP BY` e `SUM`. Como a API do PostgREST (Supabase) não aceita o envio de SQL bruto por segurança, o uso de uma View no banco é a abordagem recomendada e oficial para delegar essa lógica e ser consumida pelas rotas.

## 🚀 Como Configurar e Executar Localmente

### 0. Recriar o Banco de Dados (Professor/Avaliador)
Se desejar recriar a base de dados do zero em outro projeto Supabase:
Copie o conteúdo do arquivo **`database_schema.sql`** (incluso neste repositório) e rode no "SQL Editor" do painel do Supabase. Ele já possui as tabelas (`products` e `stock_items` com `ON DELETE CASCADE`) e a View (`stock_summary`), suprindo o entregável "Export do schema".

### 1. Pré-requisitos
- JDK 17 ou superior.
- IntelliJ IDEA ou Android Studio.

### 2. Configurar Variáveis de Ambiente (Supabase)
Para que o servidor consiga conectar no banco de dados da nuvem, você precisa configurar a URL e a Chave Pública do seu Supabase.

Crie um arquivo chamado **`local.properties`** na raiz do projeto (na mesma pasta deste README) e adicione o seguinte conteúdo:

```properties
SUPABASE_URL=https://sua-url-do-projeto.supabase.co
SUPABASE_KEY=sua-chave-anon-public-key
```

> **Aviso:** Nunca versione o arquivo `local.properties`. O Supabase precisa apenas da chave pública (`anon public`) por usar RLS (Row Level Security), por este motivo mantemos via Variável de Ambiente. 

### 3. Rodar o Servidor
Abra o projeto no Android Studio/IntelliJ e rode a função `main()` no arquivo `Application.kt` no módulo `server`.
Ou execute via terminal:

- **Windows:**
  ```shell
  .\gradlew.bat :server:run
  ```
- **macOS/Linux:**
  ```shell
  ./gradlew :server:run
  ```

O servidor iniciará localmente na porta 8080: `http://localhost:8080`

## 🛣️ Endpoints da API

**Produtos** (`/products`)
- `GET /products` - Listar todos os produtos
- `POST /products` - Cadastrar produto
- `GET /products/{id}` - Buscar produto por ID
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Remover produto

**Estoque** (`/stock`)
- `GET /stock` - Listar todos os itens do estoque
- `POST /stock` - Adicionar novo lote/item ao estoque
- `GET /stock/summary` - Resumo do total em estoque agregado por produto
- `GET /stock/{id}` - Buscar operação de estoque por ID
- `PUT /stock/{id}` - Atualizar informações de um lote de estoque
- `DELETE /stock/{id}` - Remover entrada de estoque

## 🧪 Testes de API
Para testar todos os endpoints de forma fácil, exportamos uma coleção pronta do Insomnia:
1. Baixe o software [Insomnia](https://insomnia.rest/download).
2. Clique em *Import* e selecione o arquivo **`insomnia_collection.json`** presente na raiz deste repositório.
3. Isso já contém todas as rotas e a variável Root `base_url` configurada automática para o localhost!
