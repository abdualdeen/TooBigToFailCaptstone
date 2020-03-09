-- use ahamad;
-- use hbalandran;

-- address
insert into Address(street, city, state, zip, country) values ("420 Grove St", "Los Santos", "CA", "91111", "USA");
insert into Address(street, city, state, zip, country) values ("100 Wall St", "New York", "NY", "10005-0012", "USA");
insert into Address(street, city, state, zip, country) values ("69 Shinjiku St", "Tokyo", "Shinjiku", "", "Japan");
insert into Address(street, city, state, zip, country) values ("892 Momona St", "Honolulu", "HI", "96820", "USA");
insert into Address(street, city, state, zip, country) values ("12 Pol St", "Los Angeles", "CA", "12345", "USA");
insert into Address(street, city, state, zip, country) values ("101 N 14th St", "Lincoln", "NE", "68508", "USA");
insert into Address(street, city, state, zip, country) values ("123 Alphabet Blvd", "Las Vegas", "NV", "12345", "USA");

-- person
insert into Person(alphaCode, lastName, firstName) values ("944c", "Johnson", "Carl");
insert into Person(alphaCode, brokerStat, lastName, firstName) values ("aef1", "E, sec001",  "White", "Walter");
insert into Person(alphaCode, lastName, firstName) values ("ma12", "Yagami", "Light");
insert into Person(alphaCode, lastName, firstName) values ("1svndr", "McLovin", "Fogell");
insert into Person(alphaCode, brokerStat, lastName, firstName) values ("231", "E, sec221", "Slater", "Officer");
insert into Person(alphaCode, lastName, firstName) values ("wrddoc", "John", "Jimmy");
insert into Person(alphaCode, lastName, firstName) values ("1svndr", "Jammy", "Jenny");

-- email
insert into EmailAddress (personId, emailAddress) values (1, "cj@cjcluckinbell.com");
insert into EmailAddress (personId, emailAddress) values (3, "yagamiL@tokyopolice.com");
insert into EmailAddress (personId, emailAddress) values (5, "12.O.Slater@gmail.com");
insert into EmailAddress (personId, emailAddress) values (5, "Donut.Slater@hotmail.com");
insert into EmailAddress (personId, emailAddress) values (6, "jimmy_jammy_johns@gmail.com");
insert into EmailAddress (personId, emailAddress) values (6, "jimmy.tomy.jammy@outlook.com");
insert into EmailAddress (personId, emailAddress) values (7, "better_sammies@hotmail.com");
insert into EmailAddress (personId, emailAddress) values (7, "Jenny_Jammy12@outlook.com");


-- Perosn 1 Assets
insert into Asset (quartDivi, BaseROR, omega, investmentValue, label) values (35000.0, 0.03, 0.32, 1212500.0, "Cluckin Bell restaurant chain");
insert into Asset (apr, label) values (0.25, "G Savings Account");
insert into Asset (quartDivi, BaseROR, beta, stockSymb, sharePrice, label) values (10, 0.025, .13, "LDEG", 106.13, "Linux Development Group");

-- Person 2 Assets
-- none.

-- Person 3 Assets
insert into Asset (quartDivi, BaseROR, beta, stockSymb, sharePrice, label) values (5.45, 0.031, .075, "KO", 41.08, "Coca-cola");

-- Portfolios 
insert into Portfolio (ownerId, managId, benefId) values (1, 2, 3);
insert into Portfolio (ownerId, managId) values (4, 5);
insert into Portfolio (ownerId, managId) values (6, 7);

-- (Person 1 port) Portfolio Asset association table with asset info. 
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 1, 35);
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 2, 26534.21);
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 3, 125);

-- (person 2 port) 
insert into PortfolioAsset (portId, assetId, assetInfo) values (2, 4, 25.5);

-- Queries:
