TRUNCATE TABLE topicos;
TRUNCATE TABLE usuarios;

ALTER TABLE topicos
add autor_id BIGINT NOT NULL;

ALTER TABLE topicos
ADD CONSTRAINT fk_topicos_autor_id
FOREIGN KEY (autor_id) REFERENCES usuarios(id);