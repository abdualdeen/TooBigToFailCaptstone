-- ==Queries for the assignment== --
-- Removing a person by personCode
delete from EmailAddress where personId = + personCode +;
update Portfolio set benefId = null where portId = + personCode +;
delete from Person where personId = + personCode +;

-- Removing all persons.
