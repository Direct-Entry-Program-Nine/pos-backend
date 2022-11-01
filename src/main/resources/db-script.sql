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

CREATE TABLE `order`(
                        id VARCHAR(36) PRIMARY KEY ,
                        date DATE NOT NULL,
                        customer_id VARCHAR(36) NOT NULL ,
                        FOREIGN KEY (customer_id) REFERENCES customer(id)
);

INSERT INTO `order` (id, date, customer_id) VALUES (UUID(), DATE, 'ce8d9029-59e9-11ed-a3d6-709cd1433e10');

CREATE TABLE orderDetail(
                            order_id VARCHAR(36) NOT NULL ,
                            item_code VARCHAR(36)  NOT NULL ,
                            CONSTRAINT PRIMARY KEY (order_id, item_code),
                            CONSTRAINT FOREIGN KEY (item_code) REFERENCES item(code),
                            CONSTRAINT FOREIGN KEY (order_id) REFERENCES `order`(id),
                            unit_price DECIMAL(5,2) NOT NULL ,
                            quantity INT NOT NULL
);
