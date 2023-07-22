DROP TABLE IF EXISTS public.myentity CASCADE
;

CREATE TABLE public.myentity
(
    id bigint,
    field varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.myentity
    OWNER to postgres;
    
    
CREATE SEQUENCE IF NOT EXISTS public.myentity_seq START 1;