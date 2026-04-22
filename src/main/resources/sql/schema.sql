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