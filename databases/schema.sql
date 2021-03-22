DROP TABLE IF EXISTS card_transactions;
CREATE TABLE card_transactions
(
    id                CHAR(36) PRIMARY KEY,
    source            VARCHAR(19) NOT NULL,
    target            VARCHAR(19) NOT NULL,
    amount_in_kopecks INT         NOT NULL,
    created           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS cards;
CREATE TABLE cards
(
    id                 CHAR(36) PRIMARY KEY,
    user_id            CHAR(36)           NOT NULL,
    number             VARCHAR(19) UNIQUE NOT NULL,
    balance_in_kopecks INT                NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS auth_codes;
CREATE TABLE auth_codes
(
    id      CHAR(36) PRIMARY KEY,
    user_id CHAR(36)   NOT NULL,
    code    VARCHAR(6) NOT NULL,
    created TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS card_transactions;
CREATE TABLE card_transactions
(
    id                CHAR(36) PRIMARY KEY,
    source            VARCHAR(19) NOT NULL,
    target            VARCHAR(19) NOT NULL,
    amount_in_kopecks INT         NOT NULL,
    created           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);