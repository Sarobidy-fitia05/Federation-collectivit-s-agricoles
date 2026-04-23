-- Supprimer les tables existantes (attention : cela supprime les données)
DROP TABLE IF EXISTS membership_fee CASCADE;
DROP TABLE IF EXISTS collectivity_structure CASCADE;
DROP TABLE IF EXISTS collectivity_member CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS collectivity CASCADE;

-- Recréer les tables avec VARCHAR(36) pour les IDs
CREATE TABLE collectivity (
                              id VARCHAR(36) PRIMARY KEY,
                              location VARCHAR(255) NOT NULL,
                              number VARCHAR(50),
                              name VARCHAR(255),
                              federation_approval BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE member (
                        id VARCHAR(36) PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        birth_date DATE,
                        gender VARCHAR(20),
                        address VARCHAR(255),
                        profession VARCHAR(100),
                        phone_number INTEGER,
                        email VARCHAR(100),
                        occupation VARCHAR(50),
                        membership_date DATE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE collectivity_member (
                                     collectivity_id VARCHAR(36) NOT NULL,
                                     member_id VARCHAR(36) NOT NULL,
                                     PRIMARY KEY (collectivity_id, member_id),
                                     FOREIGN KEY (collectivity_id) REFERENCES collectivity(id),
                                     FOREIGN KEY (member_id) REFERENCES member(id)
);

CREATE TABLE collectivity_structure (
                                        collectivity_id VARCHAR(36) PRIMARY KEY,
                                        president_id VARCHAR(36),
                                        vice_president_id VARCHAR(36),
                                        treasurer_id VARCHAR(36),
                                        secretary_id VARCHAR(36),
                                        FOREIGN KEY (collectivity_id) REFERENCES collectivity(id),
                                        FOREIGN KEY (president_id) REFERENCES member(id),
                                        FOREIGN KEY (vice_president_id) REFERENCES member(id),
                                        FOREIGN KEY (treasurer_id) REFERENCES member(id),
                                        FOREIGN KEY (secretary_id) REFERENCES member(id)
);

CREATE TABLE membership_fee (
                                id VARCHAR(36) PRIMARY KEY,
                                collectivity_id VARCHAR(36) NOT NULL,
                                eligible_from DATE NOT NULL,
                                frequency VARCHAR(50) NOT NULL,
                                amount NUMERIC(10, 2) NOT NULL,
                                label VARCHAR(255) NOT NULL,
                                status VARCHAR(20) DEFAULT 'ACTIVE',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (collectivity_id) REFERENCES collectivity(id),
                                UNIQUE(collectivity_id, label)
);

-- Créer les index
CREATE INDEX idx_membership_fee_collectivity ON membership_fee(collectivity_id);
CREATE INDEX idx_collectivity_member_member ON collectivity_member(member_id);

-- Insérer les données de test
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, membership_date)
VALUES
    ('member-001', 'Jean', 'Dupont', '1980-05-15', 'MALE', '123 Rue de la Paix', 'Agriculteur', 261234567, 'jean.dupont@email.com', 'PRESIDENT', '2026-01-01'),
    ('member-002', 'Marie', 'Martin', '1985-08-20', 'FEMALE', '456 Avenue des Champs', 'Eleveuse', 262345678, 'marie.martin@email.com', 'VICE_PRESIDENT', '2026-01-05'),
    ('member-003', 'Pierre', 'Bernard', '1975-03-10', 'MALE', '789 Boulevard Central', 'Cultivateur', 263456789, 'pierre.bernard@email.com', 'TREASURER', '2026-01-10'),
    ('member-004', 'Sophie', 'Leclerc', '1990-11-25', 'FEMALE', '321 Rue du Commerce', 'Fermiere', 264567890, 'sophie.leclerc@email.com', 'SECRETARY', '2026-01-15');

INSERT INTO collectivity (id, location, federation_approval)
VALUES ('collectivity-001', 'Region Amoron Mania', TRUE);

INSERT INTO collectivity_member (collectivity_id, member_id)
VALUES
    ('collectivity-001', 'member-001'),
    ('collectivity-001', 'member-002'),
    ('collectivity-001', 'member-003'),
    ('collectivity-001', 'member-004');

INSERT INTO collectivity_structure (collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id)
VALUES ('collectivity-001', 'member-001', 'member-002', 'member-003', 'member-004');

INSERT INTO membership_fee (id, collectivity_id, eligible_from, frequency, amount, label, status)
VALUES
    ('fee-001', 'collectivity-001', '2026-01-01', 'MONTHLY', 15000.00, 'Cotisation mensuelle', 'ACTIVE'),
    ('fee-002', 'collectivity-001', '2026-01-01', 'ANNUALLY', 150000.00, 'Cotisation annuelle', 'ACTIVE');

-- Vérifier les données
SELECT * FROM collectivity;
SELECT * FROM member;
SELECT * FROM membership_fee;