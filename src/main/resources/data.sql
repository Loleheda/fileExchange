INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

INSERT INTO statuses (name) VALUES ('AVAILABLE'), ('LOCKED'), ('SENT');

INSERT INTO users (login, password, role, email)
VALUES ('admin', '$2a$10$ClgFGVRNCH/U18/9GhlzS.OdiBQpUlpQuZbZEKu4g9UQv7LzzH9Vy', 1, 'admin@gmail.com'),
        ('user1', '$2a$10$P0I2ebiesZJnpS4.Vccei.fNEoRagCsT.uMm5s6m9WbyGPrQV.8Vm', 2, 'user1@gmail.com'),
        ('user2', '$2a$10$N74a2hZ2v6fDwR4xcF/Szec75nNX.mOGsuD2NDAcDG2IHTpWA1ijm', 2, 'user2@gmail.com')



