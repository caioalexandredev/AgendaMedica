INSERT INTO paciente (id, nome, telefone) VALUES ( 1, 'João Silva', '(21) 9 8765-4321');
INSERT INTO paciente (id, nome, telefone) VALUES ( 2, 'Maria Oliveira', '(31) 9 1234-5678');
INSERT INTO paciente (id, nome, telefone) VALUES ( 3, 'Carlos Santos', '(41) 9 9876-1234');
INSERT INTO paciente (id, nome, telefone) VALUES ( 4, 'Ana Souza', '(51) 9 2345-6789');
INSERT INTO paciente (id, nome, telefone) VALUES (10, 'Patrícia Gomes', '(71) 9 1234-9876');
INSERT INTO paciente (id, nome, telefone) VALUES (11, 'Lucas Martins', '(81) 9 9876-5432');
INSERT INTO paciente (id, nome, telefone) VALUES (12, 'Amanda Ribeiro', '(91) 9 2345-1234');
INSERT INTO paciente (id, nome, telefone) VALUES (13, 'Gustavo Fernandes', '(11) 9 8765-6789');
INSERT INTO paciente (id, nome, telefone) VALUES (14, 'Larissa Dias', '(21) 9 1234-4321');
INSERT INTO paciente (id, nome, telefone) VALUES (15, 'Daniel Carvalho', '(31) 9 9876-8765');
INSERT INTO paciente (id, nome, telefone) VALUES (16, 'Mariana Ferreira', '(41) 9 2345-5678');
INSERT INTO paciente (id, nome, telefone) VALUES (17, 'Rodrigo Barbosa', '(51) 9 8765-1234');
INSERT INTO paciente (id, nome, telefone) VALUES (18, 'Isabela Castro', '(61) 9 1234-6789');
INSERT INTO paciente (id, nome, telefone) VALUES (19, 'Thiago Rodrigues', '(71) 9 9876-5432');
INSERT INTO paciente (id, nome, telefone) VALUES (20, 'Bianca Mendes', '(81) 9 2345-9876');
INSERT INTO paciente (id, nome, telefone) VALUES (21, 'Felipe Santos', '(91) 9 8765-2345');
INSERT INTO paciente (id, nome, telefone) VALUES (22, 'Laura Alves', '(11) 9 1234-8765');
INSERT INTO paciente (id, nome, telefone) VALUES (23, 'Marcelo Soares', '(21) 9 9876-3456');
INSERT INTO paciente (id, nome, telefone) VALUES (24, 'Carolina Oliveira', '(31) 9 2345-7890');

INSERT INTO medico (id, nome, crm) VALUES ( 5, 'Dr. João Andrade', 'CRM-TO 1234');
INSERT INTO medico (id, nome, crm) VALUES ( 6, 'Dra. Maria Souza', 'CRM-TO 5678');
INSERT INTO medico (id, nome, crm) VALUES ( 7, 'Dr. Carlos Silva', 'CRM-TO 9101');
INSERT INTO medico (id, nome, crm) VALUES ( 8, 'Dra. Ana Lima', 'CRM-TO 1121');
INSERT INTO medico (id, nome, crm) VALUES ( 9, 'Dr. Roberto Nunes', 'CRM-TO 3141');

INSERT INTO consulta (id, data, valor, observacao, id_paciente, id_medico) VALUES ( 10, '2025-05-25T09:30:00', 200.00, 'Consulta de rotina', 1, 5);
INSERT INTO consulta (id, data, valor, observacao, id_paciente, id_medico) VALUES ( 11, '2025-05-26T14:00:00', 250.00, 'Revisão após cirurgia', 2, 6);
INSERT INTO consulta (id, data, valor, observacao, id_paciente, id_medico) VALUES ( 12, '2025-05-27T08:15:00', 180.00, 'Dor de cabeça persistente', 3, 7);
INSERT INTO consulta (id, data, valor, observacao, id_paciente, id_medico) VALUES ( 13, '2025-05-28T10:45:00', 220.00, 'Exame de rotina', 1, 8);
INSERT INTO consulta (id, data, valor, observacao, id_paciente, id_medico) VALUES ( 14, '2025-05-29T13:30:00', 210.00, 'Acompanhamento de tratamento', 2, 9);

INSERT INTO role (id, nome) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, nome) VALUES (2, 'ROLE_USER');
INSERT INTO role (id, nome) VALUES (3, 'ROLE_MEDICO');
INSERT INTO role (id, nome) VALUES (4, 'ROLE_SECRETARIO');
INSERT INTO role (id, nome) VALUES (5, 'ROLE_PACIENTE');

INSERT INTO usuario (id, login, password, id_pessoa) VALUES (1, 'administrador', '$2a$10$jFW7LJBG3BATcGjxn1JWCu3G/jpVRfqgzyEXSx3I8F9IqbAZNp2Mi', 1);
INSERT INTO usuario (id, login, password, id_pessoa) VALUES (2, 'usuario', '$2a$10$jFW7LJBG3BATcGjxn1JWCu3G/jpVRfqgzyEXSx3I8F9IqbAZNp2Mi', 2);
INSERT INTO usuario (id, login, password, id_pessoa) VALUES (3, 'medico', '$2a$10$jFW7LJBG3BATcGjxn1JWCu3G/jpVRfqgzyEXSx3I8F9IqbAZNp2Mi', 2);
INSERT INTO usuario (id, login, password, id_pessoa) VALUES (4, 'secretario', '$2a$10$jFW7LJBG3BATcGjxn1JWCu3G/jpVRfqgzyEXSx3I8F9IqbAZNp2Mi', 2);
INSERT INTO usuario (id, login, password, id_pessoa) VALUES (5, 'paciente', '$2a$10$jFW7LJBG3BATcGjxn1JWCu3G/jpVRfqgzyEXSx3I8F9IqbAZNp2Mi', 2);

INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (1, 1);
INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (2, 2);
INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (3, 3);
INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (4, 4);
INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (5, 5);