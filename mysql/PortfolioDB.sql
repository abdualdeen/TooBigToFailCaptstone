-- use ahamad;
drop table if exists PortfolioAsset;
drop table if exists Portfolio;
drop table if exists EmailAddress;
drop table if exists Asset;
drop table if exists Person;
drop table if exists Address;
drop table if exists State;
drop table if exists Country;

-- For bonus points:
-- Added a country and state tables that are linked to the address table. 
-- Added constraint keys on assetCode in Asset, portId and assetId in PortfolioAsset, and the emailAddress and emailAddressId in EmailAddress.


-- The database consists of a State, Country, Address, Person, Portofolio, and PortolfioAssets.
-- The state table holds a stateId(primary key), the name of the state and an abbreviation. The abbreviation is set so that a person can reference 
-- what it is in case they are not sure.alter
create table if not exists State (
  stateId int primary key not null auto_increment,
  name varchar(100) not null,
  abbreviation varchar(25)
);

-- The country table has a country Id, the name of the country and the abbreviation. 
create table if not exists Country (
  countryId int primary key not null auto_increment,
  name varchar(100) not null,
  abbreviation varchar(25)
);


-- The address table stores the street, city, and zip. The stateId and countryId are foreign keys referencing the State and Country table respectively. 
create table if not exists Address (
  addressId int primary key not null auto_increment,
--   personId int not null, foreign key (personId) references Person(personId),
  street varchar(100),
  city varchar(100),
  stateId int, foreign key (stateId) references State(stateId),
  zip varchar(50),
  countryId int, foreign key (countryId) references Country(countryId)
  );
  
  
  -- The person table contains the identifiing alphaCode from the old system, the broker status, name, and an AddressId pointing to their Address in the 
  -- address table.
create table if not exists Person (
  personId int primary key not null auto_increment,
  alphaCode varchar(100) not null,
  brokerStat varchar(10),
  lastName varchar(100) not null,
  firstName varchar(100) not null,
  addressId int not null, foreign key (addressId) references Address(addressId)
  );
  
  
-- The EmailAddress table stores a personId as a foreign key and stores the email address(s) of that person. A constraint is set on emailAddress and personId to
-- prevent duplicats. 
create table if not exists EmailAddress (
  emailAddressId int primary key not null auto_increment,
  personId int not null, foreign key (personId) references Person(personId),
  emailAddress varchar(255),
  constraint uniquePair unique index (emailAddress, personId)
);


-- The asset table lists each asset type along with their respective values. The asset code is constrained as the same asset should not be repeated.
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
  assetCode varchar(100) not null, constraint unique (assetCode)
);  


-- The portfolio table stores the owner, manager and beneficiary as foreign keys that point to the Person table with person Id.
-- The owner and manager cannot be null as there must be an owner and a manager for a particular portfolio, but there does not 
-- need to always be a beneficiary.
create table if not exists Portfolio (
  portId int primary key not null auto_increment,
  ownerId int not null, foreign key (ownerId) references Person(personId),
  managerId int not null, foreign key (managerId) references Person(personId),
  benefId int, foreign key (benefId) references Person(personId),
  portCode varchar(100) not null
  );


-- The portfolio asset table is a association table that associates the relevant portfolio with its relevant assets.
-- It also stores the asset information asscoiated with the portfolio such as deposit balance, private investment value, and number of shares in stocks.
-- A constraint is present to make sure that a portfolio should not have a repeated asset. 
create table if not exists PortfolioAsset (
  portAssetId int primary key not null auto_increment,
  portId int not null, foreign key (portId) references Portfolio(portId),
  assetId int not null, foreign key (assetId) references Asset(assetId),
  assetInfo double not null,
  constraint uniquePair unique index (portId, assetId)
);


-- Inserting the test cases into the database. 
-- country data
insert into Country (name, abbreviation) values ("United States", "USA");
insert into Country (name, abbreviation) values ("Persia", "PRSA");
insert into Country (name, abbreviation) values ("Japan", "JPN");

-- state data
insert into State (name, abbreviation) values ("California", "CA");
insert into State (name, abbreviation) values ("New York", "NY");
insert into State (name, abbreviation) values ("Shinjiku", "SHJ");
insert into State (name, abbreviation) values ("Hawaii", "HI");
insert into State (name, abbreviation) values ("Nevada", "NV");
insert into State (name, abbreviation) values ("Nebraska", "NE");
insert into State (name, abbreviation) values ("Aral Sea", "AS");


-- address data
insert into Address (street, city, stateId, zip, countryId) values ("420 Grove St", "Los Santos", 1, "91111", 1);
insert into Address (street, city, stateId, zip, countryId) values ("100 Wall St", "New York", 2, "10005-0012", 1);
insert into Address (street, city, stateId, countryId) values ("69 Shinjiku St", "Tokyo", 3, 3);
insert into Address (street, city, stateId, zip, countryId) values ("892 Momona St", "Honolulu", 4, "96820", 1);
insert into Address (street, city, stateId, zip, countryId) values ("12 Pol St", "Los Angeles", 1, "12345", 1);
insert into Address (street, city, stateId, zip, countryId) values ("101 N 14th St", "Lincoln", 6, "68508", 1);
insert into Address (street, city, stateId, zip, countryId) values ("123 Alphabet Blvd", "Las Vegas", 5, "12345", 1);
insert into Address (street, city, stateId, countryId) values ("99 Algebra St", "Khwarazm", 7, 2);

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

-- Person 4 Assets
-- Asset is cluckin bell

-- Portfolios 
insert into Portfolio (ownerId, managerId, benefId, portCode) values (1, 2, 3,"PT001");
insert into Portfolio (ownerId, managerId, portCode) values (4, 5, "PF006");
insert into Portfolio (ownerId, managerId, benefId, portCode) values (6, 7, 8, "PF002");
insert into Portfolio (ownerId, managerId, benefId, portCode) values (6, 4, 1, "Px003");

-- (Person 1 port) Portfolio Asset association table with asset info. 
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 1, 35);
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 2, 26534.21);
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 3, 125);

-- (person 3 port) 
insert into PortfolioAsset (portId, assetId, assetInfo) values (3, 4, 25.5);

-- (person 4 port)
insert into PortfolioAsset (portID, assetId, assetInfo) values (4, 1, 75);

