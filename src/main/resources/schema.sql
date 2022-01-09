CREATE TABLE password
(
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_password PRIMARY KEY (password)
);

CREATE TABLE profile
(
    player_id   VARCHAR(255) NOT NULL,
    balance     DOUBLE       NULL,
    promo_count INT          NULL,
    CONSTRAINT pk_profile PRIMARY KEY (player_id)
);


CREATE TABLE transaction
(
    transaction_id VARCHAR(255) NOT NULL,
    player_id      VARCHAR(255) NULL,
    wager_amount   DOUBLE       NULL,
    win_amount     DOUBLE       NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (transaction_id)
);