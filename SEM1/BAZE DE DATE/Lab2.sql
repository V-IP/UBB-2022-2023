INSERT INTO Categorii(ID,denumire) VALUES (1,'Hip-Hop')
INSERT INTO Coregrafi(ID,nume,prenume,email) VALUES (1,'Muntean','Liliana','muntean.liliana@gmail.com')
INSERT INTO Coregrafi(ID,nume,prenume,email) VALUES (2,'Bonciu','Marius','bonciu.marius@gmail.com')
INSERT INTO Locatii(ID,tara,judet,localitate,strada,numar) VALUES (1,'Romania','Sibiu','Medias','Vasile Parvan',34)
INSERT INTO Locatii(ID,tara,judet,localitate,strada,numar) VALUES (2,'Romania','Maramures','Borsa','Plopilor',103)
INSERT INTO Pachete(ID,discount) VALUES (1,20)
INSERT INTO Cursuri(ID,denumire,durata,pret,id_categorie,id_pachet) VALUES (1,'C:HIP-HOP',120,100,1,1)
INSERT INTO Cursuri(ID,denumire,durata,pret) VALUES (2,'C:Tango',45,50)
INSERT INTO Grupe(ID,nivel_minim,varsta_minima,id_curs) VALUES (1,3,10,1)
INSERT INTO Elevi(ID,nume,prenume,data_nasterii,telefon,id_grupa) VALUES (1,'Vincze','Iulia','05.05.2003',0753346207,1)
INSERT INTO Elevi(ID,nume,prenume,data_nasterii,telefon,id_grupa) VALUES (2,'Rus','Ana','10.05.2001',0757376189,1)
INSERT INTO Elevi(ID,nume,prenume,data_nasterii,telefon) VALUES (3,'Bret','Ioana','02.13.2002',0770770771)
INSERT INTO Profesie(id_curs,id_coregraf) VALUES (1,1)
INSERT INTO Progrese(id_elev,id_curs,nivel) VALUES (1,1,8)
INSERT INTO Progrese(id_elev,id_curs,nivel) VALUES (1,2,6)
INSERT INTO Progrese(id_elev,id_curs,nivel) VALUES (2,1,6)
INSERT INTO Progrese(id_elev,id_curs,nivel) VALUES (3,2,10)
INSERT INTO Rasfirare(id_curs,id_locatie) VALUES (1,1)
INSERT INTO Rasfirare(id_curs,id_locatie) VALUES (2,2)
INSERT INTO Rasfirare(id_curs,id_locatie) VALUES (2,1)



--1
--cati elevi sunt la cursurile cu durata > 60 grupati dupa nivel cu nivel > 5
SELECT COUNT(Elevi.prenume) AS Numar_elevi, Progrese.nivel
FROM Elevi
INNER JOIN Progrese ON Elevi.ID=Progrese.id_elev
INNER JOIN Cursuri ON Cursuri.ID=Progrese.id_curs
WHERE Cursuri.durata > 10
GROUP BY Progrese.nivel
HAVING Progrese.nivel > 5
--2
--judetele din Romania in care sunt mai mult de 2 cursuri
SELECT Locatii.judet
FROM Cursuri
INNER JOIN Rasfirari ON Cursuri.ID=Rasfirari.id_curs
INNER JOIN Locatii ON Locatii.ID=Rasfirari.id_locatie
WHERE Locatii.tara='Romania'
GROUP BY Locatii.judet
HAVING COUNT(Locatii.judet) > 1
--3
--profesorii, elevii si cursurile aferente care au - in denumire
SELECT DISTINCT Coregrafi.nume AS Nume_coregraf, Elevi.prenume AS Prenume_elev, Cursuri.denumire AS Denumire_curs
FROM Coregrafi
INNER JOIN Profesii ON Profesii.id_coregraf=Coregrafi.ID
INNER JOIN Cursuri ON Cursuri.ID=Profesii.id_curs
INNER JOIN Progrese ON Cursuri.ID=Progrese.id_curs
INNER JOIN Elevi ON Elevi.ID=Progrese.id_elev
WHERE Cursuri.denumire LIKE '%-%'
--4
--locatiile cursurilor care fac parte dintr-un pachet cu discount de cel putin 10%
SELECT Locatii.localitate, Pachete.discount
FROM Locatii
INNER JOIN Rasfirari ON Rasfirari.id_locatie=Locatii.ID
INNER JOIN Cursuri ON Rasfirari.id_curs=Cursuri.ID
INNER JOIN Pachete ON Pachete.ID=Cursuri.id_pachet
WHERE Pachete.discount > 10
--5
--categoriile grupate dupa nr de profesori having nr = 1
SELECT Categorii.denumire
FROM Categorii
INNER JOIN Cursuri ON Cursuri.id_categorie=Categorii.ID
INNER JOIN Profesii ON Profesii.id_curs=Cursuri.ID
INNER JOIN Coregrafi ON Coregrafi.ID=Profesii.id_coregraf
GROUP BY Coregrafi.ID, Categorii.denumire
HAVING COUNT(Coregrafi.ID) = 1
--6
--fiecare profesor care are +1 elevi la cursuri
SELECT Coregrafi.nume, COUNT(Elevi.ID) AS Numar_elevi
FROM Coregrafi
INNER JOIN Profesii ON Profesii.id_coregraf=Coregrafi.ID
INNER JOIN Cursuri ON Cursuri.ID=Profesii.id_curs 
INNER JOIN Progrese ON Progrese.id_curs=Cursuri.ID
INNER JOIN Elevi ON Elevi.ID=Progrese.id_elev
GROUP BY Coregrafi.ID, Coregrafi.nume
HAVING COUNT(Elevi.ID) > 1
--7
--cursurile la care avem elevi fara grupa
SELECT DISTINCT Cursuri.denumire
FROM Cursuri
INNER JOIN Progrese ON Progrese.id_curs=Cursuri.ID
INNER JOIN Elevi ON Elevi.ID=Progrese.id_elev
WHERE Cursuri.denumire NOT IN (SELECT Cursuri.denumire
							   FROM Cursuri 
							   INNER JOIN Grupe ON Grupe.id_curs=Cursuri.ID)
--8
--toti elevii vare fac parte dintr-o grupa
SELECT nume, prenume, id_grupa
FROM Elevi
WHERE id_grupa IS NOT NULL
--9
--toate cursurile care nu fac parte din niciun pachet
SELECT denumire
FROM Cursuri
WHERE id_pachet IS NULL
--10
--toate localitatiile existente din romania care au A in nume
SELECT judet, localitate
FROM Locatii
WHERE tara='Romania' and localitate LIKE '%a%'