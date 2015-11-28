/*
 * Copyright 2015 ranjan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rebataur.utils;

/**
 *
 * @author ranjan
 */
public class ConstantsSQL {
    public static String initPGSQL = "create table if not exists column_meta(\n" +
"	id bigserial primary key,\n" +
"	column_name text,\n" +
"	table_name text,\n" +
"	data_type text,\n" +
"	related_to_columns text[],\n" +
"	relation_types text[],\n" +
"	aliases text[]\n" +
"	\n" +
"	\n" +
");\n" +
"create table  if not exists service_meta(\n" +
"	id bigserial primary key,\n" +
"	service_name text,\n" +
"	service_type text,\n" +
"	related_to_columns text[],\n" +
"	refresh_interval_sec int,\n" +
"	represented_by_tables text[]  \n" +
"	\n" +
");\n" +
"\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'twitter_srv',\n" +
"	'social',\n" +
"	3600,\n" +
"	ARRAY['twitter_srv','twitter_search','twitter_home_timeline','twitter_friends','twitter_user_timeline','twitter_retweets_of_me','twitter_followers']	\n" +
");\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'aws_pg_srv',\n" +
"	'aws',\n" +
"	0,\n" +
"	ARRAY['webanalytics_aws']	\n" +
");\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'openweathermap_srv',\n" +
"	'weather',\n" +
"	3600*12,\n" +
"	ARRAY['fdw_openweathermap']	\n" +
");\n" +
"\n" +
"\n" +
"create table if not exists facts_table(\n" +
"	id bigserial primary key,\n" +
"	fact_table_name text,\n" +
"	table_name text,		\n" +
"	key_type text, \n" +
"	key_val text,\n" +
"	dt_type text\n" +
");";
    
    
  public static String createMetaTables = "create extension if not exists multicorn;\n" +
"\n" +
"-------------------------------------------------------------\n" +
"\n" +
"---DROP table column_meta cascade;\n" +
"create table if not exists column_meta(\n" +
"	id bigserial primary key,\n" +
"	column_name text,\n" +
"	table_name text,\n" +
"	data_type text,\n" +
"	related_to_columns text[],\n" +
"	relation_types text[],\n" +
"	aliases text[]\n" +
"	\n" +
"	\n" +
");\n" +
"---drop table service_meta cascade;\n" +
"drop table if exists service_meta cascade; \n" +
"create table if not exists  service_meta(\n" +
"	id bigserial primary key,\n" +
"	service_name text,\n" +
"	service_type text,\n" +
"	related_to_columns text[],\n" +
"	refresh_interval_sec int,\n" +
"	represented_by_tables text[]  \n" +
"	\n" +
");\n" +
"\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'twitter_srv',\n" +
"	'social',\n" +
"	3600,\n" +
"	ARRAY['twitter_srv','twitter_search','twitter_home_timeline','twitter_friends','twitter_user_timeline','twitter_retweets_of_me','twitter_followers']	\n" +
");\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'aws_pg_srv',\n" +
"	'aws',\n" +
"	0,\n" +
"	ARRAY['webanalytics_aws']	\n" +
");\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'openweathermap_srv',\n" +
"	'weather',\n" +
"	3600*12,\n" +
"	ARRAY['fdw_openweathermap']	\n" +
");\n" +
"\n" +
"select * from service_meta;\n" +
"\n" +
"---DROP table service_reg;\n" +
"create table if not exists service_reg( service_name text ,key_name text, key text,fk_id bigint references service_meta(id) null);\n" ;
  
  
  
  
  
  
  public static String processTwitter = "drop table if exists twitter_home_timeline;\n" +
"create table twitter_home_timeline(contributors text ,\n" +
"truncated text ,\n" +
"text text ,\n" +
"is_quote_status text ,\n" +
"in_reply_to_status_id text ,\n" +
"id text ,\n" +
"favorite_count int ,\n" +
"_api text ,\n" +
"author text ,\n" +
"_json text ,\n" +
"coordinates text ,\n" +
"entities text ,\n" +
"in_reply_to_screen_name text ,\n" +
"in_reply_to_user_id text ,\n" +
"retweet_count int ,\n" +
"id_str text ,\n" +
"favorited text ,\n" +
"source_url text ,\n" +
"_user text ,\n" +
"geo text ,\n" +
"in_reply_to_user_id_str text ,\n" +
"possibly_sensitive text ,\n" +
"possibly_sensitive_appealable text ,\n" +
"lang text ,\n" +
"created_at text ,\n" +
"in_reply_to_status_id_str text ,\n" +
"place text ,\n" +
"source text ,\n" +
"retweeted text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_home_timeline select tweet_data ->> 'contributors' as contributors,\n" +
"tweet_data ->> 'truncated' as truncated,\n" +
"tweet_data ->> 'text' as text,\n" +
"tweet_data ->> 'is_quote_status' as is_quote_status,\n" +
"tweet_data ->> 'in_reply_to_status_id' as in_reply_to_status_id,\n" +
"tweet_data ->> 'id' as id,\n" +
"(tweet_data ->> 'favorite_count')::text::int as favorite_count,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'author' as author,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'coordinates' as coordinates,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"tweet_data ->> 'in_reply_to_screen_name' as in_reply_to_screen_name,\n" +
"tweet_data ->> 'in_reply_to_user_id' as in_reply_to_user_id,\n" +
"(tweet_data ->> 'retweet_count')::text::int as retweet_count,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'favorited' as favorited,\n" +
"tweet_data ->> 'source_url' as source_url,\n" +
"tweet_data ->> '_user' as _user,\n" +
"tweet_data ->> 'geo' as geo,\n" +
"tweet_data ->> 'in_reply_to_user_id_str' as in_reply_to_user_id_str,\n" +
"tweet_data ->> 'possibly_sensitive' as possibly_sensitive,\n" +
"tweet_data ->> 'possibly_sensitive_appealable' as possibly_sensitive_appealable,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'in_reply_to_status_id_str' as in_reply_to_status_id_str,\n" +
"tweet_data ->> 'place' as place,\n" +
"tweet_data ->> 'source' as source,\n" +
"tweet_data ->> 'retweeted' as retweeted,pkid from twitter_srv where fn_name = 'home_timeline';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::::::::::::::user_timeline::::::::::::::\n" +
"drop table if exists twitter_user_timeline;\n" +
"create table twitter_user_timeline(contributors text ,\n" +
"truncated text ,\n" +
"text text ,\n" +
"is_quote_status text ,\n" +
"in_reply_to_status_id text ,\n" +
"id text ,\n" +
"favorite_count int ,\n" +
"_api text ,\n" +
"author text ,\n" +
"_json text ,\n" +
"coordinates text ,\n" +
"entities text ,\n" +
"in_reply_to_screen_name text ,\n" +
"in_reply_to_user_id text ,\n" +
"retweet_count int ,\n" +
"id_str text ,\n" +
"favorited text ,\n" +
"source_url text ,\n" +
"_user text ,\n" +
"geo text ,\n" +
"in_reply_to_user_id_str text ,\n" +
"lang text ,\n" +
"created_at text ,\n" +
"in_reply_to_status_id_str text ,\n" +
"place text ,\n" +
"source text ,\n" +
"retweeted text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_user_timeline select tweet_data ->> 'contributors' as contributors,\n" +
"tweet_data ->> 'truncated' as truncated,\n" +
"tweet_data ->> 'text' as text,\n" +
"tweet_data ->> 'is_quote_status' as is_quote_status,\n" +
"tweet_data ->> 'in_reply_to_status_id' as in_reply_to_status_id,\n" +
"tweet_data ->> 'id' as id,\n" +
"(tweet_data ->> 'favorite_count')::text::int as favorite_count,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'author' as author,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'coordinates' as coordinates,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"tweet_data ->> 'in_reply_to_screen_name' as in_reply_to_screen_name,\n" +
"tweet_data ->> 'in_reply_to_user_id' as in_reply_to_user_id,\n" +
"(tweet_data ->> 'retweet_count')::text::int as retweet_count,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'favorited' as favorited,\n" +
"tweet_data ->> 'source_url' as source_url,\n" +
"tweet_data ->> '_user' as _user,\n" +
"tweet_data ->> 'geo' as geo,\n" +
"tweet_data ->> 'in_reply_to_user_id_str' as in_reply_to_user_id_str,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'in_reply_to_status_id_str' as in_reply_to_status_id_str,\n" +
"tweet_data ->> 'place' as place,\n" +
"tweet_data ->> 'source' as source,\n" +
"tweet_data ->> 'retweeted' as retweeted,pkid from twitter_srv where fn_name = 'user_timeline';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::::::::::::::retweets_of_me::::::::::::::\n" +
"drop table if exists twitter_retweets_of_me;\n" +
"create table twitter_retweets_of_me(contributors text ,\n" +
"truncated text ,\n" +
"text text ,\n" +
"is_quote_status text ,\n" +
"in_reply_to_status_id text ,\n" +
"id text ,\n" +
"favorite_count int ,\n" +
"_api text ,\n" +
"author text ,\n" +
"_json text ,\n" +
"coordinates text ,\n" +
"entities text ,\n" +
"in_reply_to_screen_name text ,\n" +
"in_reply_to_user_id text ,\n" +
"retweet_count int ,\n" +
"id_str text ,\n" +
"favorited text ,\n" +
"source_url text ,\n" +
"_user text ,\n" +
"geo text ,\n" +
"in_reply_to_user_id_str text ,\n" +
"lang text ,\n" +
"created_at text ,\n" +
"in_reply_to_status_id_str text ,\n" +
"place text ,\n" +
"source text ,\n" +
"retweeted text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_retweets_of_me select tweet_data ->> 'contributors' as contributors,\n" +
"tweet_data ->> 'truncated' as truncated,\n" +
"tweet_data ->> 'text' as text,\n" +
"tweet_data ->> 'is_quote_status' as is_quote_status,\n" +
"tweet_data ->> 'in_reply_to_status_id' as in_reply_to_status_id,\n" +
"tweet_data ->> 'id' as id,\n" +
"(tweet_data ->> 'favorite_count')::text::int as favorite_count,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'author' as author,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'coordinates' as coordinates,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"tweet_data ->> 'in_reply_to_screen_name' as in_reply_to_screen_name,\n" +
"tweet_data ->> 'in_reply_to_user_id' as in_reply_to_user_id,\n" +
"(tweet_data ->> 'retweet_count')::text::int as retweet_count,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'favorited' as favorited,\n" +
"tweet_data ->> 'source_url' as source_url,\n" +
"tweet_data ->> '_user' as _user,\n" +
"tweet_data ->> 'geo' as geo,\n" +
"tweet_data ->> 'in_reply_to_user_id_str' as in_reply_to_user_id_str,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'in_reply_to_status_id_str' as in_reply_to_status_id_str,\n" +
"tweet_data ->> 'place' as place,\n" +
"tweet_data ->> 'source' as source,\n" +
"tweet_data ->> 'retweeted' as retweeted,pkid from twitter_srv where fn_name = 'retweets_of_me';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::::::::::::::followers::::::::::::::\n" +
"drop table if exists twitter_followers;\n" +
"create table twitter_followers(follow_request_sent text ,\n" +
"has_extended_profile text ,\n" +
"profile_use_background_image text ,\n" +
"_json text ,\n" +
"time_zone text ,\n" +
"id text ,\n" +
"description text ,\n" +
"_api text ,\n" +
"verified text ,\n" +
"blocked_by text ,\n" +
"profile_text_color text ,\n" +
"muting text ,\n" +
"profile_image_url_https text ,\n" +
"profile_sidebar_fill_color text ,\n" +
"is_translator text ,\n" +
"geo_enabled text ,\n" +
"entities text ,\n" +
"followers_count int ,\n" +
"protected text ,\n" +
"id_str text ,\n" +
"default_profile_image text ,\n" +
"listed_count int ,\n" +
"status text ,\n" +
"lang text ,\n" +
"utc_offset text ,\n" +
"statuses_count int ,\n" +
"profile_background_color text ,\n" +
"friends_count int ,\n" +
"profile_link_color text ,\n" +
"profile_image_url text ,\n" +
"notifications text ,\n" +
"profile_background_image_url_https text ,\n" +
"blocking text ,\n" +
"profile_background_image_url text ,\n" +
"name text ,\n" +
"is_translation_enabled text ,\n" +
"profile_background_tile text ,\n" +
"favourites_count int ,\n" +
"screen_name text ,\n" +
"url text ,\n" +
"created_at text ,\n" +
"contributors_enabled text ,\n" +
"location text ,\n" +
"profile_sidebar_border_color text ,\n" +
"default_profile text ,\n" +
"following text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_followers select tweet_data ->> 'follow_request_sent' as follow_request_sent,\n" +
"tweet_data ->> 'has_extended_profile' as has_extended_profile,\n" +
"tweet_data ->> 'profile_use_background_image' as profile_use_background_image,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'time_zone' as time_zone,\n" +
"tweet_data ->> 'id' as id,\n" +
"tweet_data ->> 'description' as description,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'verified' as verified,\n" +
"tweet_data ->> 'blocked_by' as blocked_by,\n" +
"tweet_data ->> 'profile_text_color' as profile_text_color,\n" +
"tweet_data ->> 'muting' as muting,\n" +
"tweet_data ->> 'profile_image_url_https' as profile_image_url_https,\n" +
"tweet_data ->> 'profile_sidebar_fill_color' as profile_sidebar_fill_color,\n" +
"tweet_data ->> 'is_translator' as is_translator,\n" +
"tweet_data ->> 'geo_enabled' as geo_enabled,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"(tweet_data ->> 'followers_count')::text::int as followers_count,\n" +
"tweet_data ->> 'protected' as protected,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'default_profile_image' as default_profile_image,\n" +
"(tweet_data ->> 'listed_count')::text::int as listed_count,\n" +
"tweet_data ->> 'status' as status,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'utc_offset' as utc_offset,\n" +
"(tweet_data ->> 'statuses_count')::text::int as statuses_count,\n" +
"tweet_data ->> 'profile_background_color' as profile_background_color,\n" +
"(tweet_data ->> 'friends_count')::text::int as friends_count,\n" +
"tweet_data ->> 'profile_link_color' as profile_link_color,\n" +
"tweet_data ->> 'profile_image_url' as profile_image_url,\n" +
"tweet_data ->> 'notifications' as notifications,\n" +
"tweet_data ->> 'profile_background_image_url_https' as profile_background_image_url_https,\n" +
"tweet_data ->> 'blocking' as blocking,\n" +
"tweet_data ->> 'profile_background_image_url' as profile_background_image_url,\n" +
"tweet_data ->> 'name' as name,\n" +
"tweet_data ->> 'is_translation_enabled' as is_translation_enabled,\n" +
"tweet_data ->> 'profile_background_tile' as profile_background_tile,\n" +
"(tweet_data ->> 'favourites_count')::text::int as favourites_count,\n" +
"tweet_data ->> 'screen_name' as screen_name,\n" +
"tweet_data ->> 'url' as url,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'contributors_enabled' as contributors_enabled,\n" +
"tweet_data ->> 'location' as location,\n" +
"tweet_data ->> 'profile_sidebar_border_color' as profile_sidebar_border_color,\n" +
"tweet_data ->> 'default_profile' as default_profile,\n" +
"tweet_data ->> 'following' as following,pkid from twitter_srv where fn_name = 'followers';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::::::::::::::friends::::::::::::::\n" +
"drop table if exists twitter_friends;\n" +
"create table twitter_friends(follow_request_sent text ,\n" +
"has_extended_profile text ,\n" +
"profile_use_background_image text ,\n" +
"_json text ,\n" +
"time_zone text ,\n" +
"id text ,\n" +
"description text ,\n" +
"_api text ,\n" +
"verified text ,\n" +
"blocked_by text ,\n" +
"profile_text_color text ,\n" +
"muting text ,\n" +
"profile_image_url_https text ,\n" +
"profile_sidebar_fill_color text ,\n" +
"is_translator text ,\n" +
"geo_enabled text ,\n" +
"entities text ,\n" +
"followers_count int ,\n" +
"protected text ,\n" +
"id_str text ,\n" +
"default_profile_image text ,\n" +
"listed_count int ,\n" +
"status text ,\n" +
"lang text ,\n" +
"utc_offset text ,\n" +
"statuses_count int ,\n" +
"profile_background_color text ,\n" +
"friends_count int ,\n" +
"profile_link_color text ,\n" +
"profile_image_url text ,\n" +
"notifications text ,\n" +
"profile_background_image_url_https text ,\n" +
"blocking text ,\n" +
"profile_background_image_url text ,\n" +
"name text ,\n" +
"is_translation_enabled text ,\n" +
"profile_background_tile text ,\n" +
"favourites_count int ,\n" +
"screen_name text ,\n" +
"url text ,\n" +
"created_at text ,\n" +
"contributors_enabled text ,\n" +
"location text ,\n" +
"profile_sidebar_border_color text ,\n" +
"default_profile text ,\n" +
"following text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_friends select tweet_data ->> 'follow_request_sent' as follow_request_sent,\n" +
"tweet_data ->> 'has_extended_profile' as has_extended_profile,\n" +
"tweet_data ->> 'profile_use_background_image' as profile_use_background_image,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'time_zone' as time_zone,\n" +
"tweet_data ->> 'id' as id,\n" +
"tweet_data ->> 'description' as description,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'verified' as verified,\n" +
"tweet_data ->> 'blocked_by' as blocked_by,\n" +
"tweet_data ->> 'profile_text_color' as profile_text_color,\n" +
"tweet_data ->> 'muting' as muting,\n" +
"tweet_data ->> 'profile_image_url_https' as profile_image_url_https,\n" +
"tweet_data ->> 'profile_sidebar_fill_color' as profile_sidebar_fill_color,\n" +
"tweet_data ->> 'is_translator' as is_translator,\n" +
"tweet_data ->> 'geo_enabled' as geo_enabled,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"(tweet_data ->> 'followers_count')::text::int as followers_count,\n" +
"tweet_data ->> 'protected' as protected,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'default_profile_image' as default_profile_image,\n" +
"(tweet_data ->> 'listed_count')::text::int as listed_count,\n" +
"tweet_data ->> 'status' as status,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'utc_offset' as utc_offset,\n" +
"(tweet_data ->> 'statuses_count')::text::int as statuses_count,\n" +
"tweet_data ->> 'profile_background_color' as profile_background_color,\n" +
"(tweet_data ->> 'friends_count')::text::int as friends_count,\n" +
"tweet_data ->> 'profile_link_color' as profile_link_color,\n" +
"tweet_data ->> 'profile_image_url' as profile_image_url,\n" +
"tweet_data ->> 'notifications' as notifications,\n" +
"tweet_data ->> 'profile_background_image_url_https' as profile_background_image_url_https,\n" +
"tweet_data ->> 'blocking' as blocking,\n" +
"tweet_data ->> 'profile_background_image_url' as profile_background_image_url,\n" +
"tweet_data ->> 'name' as name,\n" +
"tweet_data ->> 'is_translation_enabled' as is_translation_enabled,\n" +
"tweet_data ->> 'profile_background_tile' as profile_background_tile,\n" +
"(tweet_data ->> 'favourites_count')::text::int as favourites_count,\n" +
"tweet_data ->> 'screen_name' as screen_name,\n" +
"tweet_data ->> 'url' as url,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'contributors_enabled' as contributors_enabled,\n" +
"tweet_data ->> 'location' as location,\n" +
"tweet_data ->> 'profile_sidebar_border_color' as profile_sidebar_border_color,\n" +
"tweet_data ->> 'default_profile' as default_profile,\n" +
"tweet_data ->> 'following' as following,pkid from twitter_srv where fn_name = 'friends';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::::::::::::::search::::::::::::::\n" +
"drop table if exists twitter_search;\n" +
"create table twitter_search(contributors text ,\n" +
"truncated text ,\n" +
"text text ,\n" +
"is_quote_status text ,\n" +
"in_reply_to_status_id text ,\n" +
"id text ,\n" +
"favorite_count int ,\n" +
"_api text ,\n" +
"author text ,\n" +
"_json text ,\n" +
"coordinates text ,\n" +
"entities text ,\n" +
"in_reply_to_screen_name text ,\n" +
"id_str text ,\n" +
"retweet_count int ,\n" +
"in_reply_to_user_id text ,\n" +
"favorited text ,\n" +
"source_url text ,\n" +
"_user text ,\n" +
"geo text ,\n" +
"in_reply_to_user_id_str text ,\n" +
"possibly_sensitive text ,\n" +
"lang text ,\n" +
"created_at text ,\n" +
"in_reply_to_status_id_str text ,\n" +
"place text ,\n" +
"source text ,\n" +
"retweeted text ,\n" +
"metadata text ,fkid integer);\n" +
"\n" +
"------------------------------------------------------------\n" +
"insert into twitter_search select tweet_data ->> 'contributors' as contributors,\n" +
"tweet_data ->> 'truncated' as truncated,\n" +
"tweet_data ->> 'text' as text,\n" +
"tweet_data ->> 'is_quote_status' as is_quote_status,\n" +
"tweet_data ->> 'in_reply_to_status_id' as in_reply_to_status_id,\n" +
"tweet_data ->> 'id' as id,\n" +
"(tweet_data ->> 'favorite_count')::text::int as favorite_count,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'author' as author,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'coordinates' as coordinates,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"tweet_data ->> 'in_reply_to_screen_name' as in_reply_to_screen_name,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"(tweet_data ->> 'retweet_count')::text::int as retweet_count,\n" +
"tweet_data ->> 'in_reply_to_user_id' as in_reply_to_user_id,\n" +
"tweet_data ->> 'favorited' as favorited,\n" +
"tweet_data ->> 'source_url' as source_url,\n" +
"tweet_data ->> '_user' as _user,\n" +
"tweet_data ->> 'geo' as geo,\n" +
"tweet_data ->> 'in_reply_to_user_id_str' as in_reply_to_user_id_str,\n" +
"tweet_data ->> 'possibly_sensitive' as possibly_sensitive,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'in_reply_to_status_id_str' as in_reply_to_status_id_str,\n" +
"tweet_data ->> 'place' as place,\n" +
"tweet_data ->> 'source' as source,\n" +
"tweet_data ->> 'retweeted' as retweeted,\n" +
"tweet_data ->> 'metadata' as metadata,pkid from twitter_srv where fn_name = 'search';\n" +
"\n" +
"---::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
"---:::::::: USERS QUERY :::::::::::::::::::::\n" +
"DROP TABLE IF EXISTS TWITTER_USER;\n" +
"create table twitter_user(\n" +
"follow_request_sent text ,\n" +
"has_extended_profile text ,\n" +
"profile_use_background_image text ,\n" +
"_json text ,\n" +
"time_zone text ,\n" +
"id text ,\n" +
"_api text ,\n" +
"verified text ,\n" +
"profile_text_color text ,\n" +
"profile_image_url_https text ,\n" +
"profile_sidebar_fill_color text ,\n" +
"is_translator text ,\n" +
"geo_enabled text ,\n" +
"entities text ,\n" +
"followers_count int ,\n" +
"protected text ,\n" +
"id_str text ,\n" +
"default_profile_image text ,\n" +
"listed_count int ,\n" +
"lang text ,\n" +
"utc_offset text ,\n" +
"statuses_count int ,\n" +
"description text ,\n" +
"friends_count int ,\n" +
"profile_link_color text ,\n" +
"profile_image_url text ,\n" +
"notifications text ,\n" +
"profile_background_image_url_https text ,\n" +
"profile_background_color text ,\n" +
"profile_banner_url text ,\n" +
"profile_background_image_url text ,\n" +
"name text ,\n" +
"is_translation_enabled text ,\n" +
"profile_background_tile text ,\n" +
"favourites_count int ,\n" +
"screen_name text ,\n" +
"url text ,\n" +
"created_at text ,\n" +
"contributors_enabled text ,\n" +
"location text ,\n" +
"profile_sidebar_border_color text ,\n" +
"default_profile text ,\n" +
"following text, \n" +
"\n" +
"fkid integer\n" +
");\n" +
"\n" +
"\n" +
"insert into twitter_user select tweet_data ->> 'follow_request_sent' as follow_request_sent,\n" +
"tweet_data ->> 'has_extended_profile' as has_extended_profile,\n" +
"tweet_data ->> 'profile_use_background_image' as profile_use_background_image,\n" +
"tweet_data ->> '_json' as _json,\n" +
"tweet_data ->> 'time_zone' as time_zone,\n" +
"tweet_data ->> 'id' as id,\n" +
"tweet_data ->> '_api' as _api,\n" +
"tweet_data ->> 'verified' as verified,\n" +
"tweet_data ->> 'profile_text_color' as profile_text_color,\n" +
"tweet_data ->> 'profile_image_url_https' as profile_image_url_https,\n" +
"tweet_data ->> 'profile_sidebar_fill_color' as profile_sidebar_fill_color,\n" +
"tweet_data ->> 'is_translator' as is_translator,\n" +
"tweet_data ->> 'geo_enabled' as geo_enabled,\n" +
"tweet_data ->> 'entities' as entities,\n" +
"(tweet_data ->> 'followers_count')::text::int as followers_count,\n" +
"tweet_data ->> 'protected' as protected,\n" +
"tweet_data ->> 'id_str' as id_str,\n" +
"tweet_data ->> 'default_profile_image' as default_profile_image,\n" +
"(tweet_data ->> 'listed_count')::text::int as listed_count,\n" +
"tweet_data ->> 'lang' as lang,\n" +
"tweet_data ->> 'utc_offset' as utc_offset,\n" +
"(tweet_data ->> 'statuses_count')::text::int as statuses_count,\n" +
"tweet_data ->> 'description' as description,\n" +
"(tweet_data ->> 'friends_count')::text::int as friends_count,\n" +
"tweet_data ->> 'profile_link_color' as profile_link_color,\n" +
"tweet_data ->> 'profile_image_url' as profile_image_url,\n" +
"tweet_data ->> 'notifications' as notifications,\n" +
"tweet_data ->> 'profile_background_image_url_https' as profile_background_image_url_https,\n" +
"tweet_data ->> 'profile_background_color' as profile_background_color,\n" +
"tweet_data ->> 'profile_banner_url' as profile_banner_url,\n" +
"tweet_data ->> 'profile_background_image_url' as profile_background_image_url,\n" +
"tweet_data ->> 'name' as name,\n" +
"tweet_data ->> 'is_translation_enabled' as is_translation_enabled,\n" +
"tweet_data ->> 'profile_background_tile' as profile_background_tile,\n" +
"(tweet_data ->> 'favourites_count')::text::int as favourites_count,\n" +
"tweet_data ->> 'screen_name' as screen_name,\n" +
"tweet_data ->> 'url' as url,\n" +
"tweet_data ->> 'created_at' as created_at,\n" +
"tweet_data ->> 'contributors_enabled' as contributors_enabled,\n" +
"tweet_data ->> 'location' as location,\n" +
"tweet_data ->> 'profile_sidebar_border_color' as profile_sidebar_border_color,\n" +
"tweet_data ->> 'default_profile' as default_profile,\n" +
"tweet_data ->> 'following' as following,\n" +
"pkid\n" +
"from twitter_srv ;";
  
  
  
  
  
  
  
  public static String fdwTwitterSQL = "	--- drop and create teh server			\n" +
"			drop server if exists fdw_twitter_srv cascade;\n" +
"			create server fdw_twitter_srv  FOREIGN DATA WRAPPER multicorn\n" +
"			options (\n" +
" 				 wrapper 'reb_main.TwitterFDW'\n" +
"			);\n" +
"\n" +
"			--- drop and create the foreign table\n" +
"\n" +
"			DROP FOREIGN TABLE IF EXISTS fdw_twitter cascade;\n" +
"			CREATE FOREIGN TABLE fdw_twitter (\n" +
"			    tweet_data jsonb,\n" +
"			    fn_name text,\n" +
"			    search_text text			   \n" +
"			)server fdw_twitter_srv options(\n" +
"				access_token  '%s',\n" +
"				access_token_secret  '%s',\n" +
"				consumer_key '%s',\n" +
"				consumer_secret  '%s');		\n" +
"\n" +
"\n" +
"			CREATE TABLE IF NOT EXISTS twitter_srv (\n" +
"			    tweet_data jsonb,\n" +
"			    fn_name text,    \n" +
"			    search_text text,\n" +
"			    processed bool default false,\n" +
"			    entry_time timestamp default now(),\n" +
"			    pkid bigserial\n" +
"			);\n" +
"\n" +
"----------------------------------------\n" +
"\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'twitter_srv',\n" +
"	'social',\n" +
"	3600,\n" +
"	ARRAY['twitter_srv','twitter_search','twitter_home_timeline','twitter_friends','twitter_user_timeline','twitter_retweets_of_me','twitter_followers']	\n" +
");\n" +
"";
  
  
  
  
  public static String fdwAWSPG = "drop server if exists fdw_aws_pg_srv cascade;\n" +
"CREATE EXTENSION if not exists postgres_fdw;\n" +
"CREATE SERVER fdw_aws_pg_srv FOREIGN DATA WRAPPER postgres_fdw OPTIONS (host '%s', dbname '%s', port '%s');\n" +
"CREATE USER MAPPING FOR postgres SERVER fdw_aws_pg_srv OPTIONS (user '%s', password '%s' );\n" +
"\n" +
"CREATE FOREIGN TABLE fdw_webanalytics_aws\n" +
"(\n" +
"  dtype character varying(10),\n" +
"  event jsonb,\n" +
"  header jsonb,\n" +
"  createdat timestamp without time zone DEFAULT now()\n" +
") SERVER fdw_aws_pg_srv OPTIONS (schema_name 'public', table_name 'webanalytics_aws'); \n" +
"\n" +
"----------------------------------\n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'aws_pg_srv',\n" +
"	'aws',\n" +
"	0,\n" +
"	ARRAY['webanalytics_aws']	\n" +
");";
  
  
  
  
  
 public static String fdwWeatherSQL = "--- drop and create teh server			\n" +
"drop server if exists fdw_openweathermap_srv cascade;\n" +
"create server fdw_openweathermap_srv  FOREIGN DATA WRAPPER multicorn\n" +
"options (\n" +
"	 wrapper 'reb_main.OWMFDW'\n" +
");\n" +
"\n" +
"--- drop and create the foreign table\n" +
"\n" +
"DROP FOREIGN TABLE IF EXISTS fdw_openweathermap cascade;\n" +
"CREATE FOREIGN TABLE fdw_openweathermap (\n" +
"    weather_data text,\n" +
"    fn_name text,\n" +
"    city_name text,\n" +
"    country_name text 	   \n" +
")server fdw_openweathermap_srv options( key  '%s');		\n" +
"\n" +
"--------------------------- \n" +
"insert into service_meta(\n" +
"	service_name,\n" +
"	service_type,	\n" +
"	refresh_interval_sec,\n" +
"	represented_by_tables\n" +
")values(\n" +
"	'fdw_openweathermap',\n" +
"	'weather',\n" +
"	3600*12,\n" +
"	ARRAY['fdw_openweathermap']	\n" +
");";
 
 
 
 
}
