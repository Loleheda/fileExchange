-- Table "roles"
DROP TABLE IF EXISTS public.roles CASCADE;
CREATE TABLE IF NOT EXISTS public.roles
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name text COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.roles
    OWNER to postgres;


-- Table "users"
DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    login text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    role integer NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    enabled boolean DEFAULT true,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT login_unique UNIQUE (login),
    CONSTRAINT fk_role FOREIGN KEY (role)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

-- Table "files"
DROP TABLE IF EXISTS public.files CASCADE;
CREATE TABLE IF NOT EXISTS public.files
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    owner integer NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT files_pkey PRIMARY KEY (id),
    CONSTRAINT fk_owner FOREIGN KEY (owner)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.files
    OWNER to postgres;

-- Table "statuses"
DROP TABLE IF EXISTS public.statuses CASCADE;
CREATE TABLE IF NOT EXISTS public.statuses
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT statuses_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.statuses
    OWNER to postgres;


-- Table "requests"
DROP TABLE IF EXISTS public.requests CASCADE;
CREATE TABLE IF NOT EXISTS public.requests
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    recipient integer NOT NULL,
    status integer NOT NULL,
    file integer NOT NULL,
    CONSTRAINT recipients_pkey PRIMARY KEY (id),
    CONSTRAINT fk_file FOREIGN KEY (file)
        REFERENCES public.files (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_recipient_user FOREIGN KEY (recipient)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT fk_status FOREIGN KEY (status)
        REFERENCES public.statuses (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.requests
    OWNER to postgres;