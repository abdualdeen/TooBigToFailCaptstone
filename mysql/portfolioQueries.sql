-- use ahamad;
use hbalandran;

-- address
insert into Address(street, city, state, zip, country) values ("123 Elmo Ln", "Barnes", "CA", "12345", "USA");
insert into Address(street, city, state, zip, country) values ("456 Cookie St", "Bird", "MI", "67890", "USA");
insert into Address(street, city, state, zip, country) values ("789  Big Bird Blvd", "Micky", "FL", "11234", "USA");

-- person
insert into Person(alphaCode, brokerStat, lastName, firstName) values ("123a", "E", "John", "Jimmy");
insert into Person(alphaCode, lastName, firstName) values ("456b", "Queen", "Dairy");
insert into Person(alphaCode, brokerStat, lastName, firstName) values ("789c", "J", "King", "Burger");

-- email
insert into EmailAddress (personId, emailAddress) values (1, "123aJimmyJohn@gmail.com");
insert into EmailAddress (personId, emailAddress) values (1, "Jimmy_Jammy_Johns@outlook.com");
insert into EmailAddress (personId, emailAddress) values (2, "Dairy.Queens.King456@hotmail.com");
