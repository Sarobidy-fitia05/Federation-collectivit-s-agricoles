INSERT INTO financial_account VALUES
                                  ('ACC001', 100000, 'COL001', 'CASH'),
                                  ('ACC002', 500000, 'COL001', 'BANK');

INSERT INTO collectivity_transaction VALUES
                                         ('T1', 'ACC001', 10000, '2026-04-01'),
                                         ('T2', 'ACC001', -2000, '2026-04-02'),
                                         ('T3', 'ACC002', 50000, '2026-04-03');