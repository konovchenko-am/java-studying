--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.24
-- Dumped by pg_dump version 9.6.24

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    title character varying(255)
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- Name: goods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.goods (
    id bigint NOT NULL,
    info character varying(255),
    price integer NOT NULL,
    title character varying(255),
    category_id bigint
);


ALTER TABLE public.goods OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: shipments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shipments (
    id bigint NOT NULL,
    date character varying(255),
    user_id bigint
);


ALTER TABLE public.shipments OWNER TO postgres;

--
-- Name: shipped_goods; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shipped_goods (
    id bigint NOT NULL,
    amount integer NOT NULL,
    sum integer NOT NULL,
    good_id bigint,
    shipment_id bigint
);


ALTER TABLE public.shipped_goods OWNER TO postgres;

--
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    id bigint NOT NULL,
    amount integer NOT NULL,
    sum integer NOT NULL,
    good_id bigint
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    login character varying(255),
    password character varying(255),
    role character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, title) FROM stdin;
13	Столярные инструменты
14	Слесарные инструменты
\.


--
-- Data for Name: goods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.goods (id, info, price, title, category_id) FROM stdin;
5	Деревянный	1000	Рубанок	13
6	Пластиковая ручка	1200	Ножовка по дереву	13
8	Раритетный	8000	Коловорот	13
9	По дереву	800	Напильник драчковый	13
12	По дереву	9999	Пила электричечская, циркулярная	13
7	Пластиковая ручка	1250	Ножовка по металлу	14
10	По металлу	900	Напильник средний	14
11	По металлу	5900	Дрель электрическая	14
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 29, true);


--
-- Data for Name: shipments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.shipments (id, date, user_id) FROM stdin;
23	01.06.2023	1
27	01.06.2023	4
\.


--
-- Data for Name: shipped_goods; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.shipped_goods (id, amount, sum, good_id, shipment_id) FROM stdin;
24	4	39996	12	23
26	1	8000	8	23
28	2	1600	9	27
29	1	5900	11	27
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stock (id, amount, sum, good_id) FROM stdin;
15	46	46000	5
16	43	51600	6
17	21	26250	7
20	46	41400	10
18	34	272000	8
19	112	89600	9
21	36	212400	11
22	54	539946	12
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, login, password, role) FROM stdin;
1	user	123	USER
2	admin	123	ADMIN
4	guest	123	USER
\.


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: goods goods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT goods_pkey PRIMARY KEY (id);


--
-- Name: shipments shipments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT shipments_pkey PRIMARY KEY (id);


--
-- Name: shipped_goods shipped_goods_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shipped_goods
    ADD CONSTRAINT shipped_goods_pkey PRIMARY KEY (id);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: stock fk1tte9i4wg0e88xx43ceoi7bug; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT fk1tte9i4wg0e88xx43ceoi7bug FOREIGN KEY (good_id) REFERENCES public.goods(id);


--
-- Name: shipped_goods fk7nnwbx69ja0nista3xc2e5sh1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shipped_goods
    ADD CONSTRAINT fk7nnwbx69ja0nista3xc2e5sh1 FOREIGN KEY (good_id) REFERENCES public.goods(id);


--
-- Name: shipments fkacls0n0aowv3qw29f7gajfwk6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT fkacls0n0aowv3qw29f7gajfwk6 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: shipped_goods fke7wt2mqy3rshmgkm70r3g2b1l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shipped_goods
    ADD CONSTRAINT fke7wt2mqy3rshmgkm70r3g2b1l FOREIGN KEY (shipment_id) REFERENCES public.shipments(id);


--
-- Name: goods fkm164hdre8y3lew7lvtu0sneqe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.goods
    ADD CONSTRAINT fkm164hdre8y3lew7lvtu0sneqe FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- PostgreSQL database dump complete
--

