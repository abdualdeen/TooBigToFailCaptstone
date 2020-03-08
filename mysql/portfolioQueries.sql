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
insert into EmailAddress (personId, emailAddress) values (5, "12.O.Slater@gmail.com, Donut.Slater@hotmail.com");
insert into EmailAddress (personId, emailAddress) values (6, "jimmy_jammy_johns@gmail.com, jimmy.tomy.jammy@outlook.com");
insert into EmailAddress (personId, emailAddress) values (7, "better_sammies@hotmail.com, Jenny_Jammy12@outlook.com");

