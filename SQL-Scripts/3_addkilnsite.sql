-----------------------
-- Update Findspots  --
-----------------------

-- Create Table findspot in Schema import
CREATE TABLE import.kilnsites (
	id SERIAL PRIMARY KEY,
	name character varying,
	c1 double precision,
	c2 double precision,
	datemin integer,
	datemax integer,
	kilnsite boolean
);

-- Import kilnsites Table with DELIMITER ';' AND Create Timestamp (coordinates with '.' | Export or Kilnsite | No Dates = 10000)
SET client_encoding = 'win1252';
COPY import.kilnsites (name, c1, c2, datemin, datemax, kilnsite) FROM '/tmp/kilnsite.txt' WITH DELIMITER AS ';';
ALTER TABLE import.kilnsites ADD COLUMN importdate timestamp with time zone;
UPDATE import.kilnsites SET importdate = now();

-- Copy Data from Schema import to Schema intern if name not exists and do sth. with the data
INSERT INTO intern.findspot (name, lat, lon, datemin, datemax, kilnsite, importdate)	
	SELECT import.kilnsites.name, import.kilnsites.c2, import.kilnsites.c1, import.kilnsites.datemin, import.kilnsites.datemax, import.kilnsites.kilnsite, import.kilnsites.importdate 
	FROM import.kilnsites 
	WHERE name IN (
		SELECT name FROM import.kilnsites
		EXCEPT
		SELECT name FROM intern.findspot
	) ;

-- Create Geometry
UPDATE intern.findspot SET geom = ST_SetSRID( ST_Point(lon, lat),4326 );

-- Update all Elements (name is fix)
UPDATE intern.findspot SET lat = import.kilnsites.c2 FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;
UPDATE intern.findspot SET lon = import.kilnsites.c1 FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;
UPDATE intern.findspot SET datemin = import.kilnsites.datemin FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;
UPDATE intern.findspot SET datemax = import.kilnsites.datemax FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;
UPDATE intern.findspot SET kilnsite = import.kilnsites.kilnsite FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;
UPDATE intern.findspot SET importdate = import.kilnsites.importdate FROM import.kilnsites WHERE intern.findspot.name = import.kilnsites.name;

-- Delete Values from public Tables
DELETE FROM public.fragment;
DELETE FROM public.potter;
DELETE FROM public.findspot;

-- Update public Tables
-- Copy Data from Schema intern to Schema public
INSERT INTO public.potter (id, name)
	SELECT intern.potter.id, intern.potter.name
	FROM intern.potter;

-- Copy Data from Schema intern to Schema public
INSERT INTO public.findspot (id, name, lat, lon, datemin, datemax, kilnsite, geom)
	SELECT intern.findspot.id, intern.findspot.name, intern.findspot.lat, intern.findspot.lon, intern.findspot.datemin, intern.findspot.datemax, intern.findspot.kilnsite, intern.findspot.geom
	FROM intern.findspot;

-- Copy Data from Schema intern to Schema public
INSERT INTO public.fragment
	SELECT intern.fragment.id, intern.fragment.p_id, intern.fragment.f_id, intern.fragment.die, intern.fragment.potform, intern.fragment.number
	FROM intern.fragment;