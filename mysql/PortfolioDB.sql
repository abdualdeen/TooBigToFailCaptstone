-- clearing out database from lab
  -- drop database ahamad;
  -- create database ahamad;
  
-- required dropping of tables:****uncomment before submission.
-- drop table if exists Portfolio;
-- drop table if exists Asset;
-- drop table if exists Person;

-- creating 3 Tables; Person, Asset, and Portfolio.

create table if not exists Person (
  personId int primary key not null auto_increment,
  alphaCode varchar(10),
  brokerStat varchar(10),
  name varchar(100),
  address varchar(255),
  emailAddress varchar(255));
  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  portCode varchar(10),
  persCode varchar(10),
  managCode varchar(10),
  benefCode varchar(10),
  asset varchar(255));
  
create table if not exists Asset (
  assId int primary key not null auto_increment,
  assCode varchar(10),
  assType varchar(1),
  label varchar(25),
  apr double,
  quartDivi double,
  baseROR double,
  beta double,
  omega double,
  stockSymb varchar(5),
  sharePrice double,
  shareNum int);
  
