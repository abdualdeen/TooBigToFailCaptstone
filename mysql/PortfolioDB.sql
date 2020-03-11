-- use ahamad;
-- use hbalandran;
drop table if exists PortfolioAssets;
drop table if exists Portfolio;
drop table if exists EmailAddress;
drop table if exists Asset;
drop table if exists Person;
drop table if exists Address;
drop table if exists State;
drop table if exists Country;

-- creating 3 Tables; Person, Asset, and Portfolio.
create table if not exists State (
  stateId int primary key not null auto_increment,
  state varchar(100) not null,
  abbrevia varchar(25) not null
);

create table if not exists Country (
  countryId int primary key not null auto_increment,
  country varchar(100) not null,
  abbrevia varchar(25) not null
);

create table if not exists Address (
  addressId int primary key not null auto_increment,
--   personId int not null, foreign key (personId) references Person(personId),
  street varchar(100),
  city varchar(100),
  stateId int, foreign key (stateId) references State(stateId),
  zip varchar(50),
  countryId int, foreign key (countryId) references Country(countryId)
  );
  
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
  emailAddress varchar(255),
  constraint uniquePair unique index (emailAddressId, personId)
);
  
create table if not exists Asset (
  assetId int not null primary key auto_increment,
  assetType varchar(1) not null,
  apr double,
  label varchar(100) not null,
  quartDivi double,
  baseROR double,
  beta double,
  stockSymb varchar(5),
  sharePrice double,
  omega double,
  investmentValue double,
  assetCode varchar(100) not null
);  
  
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  ownerId int not null, foreign key (ownerId) references Person(personId),
  managerId int not null, foreign key (managerId) references Person(personId),
  benefId int, foreign key (benefId) references Person(personId),
  portCode varchar(100) not null
  );

create table if not exists PortfolioAssets (
  portAssetId int primary key not null auto_increment,
  portId int not null, foreign key (portId) references Portfolio(portId),
  assetId int not null, foreign key (assetId) references Asset(assetId),
  assetInfo double not null,
  constraint uniquePair unique index (portId, assetId)
);


-- address
insert into Address (street, city, state, zip, country) values ("420 Grove St", "Los Santos", "CA", "91111", "USA");
insert into Address (street, city, state, zip, country) values ("100 Wall St", "New York", "NY", "10005-0012", "USA");
insert into Address (street, city, state, zip, country) values ("69 Shinjiku St", "Tokyo", "Shinjiku", "", "Japan");
insert into Address (street, city, state, zip, country) values ("892 Momona St", "Honolulu", "HI", "96820", "USA");
insert into Address (street, city, state, zip, country) values ("12 Pol St", "Los Angeles", "CA", "12345", "USA");
insert into Address (street, city, state, zip, country) values ("101 N 14th St", "Lincoln", "NE", "68508", "USA");
insert into Address (street, city, state, zip, country) values ("123 Alphabet Blvd", "Las Vegas", "NV", "12345", "USA");
insert into Address (street, city, state, country) values ("99 Algebra St", "Khwarazm", "Aral Sea", "Persia");

-- person
insert into Person (alphaCode, lastName, firstName, addressId) values ("944c", "Johnson", "Carl", 1);
insert into Person (alphaCode, brokerStat, lastName, firstName, addressId) values ("aef1", "E, sec001",  "White", "Walter", 2);
insert into Person (alphaCode, lastName, firstName, addressId) values ("ma12", "Yagami", "Light", 3);
insert into Person (alphaCode, lastName, firstName, addressId) values ("1svndr", "McLovin", "Fogell", 4);
insert into Person (alphaCode, brokerStat, lastName, firstName, addressId) values ("231", "E, sec221", "Slater", "Officer", 5);
insert into Person (alphaCode, lastName, firstName, addressId) values ("wrddoc", "John", "Jimmy", 6);
insert into Person (alphaCode, lastName, firstName, addressId) values ("1svndr", "Jammy", "Jenny", 7);
insert into Person (alphaCode, lastName, firstName, addressId) values ("sqrt", "Al-Kawarzimi", "Muhammad", 8);

-- email
insert into EmailAddress (personId, emailAddress) values (1, "cj@cjcluckinbell.com");
insert into EmailAddress (personId, emailAddress) values (3, "yagamiL@tokyopolice.com");
insert into EmailAddress (personId, emailAddress) values (5, "12.O.Slater@gmail.com");
insert into EmailAddress (personId, emailAddress) values (5, "Donut.Slater@hotmail.com");
insert into EmailAddress (personId, emailAddress) values (6, "jimmy_jammy_johns@gmail.com");
insert into EmailAddress (personId, emailAddress) values (6, "jimmy.tomy.jammy@outlook.com");
insert into EmailAddress (personId, emailAddress) values (7, "better_sammies@hotmail.com");
insert into EmailAddress (personId, emailAddress) values (7, "Jenny_Jammy12@outlook.com");
insert into EmailAddress (personId, emailAddress) values (8, "mkawarzimi@algebra.com");


-- Perosn 1 Assets
insert into Asset (quartDivi, BaseROR, omega, investmentValue, label, assetType, assetCode) values (35000.0, 0.03, 0.32, 1212500.0, "Cluckin Bell restaurant chain", "P", "FOOD12");
insert into Asset (apr, label, assetType, assetCode) values (0.25, "G Savings Account", "D", "AGTSAV");
insert into Asset (quartDivi, BaseROR, beta, stockSymb, sharePrice, label, assetType, assetCode) values (10, 0.025, .13, "LDEG", 106.13, "Linux Development Group", "S", "B0oWM");

-- Person 2 Assets
-- none.

-- Person 3 Assets
insert into Asset (quartDivi, BaseROR, beta, stockSymb, sharePrice, label, assetType, assetCode) values (5.45, 0.031, .075, "KO", 41.08, "Coca-cola", "S", "321CC");

-- Portfolios 
insert into Portfolio (ownerId, managerId, benefId, portCode) values (1, 2, 3,"PT001");
insert into Portfolio (ownerId, managerId, portCode) values (4, 5, "PF006");
insert into Portfolio (ownerId, managerId, benefId, portCode) values (6, 7, 8, "PF002");

-- (Person 1 port) Portfolio Asset association table with asset info. 
insert into PortfolioAssets (portId, assetId, assetInfo) values (1, 1, 105);
insert into PortfolioAssets (portId, assetId, assetInfo) values (1, 2, 26534.21);
insert into PortfolioAssets (portId, assetId, assetInfo) values (1, 3, 125);

-- (person 3 port) 
insert into PortfolioAssets (portId, assetId, assetInfo) values (2, 4, 25.5);