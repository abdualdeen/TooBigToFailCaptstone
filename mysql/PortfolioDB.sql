-- clearing out database from lab
  -- drop database ahamad;
  -- create database ahamad;
  
-- required dropping of tables:****uncomment before submission.*****
use ahamad;
drop table if exists Portfolio;
drop table if exists Address;
drop table if exists DepositAcc;
drop table if exists PrivateInvAcc;
drop table if exists StockAcc;
drop table if exists EmailAddress;
drop table if exists Asset;
drop table if exists Person;

-- creating 3 Tables; Person, Asset, and Portfolio.
create table if not exists EmailAddress (
emailAddressId int primary key not null auto_increment,
emailAddress varchar(255)
);

create table if not exists Person (
  personId int primary key not null auto_increment,
  alphaCode varchar(10),
  brokerStat varchar(10),
  lastName varchar(25),
  firstName varchar(25),
  -- addressId varchar(255) not null, foreign key (addressId) references Address(addressId),
  emailAddressId int, foreign key (emailAddressId) references EmailAddress(emailAddressId));
  
  
create table if not exists Address (
  addressId int primary key not null auto_increment,
  personId int not null, foreign key (personId) references Person(personId),
  street varchar(100),
  city varchar(50),
  state varchar(50),
  zip varchar(50),
  country varchar(25));
  
create table if not exists Asset (
assetId int not null primary key auto_increment
);  
  
create table if not exists DepositAcc (
  depositId int primary key not null auto_increment,
  assetId int not null, foreign key (assetId) references Asset(assetId),
  apr double not null,
  balance double not null);

create table if not exists StockAcc (
  stockId int primary key not null auto_increment,
  assetId int not null, foreign key (assetId) references Asset(assetId),
  label varchar(25),
  quartDivi double not null,
  baseROR double not null,
  beta double not null,
  stockSymb varchar(5),
  sharePrice double not null,
  shareNumbers int not null);
  
create table if not exists PrivateInvAcc (
  investmentId int primary key not null auto_increment,
  assetId int not null, foreign key (assetId) references Asset(assetId),
  label varchar(25),
  quartDivi double,
  baseROR double,
  omega double,
  investmentValue double,
  percentStake double);

  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  assetId int not null, foreign key (assetId) references Asset(assetId),
  personId int not null, foreign key (personId) references Person(personId),
  label varchar(25),
  portCode varchar(10) not null,
  persCode varchar(10) not null, -- foreign key (persCode) references Person(personId),
  managCode varchar(10), -- foreign key (managCode) references Person(personId),
  benefCode varchar(10), -- foreign key (benefCode) references Person(personId),
  assInfo double);
