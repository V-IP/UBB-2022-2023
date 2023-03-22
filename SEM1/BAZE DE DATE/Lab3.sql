USE master
GO

USE CursuriDansCod
GO

DROP TABLE Versiune
GO
CREATE TABLE Versiune(
	 nr INT PRIMARY KEY DEFAULT 0);
GO

INSERT INTO Versiune VALUES (0)

DROP PROCEDURE P1
GO
CREATE PROCEDURE P1
AS
	IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='Grupe' AND COLUMN_NAME='varsta_minima' AND DATA_TYPE='SMALLINT')
	PRINT 'Nu s-a executat procedura. Acest camp are deja tipul coloanei SMALLINT!'
ELSE
BEGIN
	ALTER TABLE Grupe
	ALTER COLUMN varsta_minima SMALLINT
	PRINT 'Modificare reusita!'
END
GO

DROP PROCEDURE P1_R
GO
CREATE PROCEDURE P1_R
AS
	IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='Grupe' AND COLUMN_NAME='varsta_minima' AND DATA_TYPE='INT')
	PRINT 'Nu s-a executat procedura. Acest camp are deja tipul coloanei INT!'
ELSE
BEGIN
	ALTER TABLE Grupe
	ALTER COLUMN varsta_minima INT
	PRINT 'Modificare inversa reusita!'
END
GO

DROP PROCEDURE P2
GO
CREATE PROCEDURE P2
AS
	IF EXISTS(SELECT * FROM sys.default_constraints WHERE name='constangere_tara_RO')
	PRINT 'Exista deja o constrangere implicita cu acest nume!'
ELSE
BEGIN
	ALTER TABLE Locatii
	ADD CONSTRAINT constangere_tara_RO DEFAULT 'Romania' FOR tara
	PRINT 'Constrangere creata!'
END
GO

DROP PROCEDURE P2_R
GO
CREATE PROCEDURE P2_R
AS
	IF NOT EXISTS(SELECT * FROM sys.default_constraints WHERE name='constangere_tara_RO')
	PRINT 'Nu exista o constrangere implicita cu acest nume!'
ELSE
BEGIN
	ALTER TABLE Locatii
	DROP CONSTRAINT constangere_tara_RO
	PRINT 'Constrangere stearsa cu succes!'
END
GO

DROP PROCEDURE P3
GO
CREATE PROCEDURE P3
AS
	IF NOT EXISTS(SELECT * FROM sys.tables WHERE name='Premii')
		BEGIN
			CREATE TABLE Premii(
				ID INT PRIMARY KEY,
				id_curs INT,
				loc SMALLINT NOT NULL);
			PRINT 'Tabela creata!'
		END
	ELSE
		PRINT 'Exista deja tabela "Premii"!'
GO

DROP PROCEDURE P3_R
GO
CREATE PROCEDURE P3_R
AS
	IF NOT EXISTS(SELECT * FROM sys.tables WHERE name='Premii')
	PRINT 'Nu exista o tabela "Premii"!'
ELSE
BEGIN
	DROP TABLE Premii
	PRINT 'Tabela stearsa cu succes!'
END
GO

DROP PROCEDURE P4
GO
CREATE PROCEDURE P4
AS
	IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='Coregrafi' AND COLUMN_NAME='telefon')
	PRINT 'Exista deja aceasta coloana!'
ELSE
BEGIN
	ALTER TABLE Coregrafi
	ADD telefon CHAR(10)
	PRINT 'Coloana adaugata cu succes!'
END
GO

DROP PROCEDURE P4_R
GO
CREATE PROCEDURE P4_R
AS
	IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='Coregrafi' AND COLUMN_NAME='telefon')
	PRINT 'Nu exista aceasta coloana!'
ELSE
BEGIN
	ALTER TABLE Coregrafi
	DROP COLUMN telefon
	PRINT 'Coloana stearsa cu succes!'
END
GO

DROP PROCEDURE P5
GO
CREATE PROCEDURE P5
AS
	IF EXISTS(SELECT * FROM sys.foreign_keys WHERE NAME='FK_Premii_Cursuri')
			PRINT 'Exista deja aceasta constrangere!'
ELSE
BEGIN
	ALTER TABLE Premii
	ADD CONSTRAINT FK_Premii_Cursuri FOREIGN KEY (id_curs) REFERENCES Cursuri(ID)
	PRINT 'Constrangere adaugata cu succes!'
END
GO

DROP PROCEDURE P5_R
GO
CREATE PROCEDURE P5_R
AS
	IF NOT EXISTS(SELECT * FROM sys.foreign_keys WHERE NAME='FK_Premii_Cursuri')
		PRINT 'Nu exista aceasta constrangere!'
ELSE
BEGIN
	ALTER TABLE Premii
	DROP CONSTRAINT FK_Premii_Cursuri
	PRINT 'Constrangere stearsa cu succes!'
END
GO

DROP PROCEDURE NR_PROCEDURA
GO
CREATE PROCEDURE NR_PROCEDURA
	@nr INT
AS
BEGIN
	DECLARE @curent INT
	SET @curent = (SELECT nr FROM Versiune);

	IF @nr < 0 OR @nr > 5
		PRINT 'Versiune inexistenta!'
	ELSE

	IF @curent = @NR 
		PRINT 'Tabela este deja in versiunea ceruta!'
	ELSE

	IF @curent < @nr
		WHILE @curent < @nr
		BEGIN
			IF @curent = 4 EXEC sp_executesql P5
			IF @curent = 3 EXEC sp_executesql P4
			IF @curent = 2 EXEC sp_executesql P3
			IF @curent = 1 EXEC sp_executesql P2
			IF @curent = 0 EXEC sp_executesql P1
			SET @curent = @curent + 1
		END
	ELSE
		WHILE @curent > @nr
		BEGIN
			IF @curent = 1 EXEC sp_executesql P1_R
			IF @curent = 2 EXEC sp_executesql P2_R
			IF @curent = 3 EXEC sp_executesql P3_R
			IF @curent = 4 EXEC sp_executesql P4_R
			IF @curent = 5 EXEC sp_executesql P5_R
			SET @curent = @curent - 1
		END
	UPDATE Versiune SET nr = @nr
END
GO

DROP PROCEDURE NR_PROCEDURA_CONCATENARE
GO
CREATE PROCEDURE NR_PROCEDURA_CONCATENARE
	@nr INT
AS
BEGIN
	DECLARE @curent INT
	DECLARE @proc NVARCHAR(10)
	SET @curent = (SELECT nr FROM Versiune);

	IF @nr < 0 OR @nr > 5
		PRINT 'Versiune inexistenta!'
	ELSE

	IF @curent = @nr
		PRINT 'Tabela este deja in versiunea ceruta!'
	ELSE

	BEGIN
		IF @curent < @nr
			BEGIN
				WHILE @curent < @nr
				BEGIN
					SET @curent = @curent + 1
					SET @proc = 'P' + CONVERT(VARCHAR(5), @curent)
					EXEC sp_executesql @proc
				END
			END
		ELSE
			WHILE @curent > @nr
			BEGIN
				SET @proc = 'P' + CONVERT(VARCHAR(5), @curent) + '_R'
				EXEC sp_executesql @proc
				SET @curent = @curent - 1
			END
	END

	UPDATE Versiune SET nr = @nr
END
GO