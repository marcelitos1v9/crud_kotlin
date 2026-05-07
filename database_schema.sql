-- Criação da tabela de Produtos
CREATE TABLE IF NOT EXISTS public.products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    description TEXT,
    sku VARCHAR UNIQUE NOT NULL,
    category VARCHAR,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);

-- Criação da tabela de Estoque com Integridade Referencial (ON DELETE CASCADE)
CREATE TABLE IF NOT EXISTS public.stock_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES public.products(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL DEFAULT 0,
    unit_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    location VARCHAR,
    updated_at TIMESTAMP DEFAULT now()
);

-- Criação da View para o endpoint de Sumário (/stock/summary)
CREATE OR REPLACE VIEW public.stock_summary AS
SELECT 
    p.id AS product_id,
    p.name AS product_name,
    COALESCE(SUM(s.quantity), 0) AS total_quantity
FROM public.products p
LEFT JOIN public.stock_items s ON p.id = s.product_id
GROUP BY p.id, p.name;
