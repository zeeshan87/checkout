CREATE TABLE offer (
    id BIGSERIAL PRIMARY KEY,
    offer_type VARCHAR(50) NOT NULL,
    quantity INT,
    special_price NUMERIC,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit_price NUMERIC NOT NULL CHECK (unit_price > 0),
    offer_id BIGINT UNIQUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_offer FOREIGN KEY (offer_id) REFERENCES offer (id)
);

CREATE TABLE cart (
    id BIGSERIAL PRIMARY KEY,
    total_price NUMERIC NOT NULL DEFAULT 0 CHECK (total_price >= 0),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE cart_item (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    CONSTRAINT fk_item FOREIGN KEY (item_id) REFERENCES item (id),

    CONSTRAINT unique_cart_item UNIQUE (cart_id, item_id)
);