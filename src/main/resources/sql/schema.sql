-- membership_fee
CREATE TABLE membership_fee (
                                id VARCHAR(255) PRIMARY KEY,
                                collectivity_id UUID NOT NULL REFERENCES collectivity(id),
                                eligible_from DATE NOT NULL,
                                frequency VARCHAR(20) NOT NULL,
                                amount NUMERIC(10,2) NOT NULL,
                                label VARCHAR(255) NOT NULL,
                                status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'
);
-- financial_account
CREATE TABLE IF NOT EXISTS financial_account (
                                                 id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    account_number VARCHAR(100),
    bank_name VARCHAR(255),
    collectivity_id VARCHAR(255)
    );

--member_payment
CREATE TABLE IF NOT EXISTS member_payment (
                                              id VARCHAR(255) PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL,
    membership_fee_id VARCHAR(255),
    financial_account_id VARCHAR(255),
    payment_mode VARCHAR(50),
    creation_date TIMESTAMP NOT NULL,
    member_id VARCHAR(255),
    FOREIGN KEY (membership_fee_id) REFERENCES membership_fee(id),
    FOREIGN KEY (financial_account_id) REFERENCES financial_account(id)
    );

CREATE TABLE collectivity_transaction (
                                          id VARCHAR(255) PRIMARY KEY,
                                          creation_date DATE NOT NULL,
                                          amount DECIMAL(19,2) NOT NULL,
                                          payment_mode VARCHAR(50) NOT NULL,
                                          account_credited_id VARCHAR(255) NOT NULL,
                                          member_debited_id VARCHAR(255) NOT NULL,
                                          collectivity_id VARCHAR(255) NOT NULL,

                                          CONSTRAINT fk_account_credited
                                              FOREIGN KEY (account_credited_id)
                                                  REFERENCES financial_account(id),

                                          CONSTRAINT fk_member_debited
                                              FOREIGN KEY (member_debited_id)
                                                  REFERENCES member(id),

                                          CONSTRAINT fk_collectivity
                                              FOREIGN KEY (collectivity_id)
                                                  REFERENCES collectivity(id)
);