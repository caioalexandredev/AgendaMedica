INSERT INTO paciente (id, nome, telefone) VALUES ( 1, 'João Silva', '(11) 1 1111-1111');
INSERT INTO paciente (id, nome, telefone) VALUES ( 2, 'Maria Oliveira', '(11) 1 1111-1111');
INSERT INTO paciente (id, nome, telefone) VALUES ( 3, 'Carlos Santos', '(11) 1 1111-1111');
INSERT INTO paciente (id, nome, telefone) VALUES ( 4, 'Ana Souza', '(11) 1 1111-1111');

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

INSERT INTO status_horario_agenda (id, descricao) VALUES ( 1, 'DISPONÍVEL');
INSERT INTO status_horario_agenda (id, descricao) VALUES ( 2, 'AGENDADO');
INSERT INTO status_horario_agenda (id, descricao) VALUES ( 3, 'CANCELADO');
INSERT INTO status_horario_agenda (id, descricao) VALUES ( 4, 'CONFLITO');