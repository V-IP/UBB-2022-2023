% Se dau Npuncte in plan (prin coordonatele lor). Se cere sa se
% determine toate submultimile de puncte coliniare.
% ex: A(1,3), B(2,2), C(3,1), D(4,4) => [[1,3],[2,2],[3,1],[4,4]]
% => (A,B,C)

% candidat(L:ListaEterogena, E:Lista, R:ListaEterogena)
% ListaEterogena = (Lista/Element)*
% Lista = Element*
% Modele de flux: (i,o,o) - nedeterminism
%		  (i,i,i) - determinist
% L - lista in care alegem un candidat E
% E - candidatul ales din lista L
% R - lista elementelor din lista L care apar dupa candidatul E

candidat([E|T],E,T).
candidat([_|T],E,R):- candidat(T,E,R).

% coliniar(P1:Lista, P2:Lista, P3:Lista)
% Lista = Element*
% Modele de flux: (i,i,i)
% P1,P2,P3 sunt cele 3 puncte la care dorim sa verificam coliniaritatea

coliniar([H11,_],[H11,_],[H11,_]):-!.
coliniar([_,H12],[_,H12],[_,H12]):-!.
coliniar([H11,H12],[H21,H22],[H31,H32]):-
	!,
	not(H22-H12 =:= 0),
	not(H21-H11 =:= 0),
	(H32-H12)/(H22-H12) =:= (H31-H11)/(H21-H11).

% coliniare(L:ListaEterogena, Col:ListaEterogena, Rez:ListaEterogena)
% ListaEterogena = (Lista/Element)*
% Lista = Element*
% Modele de flux: (i,i,o) - nedeterminism
%		  (i,i,i) - determinism
% L - lista din care alegem candidati
% Col - lista in care colectam rezultatele partiale
% Rez - lista rezultat care satisface conditiile

coliniare(_,Col,Col).
coliniare(L,[H1,H2|T],Rez):-
	candidat(L,E,R),
	coliniar(H1,H2,E),
	coliniare(R,[E|[H1|[H2|T]]],Rez).

% apel(L:ListaEterogena, R:ListaEterogena)
% ListaEterogena = (Lista/Element)*
% Lista = Element*
% Modele de flux: (i,o) - nedeterminism
%		  (i,i) - determinism
% L - lista din care generam submultimile de puncte coliniare
% R - lista rezultat in care avem pe rand toate submultimile de puncte
% coliniare generate prin backtracking

apel(L,R):-
	candidat(L,H1,R1),
	candidat(R1,H2,R2),
	coliniare(R2,[H1,H2],R).
