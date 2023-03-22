% adaugaSF(L:Lista,E:Element,R:Rezultat)
% Lista=Element*
% Modele de flux: (i,i,o), (i,o,i), (o,i,i), (i,i,i)-toate deterministe
% L - lista in care dorim sa adaugam elementul E la final
% E - elementul pe care dorim sa il inseram la final in lista L
% R - lista rezultat in care elementul E se afla pe ultima pozitie in L
adaugaSF([],E,[E]).
adaugaSF([H|T],R,[H|Rez]):- adaugaSF(T,R,Rez).

% sterge(L:Lista,D:Element,Col:Lista,Rez:Lista,Last:Element)
% Modele de flux: (i,i,i,o,i), (i,i,i,i,i)
% L - lista in care dorim sa eliminam toate secventele de minim 2
% elemente formate din multipli ai lui D
% D - elementul introdus de utilizator dupa care dorim sa eliminam
% multiplii sai din lista L
% Col - lista colectoare in care adunam rezultatul partial (adica fara
% secventele de multipli ai lui D)
% Rez - lista rezultat in care avem lista fara secventele mentionate
% anterior
% Last - ultimul element din secventa noastra pe care dorim sa o stergem
% (avem nevoie de el mai ales cand stergem ultimul element din secventa)
sterge([],_,Col,Col,_).
sterge([H1,H2|T],D,Col,Rez,_):-
	H1 mod D =:= 0,
	H2 mod D =:= 0,
	!,
	sterge([H2|T],D,Col,Rez,H1).
sterge([H|T],D,Col,Rez,L):-
	L mod D =:= 0,
	H mod D =:= 0,
	!,
	sterge(T,D,Col,Rez,H).
sterge([H|T],D,Col,Rez,_):-
	adaugaSF(Col,H,Col1),
	sterge(T,D,Col1,Rez,H).

% sterge(L:Lista,D:Element,Rez:Lista)
% Modele de flux: (i,i,o), (i,i,i) - deterministe
% L - lista in care dorim sa eliminam toate secventele de minim 2
% elemente formate din multipli ai lui D
% D - elementul introdus de utilizator dupa care dorim sa eliminam
% multiplii sai din lista L
% Rez - lista rezultat in care avem lista fara secventele mentionate
% anterior
stergeP(L,D,Rez):-
	sterge(L,D,[],Rez,1).
