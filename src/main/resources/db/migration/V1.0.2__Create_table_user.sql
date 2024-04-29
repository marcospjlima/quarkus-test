DROP TABLE IF EXISTS public.usuario CASCADE
;

CREATE TABLE public.usuario
(
    id 			varchar(36) NOT null,
    name 		varchar(255) NOT null,
    email   	varchar(255) NOT null,
    password    varchar(255) NOT null,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.usuario
    OWNER to postgres;
    
    
--CREATE SEQUENCE IF NOT EXISTS public.usuario_seq START 1;