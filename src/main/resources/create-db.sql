
CREATE TABLE IMPORT_TRACKING (
    import_id bigint not null AUTO_INCREMENT,
    file_name varchar(50),
    import_count int,
    import_status varchar(50),
    import_date timestamp,
    PRIMARY KEY (import_id)
);

CREATE TABLE GAME_SALES (
    game_sales_id bigint not null AUTO_INCREMENT,
    import_id bigint not null,
    id int,
    game_no int,
    game_name varchar(255),
    game_code varchar(255),
    type tinyint,
    cost_price DECIMAL(10,2),
    tax DECIMAL(10,2),
    sale_price DECIMAL(10,2),
    date_of_sale date,
    created_date date,
    PRIMARY KEY (game_sales_id),
    FOREIGN KEY (import_id) REFERENCES IMPORT_TRACKING(import_id)
);

CREATE INDEX IDX_GAME_SALES_DOS ON GAME_SALES (date_of_sale);

CREATE INDEX IDX_GAME_SALES_DOS_GN ON GAME_SALES (date_of_sale, game_no);

CREATE INDEX IDX_GAME_SALES_SP ON GAME_SALES (sale_price);
