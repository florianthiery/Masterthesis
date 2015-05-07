-------------------------
-- Initialize Database --
-------------------------

-- Create Schema
CREATE SCHEMA import AUTHORIZATION postgres;
CREATE SCHEMA intern AUTHORIZATION postgres;

-- Create Table potter in Schema intern
CREATE TABLE intern.potter (
	id integer PRIMARY KEY,
	name character varying,
	importdate timestamp with time zone
);

-- Create Table findspot in Schema intern
CREATE TABLE intern.findspot (
	id SERIAL PRIMARY KEY,
	name character varying,
	lat double precision,
	lon double precision,
	datemin integer,
	datemax integer,
	kilnsite boolean,
	importdate timestamp with time zone
);

-- Create Table fragment Schema intern 
CREATE TABLE intern.fragment (
	id integer PRIMARY KEY,
	p_name character varying,
	p_id integer,
	die character varying,
	potform character varying,
	number integer,
	f_name character varying,
	f_id integer,
	importdate timestamp with time zone
);

-- Create Table potter in Schema public
CREATE TABLE public.potter (
	id integer PRIMARY KEY,
	name character varying UNIQUE
);

-- Create Table findspot in Schema public
CREATE TABLE public.findspot (
	id integer PRIMARY KEY,
	name character varying UNIQUE,
	lat double precision,
	lon double precision,
	datemin integer,
	datemax integer,
	kilnsite boolean,
	geom geometry(Point,4326)
);

-- Create Table fragment in Schema public
CREATE TABLE public.fragment (
	id integer PRIMARY KEY,
	f_pid integer REFERENCES public.potter(id),
	f_fid integer REFERENCES public.findspot(id),
	die character varying,
	potform character varying,
	number integer
);

-- Create Views for Places and Locations
CREATE VIEW locations AS SELECT * FROM public.findspot WHERE public.findspot.name LIKE '%\_\_%' ORDER BY public.findspot.name ASC;
CREATE VIEW places AS SELECT * FROM public.findspot WHERE public.findspot.name NOT LIKE '%\_\_%' ORDER BY public.findspot.name ASC;