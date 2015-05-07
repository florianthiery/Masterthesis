-----------------
-- Import Data --
-----------------

-- Create Table potter in Schema import
CREATE TABLE import.potter (
	id integer PRIMARY KEY,
	name character varying
);

-- Import potter Table with DELIMITER ';' AND Create Timestamp
SET client_encoding = 'win1252';
COPY import.potter FROM '/tmp/potter.txt' WITH DELIMITER AS ';';
ALTER TABLE import.potter ADD COLUMN importdate timestamp with time zone;
UPDATE import.potter SET importdate = now();

-- Create Table findspot in Schema import
CREATE TABLE import.findspot (
	id SERIAL PRIMARY KEY,
	name character varying,
	c1 double precision,
	c2 double precision,
	datemin integer,
	datemax integer,
	kilnsite boolean
);

-- Import potter Table with DELIMITER ';' AND Create Timestamp (coordinates with '.' | Export or Kilnsite | No Dates = 10000)
SET client_encoding = 'win1252';
COPY import.findspot (name, c1, c2, datemin, datemax, kilnsite) FROM '/tmp/findspot.txt' WITH DELIMITER AS ';';
ALTER TABLE import.findspot ADD COLUMN importdate timestamp with time zone;
UPDATE import.findspot SET importdate = now();

-- Create Table fragment in import
CREATE TABLE import.fragment (
	id integer PRIMARY KEY,
	pottername character varying,
	die character varying,
	potform character varying,
	number integer,
	findspot character varying
);

-- Import potter Table with DELIMITER ';' AND Create Timestamp
SET client_encoding = 'win1252';
COPY import.fragment FROM '/tmp/fragment.txt' WITH DELIMITER AS ';';
ALTER TABLE import.fragment ADD COLUMN importdate timestamp with time zone;
UPDATE import.fragment SET importdate = now();

------------------
-- First Import --
------------------

-- Copy Data from Schema import to Schema intern
INSERT INTO intern.potter (id, name, importdate)
	SELECT import.potter.id, import.potter.name, import.potter.importdate
	FROM import.potter;
	
-- Copy Data from Schema intern to Schema public
INSERT INTO public.potter (id, name)
	SELECT intern.potter.id, intern.potter.name
	FROM intern.potter;
	
-- Copy Data from Schema import to Schema intern AND UPDATE 10000 to NULL AND Create GeometryColumn
INSERT INTO intern.findspot (name, lat, lon, datemin, datemax, kilnsite, importdate)
	SELECT import.findspot.name, import.findspot.c2, import.findspot.c1, import.findspot.datemin, import.findspot.datemax, import.findspot.kilnsite, import.findspot.importdate
	FROM import.findspot;
--UPDATE intern.findspot SET datemax=NULL WHERE datemax = 10000;
--UPDATE intern.findspot SET datemin=NULL WHERE datemin = 10000;
SELECT AddGeometryColumn( 'intern', 'findspot', 'geom', 4326, 'POINT', 2 );
UPDATE intern.findspot SET geom = ST_SetSRID( ST_Point(lon, lat),4326 );

-- Copy Data from Schema intern to Schema public
INSERT INTO public.findspot (id, name, lat, lon, datemin, datemax, kilnsite, geom)
	SELECT intern.findspot.id, intern.findspot.name, intern.findspot.lat, intern.findspot.lon, intern.findspot.datemin, intern.findspot.datemax, intern.findspot.kilnsite, intern.findspot.geom
	FROM intern.findspot;
	
-- Copy Data from Schema intern to Schema public AND create foreign keys
INSERT INTO intern.fragment (id, die, potform, number, importdate, p_name, f_name)
	SELECT import.fragment.id, import.fragment.die, import.fragment.potform, import.fragment.number, import.fragment.importdate, import.fragment.pottername, import.fragment.findspot
	FROM import.fragment;
UPDATE intern.fragment
SET p_id = intern.potter.id 
FROM intern.potter 
WHERE intern.fragment.p_name = intern.potter.name;
UPDATE intern.fragment
SET f_id = intern.findspot.id 
FROM intern.findspot 
WHERE intern.fragment.f_name = intern.findspot.name;

-- Copy Data from Schema intern to Schema public
INSERT INTO public.fragment
	SELECT intern.fragment.id, intern.fragment.p_id, intern.fragment.f_id, intern.fragment.die, intern.fragment.potform, intern.fragment.number
	FROM intern.fragment;