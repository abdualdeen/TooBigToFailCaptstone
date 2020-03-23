-- ==BONUS QUERIES== --
-- Run these after running the rest of the queries first.
-- State
insert into State (name, abbreviation) values ("Colorado", "CO");
insert into State (name, abbreviation) values ("Wyoming", "WY");

-- Country
insert into Country (name, abbreviation) values ("Thailand", "THI");
insert into Country (name, abbreviation) values ("China", "CHI");

-- Testing key constraints
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 5, 9999); -- tests for duplicate portfolios with assets
insert into EmailAddress (personId, emailAddress) values (9, "mkawarzimi@algebra.com"); -- tests for duplicate email address
insert into Asset (quartDivi, BaseROR, omega, investmentValue, label, assetType, assetCode) 
  values (95000.0, 0.50, 0.15, 999999.0, "Mass Effect Group", "P", "MEEG"); -- tests for duplicate assets. 
-- If running these returns errors then the constraint works.


-- ==Queries== --
-- 1

select p.personId, p.lastName, a.street, a.city, s.name, c.name, a.zip, e.emailAddress from Person p
  left join Address a on a.addressId = p.addressId
  left join EmailAddress e on e.personId = p.personId
  left join State s on s.stateId = a.stateId
  left join Country c on c.countryId = a.countryId;


-- 2
select emailAddress from EmailAddress where personId = (select personId from Person where firstName = "Light");
-- for the person with first name Light, display the email addresses

-- 3
insert into EmailAddress (personId, emailAddress) values ((select personId from Person where firstName = "Light"), "kira@deathnote.com");
-- add a new email address for the person with first name Light

-- 4
update EmailAddress set emailAddress = "cj@grovestreet.com" where emailAddressId  = 1;
-- change the email address for a specific email ID

-- 5 
delete from EmailAddress where personId = 8;
update Portfolio set benefId = null where portId = 3;
delete from Person where personId = 8;
-- removing a specific person

-- 6
insert into Address (street, city, stateId, countryId) values ("99 Algebra St", "Khwarazm", 7, 3);
insert into Person(alphaCode, lastName, firstName, addressId) values ("sqrt", "Al-Kawarzimi", "Muhammad", 9);
insert into EmailAddress (personId, emailAddress) values (9, "mkawarzimi@algebra.com");
-- adding a new person

-- 7 
select * from Asset a join PortfolioAsset pa on a.assetId = pa.assetId join Portfolio p on p.portId = pa.portId;
-- retrieving the assets from a specific portfolio

-- 8 
select * from Asset a join PortfolioAsset pa on a.assetId = pa.assetId join Portfolio p on p.portId = pa.portId join Person pe on pe.personId = p.ownerId;
-- retrieving the assets for a specific person

-- 9
insert into Asset (quartDivi, BaseROR, omega, investmentValue, label, assetType, assetCode) values (95000.0, 0.50, 0.15, 999999.0, "Mass Effect Group", "P", "MEEG");
-- adding a new asset

-- 10
insert into Portfolio (ownerId, managerId) values (9, 1);
-- adding a new portfolio

-- 11
insert into PortfolioAsset (portId, assetId, assetInfo) values (1, 5, 9999);
-- connect an asset with portfolio

-- 12
select pe.lastName, count(pa.assetId) as numberOfAssets from PortfolioAsset pa
  join Portfolio p on p.portId = pa.portId
  right join Person pe on pe.personId = p.ownerId group by pe.personId;
-- total amount of portfolios each person owns

-- 13
select pe.lastName, count(p.portId) as numberOfAssets from Portfolio p
  right join Person pe on pe.personId = p.managerId group by pe.personId;
-- total amount of portfolios each person manages 
  
-- 14
select p.portCode, sum(a.sharePrice*pa.assetInfo) as sumOfAssets from PortfolioAsset pa
  join Portfolio p on p.portId = pa.portId
  join Asset a on a.assetId = pa.assetId where a.assetType = "S" group by p.portId;
-- for the stocks in each portfolio find the total value
  
-- 15
select a.label, sum(pa.assetInfo) as sumOfStakePercentage from PortfolioAsset pa
  join Asset a on pa.assetId = a.assetId where (a.assetType = "P") group by a.assetId having sumOfStakePercentage>100; -- group by a.assetId ;
-- in private investments, if the stake percentage is greater than 100 display the list of investments that exceeds
  