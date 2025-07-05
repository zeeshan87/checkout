-- Pre load the offers and items tables with initial data

INSERT INTO offer (id, offer_type, quantity, special_price)
VALUES
    (1, 'GROUP_PRICING', 2, 45.00),
    (2, 'GROUP_PRICING', 3, 130.00);

SELECT setval('offer_id_seq', 3);

INSERT INTO item (id, name, unit_price, offer_id)
VALUES
    (1, 'Apple',  30.00, 1),
    (2, 'Banana', 50.00, 2),
    (3, 'Peach',  60.00, NULL),
    (4, 'Kiwi',   20.00, NULL);

SELECT setval('item_id_seq', 5);
