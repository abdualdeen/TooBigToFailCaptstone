use ahamad;
SET SQL_SAFE_UPDATES = 0; 


select * from Asset;
select * from PortfolioAsset;
select * from Portfolio;

delete from PortfolioAsset where assetId = (select assetId from Asset where assetCode = 'AGTSAV'); -- (select portAssetId from PortfolioAsset where assetId = 1);
delete from Asset where assetId = (select assetId from Asset where assetCode = 'AGTSAV');

delete from PortfolioAsset;

select portId from Portfolio where portCode = 'PF002';
delete from PortfolioAsset where portId = 3;
delete from Portfolio where portId = 3;
delete from Asset;

delete from PortfolioAsset;
delete from Portfolio;

select * from Country;
select countryId from Country where abbreviation = 'sooeoe' or name = 'newcount';

insert into Country (name) values ('newcount');