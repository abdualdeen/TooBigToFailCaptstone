-- clearing out database from lab
  -- drop database ahamad;
  -- create database ahamad;
  
-- required dropping of tables:****uncomment before submission.*****
use ahamad;
drop table if exists Portfolio;
drop table if exists Address;
drop table if exists DepoAcc;
drop table if exists PrivateInvAcc;
drop table if exists StockAcc;
drop table if exists Person;

-- creating 3 Tables; Person, Asset, and Portfolio.
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
  
  
create table if not exists DepoAcc (
  assId int primary key not null auto_increment,
  apr double,
  balance double);

create table if not exists StockAcc (
  assId int primary key not null auto_increment,
  label varchar(25),
  quartDivi double,
  baseROR double,
  beta double,
  stockSymb varchar(5),
  sharePrice double,
  shareNum int);
  
create table if not exists PrivateInvAcc (
  assId int primary key not null auto_increment,
  label varchar(25),
  quartDivi double,
  baseROR double,
  omega double,
  invValue double,
  percentStake double);
  
  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  assId int not null, foreign key (assId) references PrivateInvAcc(assId), foreign key (assId) references StockAcc(assId), foreign key (assId) references DepoAcc(assId),
  label varchar(25),
  portCode varchar(10),
  persCode varchar(10), foreign key (persCode) references Person(personId),
  managCode varchar(10), foreign key (managCode) references Person(personId),
  benefCode varchar(10), foreign key (benefCode) references Person(personId),
  assInfo double);
