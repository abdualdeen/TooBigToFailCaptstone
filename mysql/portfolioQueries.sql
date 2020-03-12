-- use ahamad;
-- use hbalandran;

-- ==Queries== --
-- 1
select p.personId, p.lastName, a.address, e.emailAddress from Person p
  left join Address a on a.addressId = p.addressId
  left join EmailAddress e on e.personId = p.personId; -- join to address and email address

-- 2
select emailAddress from EmailAddress where personId = (select personId from Person where firstName = "Light");

-- 3
insert into EmailAddress (personId, emailAddress) values ((select personId from Person where firstName = "Light"), "kira@deathnote.com");

-- 4
update EmailAddress set emailAddress = "cj@grovestreet.com" where emailAddressId  = 1;

-- 5 
delete from EmailAddress where personId = 8;
update Portfolio set benefId = null where portId = 3;
delete from Person where personId = 8;

-- 6
insert into Address (street, city, state, country) values ("99 Algebra St", "Khwarazm", "Aral Sea", "Persia");
insert into Person(alphaCode, lastName, firstName, addressId) values ("sqrt", "Al-Kawarzimi", "Muhammad", 9);
insert into EmailAddress (personId, emailAddress) values (9, "mkawarzimi@algebra.com");

-- 7 
select * from Asset a join PortfolioAssets pa on a.assetId = pa.assetId join Portfolio p on p.portId = pa.portId;

-- 8 
select * from Asset a join PortfolioAssets pa on a.assetId = pa.assetId join Portfolio p on p.portId = pa.portId join Person pe on pe.personId = p.ownerId;

-- 9
insert into Asset (quartDivi, BaseROR, omega, investmentValue, label, assetType) values (95000.0, 0.50, 0.15, 999999.0, "Mass Effect Group", "P");

-- 10
insert into Portfolio (ownerId, managerId) values (9, 1);

-- 11
insert into PortfolioAssets (portId, assetId, assetInfo) values (1, 5, 9999);

-- 12
select pe.lastName, count(pa.assetId) as numberOfAssets from PortfolioAssets pa
  join Portfolio p on p.portId = pa.portId
  right join Person pe on pe.personId = p.ownerId group by pe.personId;


-- 13
select pe.lastName, count(p.portId) as numberOfAssets from Portfolio p
  right join Person pe on pe.personId = p.managerId group by pe.personId;
  
-- 14
select p.portCode, sum(a.sharePrice*pa.assetInfo) as sumOfAssets from PortfolioAssets pa
  join Portfolio p on p.portId = pa.portId
  join Asset a on a.assetId = pa.assetId where a.assetType = "S" group by p.portId;
  
-- 15
select a.label, sum(pa.assetInfo) as sumOfStakePercentage from PortfolioAssets pa
  join Asset a on pa.assetId = a.assetId where (a.assetType = "P") group by a.assetId having sumOfStakePercentage>100;
  
  -- country and state table. keys in address table. name and abbre.
  -- constraint on email address, portfolio, and portfolioasset
  -- test them yourselves
  -- add couple more test cases for assets and portfolios. 
  -- put bonus queries