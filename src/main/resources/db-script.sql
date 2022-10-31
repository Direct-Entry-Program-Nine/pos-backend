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