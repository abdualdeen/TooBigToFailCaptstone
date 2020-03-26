-- ==Queries for the assignment== --
-- Removing a person by personCode
-- delete from EmailAddress where personId = + personCode +;
-- update Portfolio set benefId = null where portId = + personCode +;
-- delete from Person where personId = + personCode +;

-- Removing all persons.

-- adding email to existing person


-- Adding a new person

insert into Address (street, city, stateId, countryId) values ("99 Algebra St", "Khwarazm", 7, 3);
insert into Person(alphaCode, lastName, firstName, addressId) values ("sqrt", "Al-Kawarzimi", "Muhammad", 9);
insert into EmailAddress (personId, emailAddress) values (9, "mkawarzimi@algebra.com");
-- selecting person 
select * from Person where personId = 1;
-- all portfolios 
use ahamad;
select * from Portfolio;
-- selecting all address.
select * from Address;

-- for all assets.
select * from Asset;

-- for assets in portoflio
select * from Asset a join PortfolioAsset pa on a.assetId = pa.assetId where pa.portId = 1;