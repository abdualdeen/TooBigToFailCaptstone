-- clearing out database from lab
  -- drop database ahamad;
  -- create database ahamad;
  
-- required dropping of tables:****uncomment before submission.*****
-- drop table if exists Portfolio;
-- drop table if exists Asset;
-- drop table if exists Person;

-- creating 3 Tables; Person, Asset, and Portfolio.
use ahamad;
create table if not exists Person (
  personId int primary key not null auto_increment,
  alphaCode varchar(10),
  brokerStat varchar(10),
  lastName varchar(25),
  firstName varchar(25),
  -- addressId varchar(255) not null, foreign key (addressId) references Address(addressId),
  emailAddress varchar(255));
  
create table if not exists Address (
  addressId int primary key not null auto_increment,
  personId int not null, foreign key (personId) references Person(personId),
  street varchar(100),
  city varchar(25),
  state varchar(25),
  zip int,
  country varchar(25));
  
  
create table if not exists Asset (
  assId int primary key not null auto_increment,
  personId int not null, foreign key (personId) references Person(personId),
  assCode varchar(10),
  assType varchar(1),
  label varchar(25));
  
create table if not exists DepositAcc (
  depoId int primary key not null auto_increment,
  assId int not null, foreign key (assId) references Asset(assId),
  apr double,
  balance double);

create table if not exists StockAcc (
  stockId int primary key not null auto_increment,
  assId int not null, foreign key(assId) references Asset(assId),
  quartDivi double,
  baseROR double,
  beta double,
  stockSymb varchar(5),
  sharePrice double,
  shareNum int);
  
create table if not exists PrivateInvAcc (
  pInvId int primary key not null auto_increment,
  assId int not null, foreign key (assId) references Asset(assId),
  quartDivi double,
  baseROR double,
  omega double,
  invValue double,
  percentStake double);
  
  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  assId int not null, foreign key (assId) references Asset(assId),
  portCode varchar(10),
  persCode varchar(10),
  managCode varchar(10),
  benefCode varchar(10),
  asset varchar(10),
  assInfo double);
