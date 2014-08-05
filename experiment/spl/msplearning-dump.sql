--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2014-08-04 21:26:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 185 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2036 (class 0 OID 0)
-- Dependencies: 185
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 170 (class 1259 OID 24759)
-- Name: sq_tb_app; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_app
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sq_tb_app OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 24761)
-- Name: sq_tb_discipline; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_discipline
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sq_tb_discipline OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 24765)
-- Name: sq_tb_educational_content; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_educational_content
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sq_tb_educational_content OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 24763)
-- Name: sq_tb_lesson; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_lesson
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sq_tb_lesson OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 24767)
-- Name: sq_tb_user; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sq_tb_user
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sq_tb_user OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 178 (class 1259 OID 24797)
-- Name: tb_app; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_app (
    id bigint DEFAULT nextval('sq_tb_app'::regclass) NOT NULL,
    date_creation timestamp without time zone NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.tb_app OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 24828)
-- Name: tb_app_feature; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_app_feature (
    id_app bigint NOT NULL,
    id_feature bigint NOT NULL,
    active boolean NOT NULL
);


ALTER TABLE public.tb_app_feature OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 24803)
-- Name: tb_app_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_app_user (
    id_app bigint NOT NULL,
    id_user bigint NOT NULL,
    date_request timestamp without time zone NOT NULL,
    active boolean NOT NULL,
    admin boolean NOT NULL
);


ALTER TABLE public.tb_app_user OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 24843)
-- Name: tb_discipline; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_discipline (
    id bigint DEFAULT nextval('sq_tb_discipline'::regclass) NOT NULL,
    description character varying(1000),
    name character varying(50) NOT NULL,
    id_app bigint NOT NULL,
    id_creator bigint NOT NULL
);


ALTER TABLE public.tb_discipline OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 24873)
-- Name: tb_educational_content; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_educational_content (
    id bigint DEFAULT nextval('sq_tb_educational_content'::regclass) NOT NULL,
    content character varying(500) NOT NULL,
    footnote character varying(50),
    media_type character varying(1) NOT NULL,
    page bigint NOT NULL,
    title character varying(50) NOT NULL,
    id_lesson bigint NOT NULL
);


ALTER TABLE public.tb_educational_content OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 24818)
-- Name: tb_feature; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_feature (
    id bigint NOT NULL,
    abstract boolean NOT NULL,
    hidden boolean NOT NULL,
    mandatory boolean NOT NULL,
    name character varying(50) NOT NULL,
    operator character varying(1),
    id_parent bigint
);


ALTER TABLE public.tb_feature OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 24862)
-- Name: tb_lesson; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_lesson (
    id bigint DEFAULT nextval('sq_tb_lesson'::regclass) NOT NULL,
    name character varying(50) NOT NULL,
    id_discipline bigint NOT NULL
);


ALTER TABLE public.tb_lesson OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 24777)
-- Name: tb_student; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_student (
    id_user bigint NOT NULL,
    academic_identifier character varying(50)
);


ALTER TABLE public.tb_student OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 24787)
-- Name: tb_teacher; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_teacher (
    id_user bigint NOT NULL,
    academic_identifier character varying(50)
);


ALTER TABLE public.tb_teacher OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 24769)
-- Name: tb_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tb_user (
    id bigint DEFAULT nextval('sq_tb_user'::regclass) NOT NULL,
    date_last_login timestamp without time zone NOT NULL,
    date_registration timestamp without time zone NOT NULL,
    email character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    gender character varying(1) NOT NULL,
    last_name character varying(50) NOT NULL,
    password character varying(30)
);


ALTER TABLE public.tb_user OWNER TO postgres;

--
-- TOC entry 2037 (class 0 OID 0)
-- Dependencies: 170
-- Name: sq_tb_app; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_app', 20, true);


--
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 171
-- Name: sq_tb_discipline; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_discipline', 1, true);


--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 173
-- Name: sq_tb_educational_content; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_educational_content', 1, false);


--
-- TOC entry 2040 (class 0 OID 0)
-- Dependencies: 172
-- Name: sq_tb_lesson; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_lesson', 1, false);


--
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 174
-- Name: sq_tb_user; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sq_tb_user', 24, true);


--
-- TOC entry 2022 (class 0 OID 24797)
-- Dependencies: 178
-- Data for Name: tb_app; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_app (id, date_creation, name) FROM stdin;
1	2014-08-04 16:54:52.44	My App Linda
2	2014-08-04 17:19:28.172	Teste
3	2014-08-04 17:57:48.767	ECU01 - Imagem (Odair Viol)
4	2014-08-04 17:58:02.981	Rodrigo Mendes
5	2014-08-04 17:58:09.856	janaiza_ECU02
6	2014-08-04 17:58:19.492	Fabricio Imagem
7	2014-08-04 17:58:27.638	AplicativoDoPaulo
8	2014-08-04 17:58:59.032	lais-garcia
9	2014-08-04 17:59:39.755	Eduardo
10	2014-08-04 17:59:41.922	Jaqueline
11	2014-08-04 18:00:07.964	GustavoBergamim
12	2014-08-04 18:11:17.271	Arthur
13	2014-08-04 19:15:31.316	experimento
14	2014-08-04 19:38:09.364	Experimento
15	2014-08-04 19:39:58.687	Douglas_app
16	2014-08-04 19:40:31.287	André Luiz dos Santos
17	2014-08-04 19:50:37.601	Francinei
18	2014-08-04 19:51:21.704	Experimento Image
19	2014-08-04 20:40:37.034	AndreCampopiano
20	2014-08-04 20:54:55.385	Adriano Bocaletti
\.


--
-- TOC entry 2025 (class 0 OID 24828)
-- Dependencies: 181
-- Data for Name: tb_app_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_app_feature (id_app, id_feature, active) FROM stdin;
1	1	f
1	2	f
1	3	f
1	4	f
2	1	f
2	2	f
2	3	f
2	4	f
3	1	f
3	2	f
3	3	f
3	4	f
4	1	f
4	2	f
4	3	f
4	4	f
5	1	f
5	2	f
5	3	f
5	4	f
6	1	f
6	2	f
6	3	f
6	4	f
7	1	f
7	2	f
7	3	f
7	4	f
8	1	f
8	2	f
8	3	f
8	4	f
9	1	f
9	2	f
9	3	f
9	4	f
10	1	f
10	2	f
10	3	f
10	4	f
11	1	f
11	2	f
11	3	f
11	4	f
12	1	f
12	2	f
12	3	f
12	4	f
13	1	f
13	2	f
13	3	f
13	4	f
14	1	f
14	2	f
14	3	f
14	4	f
15	1	f
15	2	f
15	3	f
15	4	f
16	1	f
16	2	f
16	3	f
16	4	f
17	1	f
17	2	f
17	3	f
17	4	f
18	1	f
18	2	f
18	3	f
18	4	f
19	1	f
19	2	f
19	3	f
19	4	f
20	1	f
20	2	f
20	3	f
20	4	f
\.


--
-- TOC entry 2023 (class 0 OID 24803)
-- Dependencies: 179
-- Data for Name: tb_app_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_app_user (id_app, id_user, date_request, active, admin) FROM stdin;
1	2	2014-08-04 16:54:52.484	t	t
2	2	2014-08-04 17:19:28.179	t	t
3	10	2014-08-04 17:57:48.773	t	t
4	6	2014-08-04 17:58:02.986	t	t
5	4	2014-08-04 17:58:09.861	t	t
6	8	2014-08-04 17:58:19.511	t	t
7	17	2014-08-04 17:58:27.644	t	t
8	16	2014-08-04 17:58:59.039	t	t
9	9	2014-08-04 17:59:39.76	t	t
10	18	2014-08-04 17:59:41.928	t	t
11	3	2014-08-04 18:00:07.984	t	t
12	19	2014-08-04 18:11:17.282	t	t
13	11	2014-08-04 19:15:31.334	t	t
14	7	2014-08-04 19:38:09.391	t	t
15	21	2014-08-04 19:39:58.693	t	t
16	5	2014-08-04 19:40:31.309	t	t
17	22	2014-08-04 19:50:37.618	t	t
18	23	2014-08-04 19:51:21.71	t	t
19	24	2014-08-04 20:40:37.058	t	t
20	13	2014-08-04 20:54:55.402	t	t
\.


--
-- TOC entry 2026 (class 0 OID 24843)
-- Dependencies: 182
-- Data for Name: tb_discipline; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_discipline (id, description, name, id_app, id_creator) FROM stdin;
\.


--
-- TOC entry 2028 (class 0 OID 24873)
-- Dependencies: 184
-- Data for Name: tb_educational_content; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_educational_content (id, content, footnote, media_type, page, title, id_lesson) FROM stdin;
\.


--
-- TOC entry 2024 (class 0 OID 24818)
-- Dependencies: 180
-- Data for Name: tb_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_feature (id, abstract, hidden, mandatory, name, operator, id_parent) FROM stdin;
1	f	f	t	Pedagogical	A	\N
7	f	f	f	Interactivity	\N	1
8	f	f	t	Multimedia Resources	O	1
5	f	f	t	Content Management	\N	1
6	f	f	t	Educational Activities	\N	1
20	f	f	f	Image	\N	8
19	f	f	f	Text	\N	8
21	f	f	f	Video	\N	8
2	t	f	t	Usability	A	\N
11	t	f	t	Intelligibility	\N	2
12	t	f	t	Learnability	\N	2
9	t	f	t	Accessibility	\N	2
10	t	f	t	Attractiveness	\N	2
13	t	f	t	Operability	\N	2
3	f	f	t	Compatibility	A	\N
15	f	f	t	Interoperability	\N	3
14	t	f	t	Coexistence	\N	3
4	f	f	t	Security	A	\N
18	f	f	t	Integrity	\N	4
17	f	f	t	Confidentiality	\N	4
16	f	f	t	Authenticity	\N	4
\.


--
-- TOC entry 2027 (class 0 OID 24862)
-- Dependencies: 183
-- Data for Name: tb_lesson; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_lesson (id, name, id_discipline) FROM stdin;
\.


--
-- TOC entry 2020 (class 0 OID 24777)
-- Dependencies: 176
-- Data for Name: tb_student; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_student (id_user, academic_identifier) FROM stdin;
\.


--
-- TOC entry 2021 (class 0 OID 24787)
-- Dependencies: 177
-- Data for Name: tb_teacher; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_teacher (id_user, academic_identifier) FROM stdin;
1	\N
2	\N
3	\N
4	\N
5	\N
6	\N
7	\N
8	\N
9	\N
10	\N
11	\N
13	\N
16	\N
17	\N
18	\N
19	\N
21	\N
22	\N
23	\N
24	\N
\.


--
-- TOC entry 2019 (class 0 OID 24769)
-- Dependencies: 175
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tb_user (id, date_last_login, date_registration, email, first_name, gender, last_name, password) FROM stdin;
1	2014-08-04 15:21:11.239	2014-08-04 15:21:11.239	venilton.falvo@gmail.com	Venilton	M	Falvo Junior	iDUQRA6XBqgo21hrQTEZ/Q==
2	2014-08-04 16:54:23.607	2014-08-04 16:54:23.607	renan.paula@cast.com.br	Renan	M	Johannsen de Paula	SF3okCFVyOnuOVBIM8I47A==
3	2014-08-04 17:21:21.907	2014-08-04 17:21:21.907	gustavo.bergamim@cast.com.br	Gustavo	M	Bergamim	E0fVPkc4w/NVkST+wn8Bjg==
4	2014-08-04 17:49:17.103	2014-08-04 17:49:17.103	janaiza.correia@cast.com.br	Janaiza	F	Correia	hWD8dS2qCsnFbAU1z+8llA==
5	2014-08-04 17:49:17.803	2014-08-04 17:49:17.803	andre.luiz@cast.com.br	André	M	Luiz dos Santos	nCkM0oGpw4FArCEAS7xUvg==
6	2014-08-04 17:52:25.778	2014-08-04 17:52:25.778	rodrigo.dasilva@cast.com.br	Rodrigo	M	Mendes	PGfDLg0m6179/UcDt+NoVA==
7	2014-08-04 17:52:35.368	2014-08-04 17:52:35.368	andre.bacaglini@gmail.com	André	M	Bacaglini	+4YhtRV6eJlEYIjZXsjW6Q==
8	2014-08-04 17:52:40.339	2014-08-04 17:52:40.339	fabricio.roncalio@cast.com.br	Fabricio	M	Roncalio	2nsSo/aYfcQ1XW4M5d+Hag==
9	2014-08-04 17:52:41.474	2014-08-04 17:52:41.474	eduardo.scamilhe@cast.com.br	Eduardo	M	Scamilhe	L0Y7I2pQ7I4HL97oK7d2nQ==
10	2014-08-04 17:52:43.57	2014-08-04 17:52:43.57	odair.pereira@cast.com.br	Odair	M	Viol R. Pereira	a3wct6vcsdX16TUGlZehaw==
11	2014-08-04 17:52:46.533	2014-08-04 17:52:46.533	douglas.dionizio@cast.com.br	Douglas	M	Dionizio	0CqX5WeNtJNhUYBwBCcbZQ==
13	2014-08-04 17:53:04.015	2014-08-04 17:53:04.015	adriano.bocaletti@cast.com.br	Adriano	M	Bocaletti	SC4iGo7EQbBFrsU4bQ2vDg==
16	2014-08-04 17:57:21.061	2014-08-04 17:57:21.061	lais.garcia@cast.com.br	Lais	F	Garcia	oUr8ta2aFCpNRvl9vVy1gw==
17	2014-08-04 17:57:39.207	2014-08-04 17:57:39.207	paulo.barros@cast.com.br	Paulo	M	Eduardo	qVz33GdzBHoCPnSUKccUZg==
18	2014-08-04 17:57:46.578	2014-08-04 17:57:46.578	jaqueline.mendes@cast.com.br	Jaqueline	F	Mendes	3rOxl0+oVJ7Ncc7rIdLKDw==
19	2014-08-04 18:09:04.707	2014-08-04 18:09:04.707	arthur.godoy@cast.com.br	Arthur	M	Godoy	k4P4X97nAQuQOcKk2f1ccQ==
21	2014-08-04 19:38:35.921	2014-08-04 19:38:35.921	douglas.santos@cast.com.br	Douglas	M	Santos	u+dAJQD1qWdi4+4JadgZyw==
22	2014-08-04 19:49:53.593	2014-08-04 19:49:53.593	francinei.marin@cast.com.br	Francinei	M	Marin	ynKyjV55xvHgnOS9vUzKhg==
23	2014-08-04 19:50:53.035	2014-08-04 19:50:53.035	felipe.camara@cast.com.br	Felipe	M	Camara	HhhpG4p4RTy4WUsQJqNzfg==
24	2014-08-04 20:38:13.874	2014-08-04 20:38:13.874	andre.campopiano@cast.com.br	Andre Luis	M	Campopiano	OO16llMCelqd3+nm04xTyA==
\.


--
-- TOC entry 1883 (class 2606 OID 24802)
-- Name: pk_tb_app; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_app
    ADD CONSTRAINT pk_tb_app PRIMARY KEY (id);


--
-- TOC entry 1889 (class 2606 OID 24832)
-- Name: pk_tb_app_feature; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_app_feature
    ADD CONSTRAINT pk_tb_app_feature PRIMARY KEY (id_app, id_feature);


--
-- TOC entry 1885 (class 2606 OID 24807)
-- Name: pk_tb_app_user; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_app_user
    ADD CONSTRAINT pk_tb_app_user PRIMARY KEY (id_app, id_user);


--
-- TOC entry 1891 (class 2606 OID 24851)
-- Name: pk_tb_discipline; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_discipline
    ADD CONSTRAINT pk_tb_discipline PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 24881)
-- Name: pk_tb_educational_content; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_educational_content
    ADD CONSTRAINT pk_tb_educational_content PRIMARY KEY (id);


--
-- TOC entry 1887 (class 2606 OID 24822)
-- Name: pk_tb_feature; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_feature
    ADD CONSTRAINT pk_tb_feature PRIMARY KEY (id);


--
-- TOC entry 1893 (class 2606 OID 24867)
-- Name: pk_tb_lesson; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_lesson
    ADD CONSTRAINT pk_tb_lesson PRIMARY KEY (id);


--
-- TOC entry 1879 (class 2606 OID 24781)
-- Name: pk_tb_student; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_student
    ADD CONSTRAINT pk_tb_student PRIMARY KEY (id_user);


--
-- TOC entry 1881 (class 2606 OID 24791)
-- Name: pk_tb_teacher; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_teacher
    ADD CONSTRAINT pk_tb_teacher PRIMARY KEY (id_user);


--
-- TOC entry 1875 (class 2606 OID 24774)
-- Name: pk_tb_user; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_user
    ADD CONSTRAINT pk_tb_user PRIMARY KEY (id);


--
-- TOC entry 1877 (class 2606 OID 24776)
-- Name: uk_tb_user_username; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tb_user
    ADD CONSTRAINT uk_tb_user_username UNIQUE (email);


--
-- TOC entry 1901 (class 2606 OID 24833)
-- Name: fk_tb_app_feature_tb_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_app_feature
    ADD CONSTRAINT fk_tb_app_feature_tb_app FOREIGN KEY (id_app) REFERENCES tb_app(id);


--
-- TOC entry 1902 (class 2606 OID 24838)
-- Name: fk_tb_app_feature_tb_feature; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_app_feature
    ADD CONSTRAINT fk_tb_app_feature_tb_feature FOREIGN KEY (id_feature) REFERENCES tb_feature(id);


--
-- TOC entry 1898 (class 2606 OID 24808)
-- Name: fk_tb_app_user_tb_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_app_user
    ADD CONSTRAINT fk_tb_app_user_tb_app FOREIGN KEY (id_app) REFERENCES tb_app(id);


--
-- TOC entry 1899 (class 2606 OID 24813)
-- Name: fk_tb_app_user_tb_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_app_user
    ADD CONSTRAINT fk_tb_app_user_tb_user FOREIGN KEY (id_user) REFERENCES tb_user(id);


--
-- TOC entry 1903 (class 2606 OID 24852)
-- Name: fk_tb_discipline_tb_app; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_discipline
    ADD CONSTRAINT fk_tb_discipline_tb_app FOREIGN KEY (id_app) REFERENCES tb_app(id);


--
-- TOC entry 1904 (class 2606 OID 24857)
-- Name: fk_tb_discipline_tb_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_discipline
    ADD CONSTRAINT fk_tb_discipline_tb_user FOREIGN KEY (id_creator) REFERENCES tb_user(id);


--
-- TOC entry 1906 (class 2606 OID 24882)
-- Name: fk_tb_educational_content_tb_lesson; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_educational_content
    ADD CONSTRAINT fk_tb_educational_content_tb_lesson FOREIGN KEY (id_lesson) REFERENCES tb_lesson(id);


--
-- TOC entry 1900 (class 2606 OID 24823)
-- Name: fk_tb_feature_tb_feature; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_feature
    ADD CONSTRAINT fk_tb_feature_tb_feature FOREIGN KEY (id_parent) REFERENCES tb_feature(id);


--
-- TOC entry 1905 (class 2606 OID 24868)
-- Name: fk_tb_lesson_tb_discipline; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_lesson
    ADD CONSTRAINT fk_tb_lesson_tb_discipline FOREIGN KEY (id_discipline) REFERENCES tb_discipline(id);


--
-- TOC entry 1896 (class 2606 OID 24782)
-- Name: fk_tb_student_tb_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_student
    ADD CONSTRAINT fk_tb_student_tb_user FOREIGN KEY (id_user) REFERENCES tb_user(id);


--
-- TOC entry 1897 (class 2606 OID 24792)
-- Name: fk_tb_teacher_tb_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tb_teacher
    ADD CONSTRAINT fk_tb_teacher_tb_user FOREIGN KEY (id_user) REFERENCES tb_user(id);


--
-- TOC entry 2035 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-08-04 21:26:46

--
-- PostgreSQL database dump complete
--

