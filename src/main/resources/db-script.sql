CREATE TABLE customer(
                         id VARCHAR(36) PRIMARY KEY ,
                         name VARCHAR(100) NOT NULL ,
                         address VARCHAR(350) NOT NULL
);

INSERT INTO customer(id, name, address) VALUES (UUID(), 'Dasun', 'Moratuwa'),
                                               (UUID(), 'Kushan', 'Moratuwa'),
                                               (UUID(), 'Chathura', 'Moratuwa'),
                                               (UUID(), 'Tharindu', 'Moratuwa'),
                                               (UUID(), 'Anushka', 'Moratuwa');

CREATE TABLE item(
                     code VARCHAR(36) PRIMARY KEY ,
                     description VARCHAR(350) NOT NULL ,
                     unit_price DECIMAL(5,2) NOT NULL,
                     stock INT NOT NULL
);

INSERT INTO item (code, description, unit_price,stock) VALUES (UUID(), 'Milk Powder', 700,100);
INSERT INTO item (code, description, unit_price,stock) VALUES (UUID(), 'Egg', 50,200);