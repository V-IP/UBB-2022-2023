create or alter view ViewCoregrafi as 
select * from Coregrafi
go

create or alter view ViewCursuri as 
select * from Cursuri
go

create or alter view ViewCursuriCoregrafi as 
SELECT nume, prenume, denumire
FROM Cursuri 
INNER JOIN Profesie ON Profesie.id_curs = Cursuri.ID 
INNER JOIN Coregrafi ON Profesie.id_coregraf = Coregrafi.ID
go

create or alter view ViewCursuriCoregrafiGroupBy as
SELECT denumire AS [Denumire curs], COUNT(denumire) AS [Nr. coregrafi la curs]
FROM Cursuri 
INNER JOIN Profesie ON Profesie.id_curs = Cursuri.ID 
INNER JOIN Coregrafi ON Profesie.id_coregraf = Coregrafi.ID
GROUP BY denumire
go

create or alter view ViewCursuriLocatii as
SELECT denumire, judet, localitate, strada, numar
FROM Cursuri
INNER JOIN Rasfirare ON Rasfirare.id_curs = Cursuri.ID
INNER JOIN Locatii On Rasfirare.id_locatie = Locatii.ID
go

create or alter view ViewCursuriLocatiiGroupBy as
SELECT judet+' '+localitate+' '+strada AS [Locatie asignata], COUNT(judet+' '+localitate+' '+strada) as [Nr. coregrafi la locatie]
FROM Cursuri
INNER JOIN Rasfirare ON Rasfirare.id_curs = Cursuri.ID
INNER JOIN Locatii On Rasfirare.id_locatie = Locatii.ID
GROUP BY judet+' '+localitate+' '+strada
go


insert into Tests values ('TestCoregrafi'),('TestCursuriCoregrafi'),('TestCursuriLocatii')
go

insert into Tables(Name) values ('Cursuri'),('Coregrafi'),('Profesie'),('Locatii'),('Rasfirare')
go

insert into TestTables(TestID,TableID,NoOfRows,Position) values (1,2,10000,1),(2,1,10000,3),(2,2,10000,2),(2,3,100000,1),(3,1,10000,3),(3,4,10000,2),(3,5,100000,1)
go

insert into Views(Name) values ('ViewCoregrafi'),('ViewCursuri'),('ViewCursuriCoregrafi'),('ViewCursuriCoregrafiGroupBy'),('ViewCursuriLocatii'),('ViewCursuriLocatiiGroupBy')
go

insert into TestViews(TestID,ViewID) values (1,1),(2,1),(2,3),(2,4),(3,2),(3,5),(3,6)
go


create or alter procedure insert_Cursuri
	@NoOfRows int
as
begin
	WHILE @NoOfRows > 0
	BEGIN
		INSERT INTO Cursuri values(@NoOfRows, 'denumireTest', 10, 10, 1, 1)
		SET @NoOfRows = @NoOfRows - 1
	END
end
go

create or alter procedure insert_Coregrafi
	@NoOfRows int
as
begin
	WHILE @NoOfRows > 0
	BEGIN
		INSERT INTO Coregrafi VALUES (@NoOfRows,'numeTest','prenumeTest','email.test@test.com')
		SET @NoOfRows = @NoOfRows - 1
	END
end
go

create or alter procedure insert_Profesie
	@NoOfRows int
as
begin
	SET @NoOfRows = @NoOfRows/10
	WHILE @NoOfRows > 10
	BEGIN
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-1)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-2)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-3)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-4)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-5)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-6)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-7)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-8)
		INSERT INTO Profesie VALUES (@NoOfRows,@NoOfRows-9)
		SET @NoOfRows = @NoOfRows - 1
	END
end
go

create or alter procedure insert_Locatii
	@NoOfRows int
as
begin
	WHILE @NoOfRows > 0
	BEGIN
		INSERT INTO Locatii VALUES (@NoOfRows,'taraTest','judetTest','localitateTest','stradaTest',10,'A',10)
		SET @NoOfRows = @NoOfRows - 1
	END
end
go

create or alter procedure insert_Rasfirare
	@NoOfRows int
as
begin
	SET @NoOfRows = @NoOfRows/10
	WHILE @NoOfRows > 10
	BEGIN
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-1)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-2)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-3)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-4)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-5)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-6)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-7)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-8)
		INSERT INTO Rasfirare VALUES (@NoOfRows,@NoOfRows-9)
		SET @NoOfRows = @NoOfRows - 1
	END
end
go