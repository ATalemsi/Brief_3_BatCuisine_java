--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-24 20:17:56

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16726)
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id_client integer NOT NULL,
    nom character varying(255) NOT NULL,
    adresse text NOT NULL,
    telephone character varying(20) NOT NULL,
    est_professionnel boolean NOT NULL,
    prenom character varying(50)
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16725)
-- Name: clients_id_client_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_client_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_client_seq OWNER TO postgres;

--
-- TOC entry 4883 (class 0 OID 0)
-- Dependencies: 215
-- Name: clients_id_client_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_client_seq OWNED BY public.clients.id_client;


--
-- TOC entry 222 (class 1259 OID 24908)
-- Name: composant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.composant (
    id_composant integer NOT NULL,
    nom character varying(255) NOT NULL,
    type_composant character varying(50),
    taux_tva double precision NOT NULL,
    id_projet integer,
    CONSTRAINT composant_type_composant_check CHECK (((type_composant)::text = ANY ((ARRAY['Materiel'::character varying, 'Main-œuvre'::character varying])::text[])))
);


ALTER TABLE public.composant OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 24907)
-- Name: composant_id_composant_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.composant_id_composant_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.composant_id_composant_seq OWNER TO postgres;

--
-- TOC entry 4884 (class 0 OID 0)
-- Dependencies: 221
-- Name: composant_id_composant_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.composant_id_composant_seq OWNED BY public.composant.id_composant;


--
-- TOC entry 220 (class 1259 OID 16764)
-- Name: devis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.devis (
    id_devis integer NOT NULL,
    montant_estime double precision NOT NULL,
    date_emission date NOT NULL,
    date_validite date NOT NULL,
    accepte boolean NOT NULL,
    id_projet integer
);


ALTER TABLE public.devis OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16763)
-- Name: devis_id_devis_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.devis_id_devis_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.devis_id_devis_seq OWNER TO postgres;

--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 219
-- Name: devis_id_devis_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.devis_id_devis_seq OWNED BY public.devis.id_devis;


--
-- TOC entry 224 (class 1259 OID 24920)
-- Name: main_d_oeuvre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.main_d_oeuvre (
    taux_horaire double precision NOT NULL,
    heures_travail double precision NOT NULL,
    productivite_ouvrier double precision NOT NULL
)
INHERITS (public.composant);


ALTER TABLE public.main_d_oeuvre OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24915)
-- Name: materiel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.materiel (
    cout_unitaire double precision NOT NULL,
    quantite double precision NOT NULL,
    cout_transport double precision NOT NULL,
    coefficient_qualite double precision NOT NULL
)
INHERITS (public.composant);


ALTER TABLE public.materiel OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16735)
-- Name: projets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projets (
    id_projet integer NOT NULL,
    nom_projet character varying(255) NOT NULL,
    marge_beneficiaire double precision NOT NULL,
    cout_total double precision DEFAULT 0,
    etat_projet character varying(20) NOT NULL,
    id_client integer,
    adresse character varying(50),
    CONSTRAINT projets_etat_projet_check CHECK (((etat_projet)::text = ANY (ARRAY[('EN_COURS'::character varying)::text, ('TERMINE'::character varying)::text, ('ANNULE'::character varying)::text])))
);


ALTER TABLE public.projets OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16734)
-- Name: projets_id_projet_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.projets_id_projet_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.projets_id_projet_seq OWNER TO postgres;

--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 217
-- Name: projets_id_projet_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.projets_id_projet_seq OWNED BY public.projets.id_projet;


--
-- TOC entry 4711 (class 2604 OID 16729)
-- Name: clients id_client; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id_client SET DEFAULT nextval('public.clients_id_client_seq'::regclass);


--
-- TOC entry 4715 (class 2604 OID 24911)
-- Name: composant id_composant; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composant ALTER COLUMN id_composant SET DEFAULT nextval('public.composant_id_composant_seq'::regclass);


--
-- TOC entry 4714 (class 2604 OID 16767)
-- Name: devis id_devis; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devis ALTER COLUMN id_devis SET DEFAULT nextval('public.devis_id_devis_seq'::regclass);


--
-- TOC entry 4717 (class 2604 OID 24923)
-- Name: main_d_oeuvre id_composant; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.main_d_oeuvre ALTER COLUMN id_composant SET DEFAULT nextval('public.composant_id_composant_seq'::regclass);


--
-- TOC entry 4716 (class 2604 OID 24918)
-- Name: materiel id_composant; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiel ALTER COLUMN id_composant SET DEFAULT nextval('public.composant_id_composant_seq'::regclass);


--
-- TOC entry 4712 (class 2604 OID 16738)
-- Name: projets id_projet; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projets ALTER COLUMN id_projet SET DEFAULT nextval('public.projets_id_projet_seq'::regclass);


--
-- TOC entry 4723 (class 2606 OID 16733)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id_client);


--
-- TOC entry 4729 (class 2606 OID 24914)
-- Name: composant composant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composant
    ADD CONSTRAINT composant_pkey PRIMARY KEY (id_composant);


--
-- TOC entry 4727 (class 2606 OID 16769)
-- Name: devis devis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devis
    ADD CONSTRAINT devis_pkey PRIMARY KEY (id_devis);


--
-- TOC entry 4725 (class 2606 OID 16742)
-- Name: projets projets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projets
    ADD CONSTRAINT projets_pkey PRIMARY KEY (id_projet);


--
-- TOC entry 4731 (class 2606 OID 16770)
-- Name: devis devis_id_projet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devis
    ADD CONSTRAINT devis_id_projet_fkey FOREIGN KEY (id_projet) REFERENCES public.projets(id_projet) ON DELETE CASCADE;


--
-- TOC entry 4733 (class 2606 OID 33099)
-- Name: materiel fk_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.materiel
    ADD CONSTRAINT fk_project FOREIGN KEY (id_projet) REFERENCES public.projets(id_projet) ON DELETE CASCADE;


--
-- TOC entry 4734 (class 2606 OID 33104)
-- Name: main_d_oeuvre fk_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.main_d_oeuvre
    ADD CONSTRAINT fk_project FOREIGN KEY (id_projet) REFERENCES public.projets(id_projet) ON DELETE CASCADE;


--
-- TOC entry 4732 (class 2606 OID 24925)
-- Name: composant fk_projet; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.composant
    ADD CONSTRAINT fk_projet FOREIGN KEY (id_projet) REFERENCES public.projets(id_projet) ON DELETE CASCADE;


--
-- TOC entry 4730 (class 2606 OID 16743)
-- Name: projets projets_id_client_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projets
    ADD CONSTRAINT projets_id_client_fkey FOREIGN KEY (id_client) REFERENCES public.clients(id_client) ON DELETE CASCADE;


-- Completed on 2024-09-24 20:17:57

--
-- PostgreSQL database dump complete
--

