create_meta_tables = """
create extension if not exists multicorn;

-------------------------------------------------------------

---DROP table column_meta cascade;
create table if not exists column_meta(
	id bigserial primary key,
	column_name text,
	table_name text,
	data_type text,
	related_to_columns text[],
	relation_types text[],
	aliases text[]
	
	
);
---drop table service_meta cascade;
create table if not exists  service_meta(
	id bigserial primary key,
	service_name text,
	service_type text,
	related_to_columns text[],
	refresh_interval_sec int,
	represented_by_tables text[]  
	
);

insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'twitter_srv',
	'social',
	3600,
	ARRAY['twitter_srv','twitter_search','twitter_home_timeline','twitter_friends','twitter_user_timeline','twitter_retweets_of_me','twitter_followers']	
);
insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'aws_pg_srv',
	'aws',
	0,
	ARRAY['webanalytics_aws']	
);
insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'fdw_openweathermap',
	'weather',
	3600*12,
	ARRAY['fdw_openweathermap']	
);

select * from service_meta;

---DROP table service_reg;
create table if not exists service_reg( service_name text ,key_name text, key text,fk_id bigint references service_meta(id) null);

"""



twitter_fdw_sql = """	
			--- drop and create teh server			
			drop server if exists fdw_twitter_srv cascade;
			create server fdw_twitter_srv  FOREIGN DATA WRAPPER multicorn
			options (
 				 wrapper 'reb_main.TwitterFDW'
			);

			--- drop and create the foreign table

			DROP FOREIGN TABLE IF EXISTS fdw_twitter cascade;
			CREATE FOREIGN TABLE fdw_twitter (
			    tweet_data jsonb,
			    fn_name text,
			    search_text text			   
			)server fdw_twitter_srv options(
				access_token  '%s',
				access_token_secret  '%s',
				consumer_key '%s',
				consumer_secret  '%s');		


			CREATE TABLE IF NOT EXISTS twitter_srv (
			    tweet_data jsonb,
			    fn_name text,    
			    search_text text,
			    processed bool default false,
			    entry_time timestamp default now(),
			    pkid bigserial
			);

----------------------------------------

insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'twitter_srv',
	'social',
	3600,
	ARRAY['twitter_srv','twitter_search','twitter_home_timeline','twitter_friends','twitter_user_timeline','twitter_retweets_of_me','twitter_followers']	
);

"""

aws_pg_fdw_sql = """
drop server if exists fdw_aws_pg_srv cascade;
CREATE EXTENSION if not exists postgres_fdw;
CREATE SERVER fdw_aws_pg_srv FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host '%s', dbname '%s', port '%s');
CREATE USER MAPPING FOR postgres SERVER fdw_aws_pg_srv OPTIONS (user '%s', password '%s' );

CREATE FOREIGN TABLE fdw_webanalytics_aws
(
  dtype character varying(10),
  event jsonb,
  header jsonb,
  createdat timestamp without time zone DEFAULT now()
) SERVER fdw_aws_pg_srv OPTIONS (schema_name 'public', table_name 'webanalytics_aws'); 

----------------------------------
insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'aws_pg_srv',
	'aws',
	0,
	ARRAY['webanalytics_aws']	
);
"""


owm_fdw_sql = """	
--- drop and create teh server			
drop server if exists fdw_openweathermap_srv cascade;
create server fdw_openweathermap_srv  FOREIGN DATA WRAPPER multicorn
options (
	 wrapper 'reb_main.OWMFDW'
);

--- drop and create the foreign table

DROP FOREIGN TABLE IF EXISTS fdw_openweathermap cascade;
CREATE FOREIGN TABLE fdw_openweathermap (
    weather_data text,
    fn_name text,
    city_name text,
    country_name text 	   
)server fdw_openweathermap_srv options( key  '%s');		

--------------------------- 
insert into service_meta(
	service_name,
	service_type,	
	refresh_interval_sec,
	represented_by_tables
)values(
	'fdw_openweathermap',
	'weather',
	3600*12,
	ARRAY['fdw_openweathermap']	
);

"""
