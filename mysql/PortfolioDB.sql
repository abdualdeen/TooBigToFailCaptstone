-- create database ahamad;
use ahamad;
drop table if exists PortfolioAssets;
drop table if exists Portfolio;
drop table if exists Address;
drop table if exists EmailAddress;
drop table if exists Asset;
drop table if exists Person;
-- creating 3 Tables; Person, Asset, and Portfolio.

create table if not exists Person (
  personId int primary key not null auto_increment,
  alphaCode varchar(10) not null,
  brokerStat varchar(10),
  lastName varchar(100) not null,
  firstName varchar(100) not null,
  addressId int not null, foreign key (addressId) references Address(addressId)
  );
  
create table if not exists EmailAddress (
  emailAddressId int primary key not null auto_increment,
  personId int not null, foreign key (personId) references Person(personId),
  emailAddress varchar(255)
);
  
create table if not exists Address (
  addressId int primary key not null auto_increment,
--   personId int not null, foreign key (personId) references Person(personId),
  street varchar(100),
  city varchar(100),
  state varchar(100),
  zip varchar(50),
  country varchar(50)
  );
  
create table if not exists Asset (
  assetId int not null primary key auto_increment,
  apr double not null,
  label varchar(100) not null,
  quartDivi double,
  baseROR double,
  beta double,
  stockSymb varchar(5),
  sharePrice double,
  omega double,
  investmentValue double
);  
  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  ownerId int not null, foreign key (ownerId) references Person(personId),
  managerId int not null, foreign key (managerId) references Person(personId),
  benefId int not null, foreign key (benefId) references Person(personId)
  );

create table if not exists PortfolioAssets (
  portfolioAssetId int primary key not null auto_increment,
  portId int not null, foreign key (portId) references Portfolio(portId),
  assetId int not null, foreign key (assetId) references Asset(assetId)
);
