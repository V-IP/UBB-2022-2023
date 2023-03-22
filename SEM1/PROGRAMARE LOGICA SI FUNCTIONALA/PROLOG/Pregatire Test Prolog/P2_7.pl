% Definiti un predicat care determina produsul unui numar reprezentat
% cifra cu cifra intr-o lista cu o anumita cifra. De ex: [1,9,3,5,9,9]*2
% => [3 8 7 1 9 8]

inverseaza([],Col,Col):-!.
inverseaza([H|T],Col,Rez):- inverseaza(T,[H|Col],Rez).

produs([],_,0,Col,Col):-!.
produs([],_,CF,Col,[CF|Col]):-!.
produs([H|T],E,CF,Col,Rez):-
	P is H*E+CF,
	U is P mod 10,
	C is P div 10,
	produs(T,E,C,[U|Col],Rez).

produsP(L,E,Rez):-
	inverseaza(L,[],L1),
	produs(L1,E,0,[],Rez).
