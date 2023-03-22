% Sa se adauge dupa fiecare element dintr-o lista divizorii elementului.

adaugaSF([],E,[E]).
adaugaSF([H|T],E,[H|Rez]):- adaugaSF(T,E,Rez).

divizori(N,D,Col,Col):-
	N is D+1,!.
divizori(N,D,Col,Rez):-
	D<N,
	R is N mod D,
	R=0,
	!,
	Next is D+1,
	adaugaSF(Col,D,Col1),
	divizori(N,Next,Col1,Rez).
divizori(N,D,Col,Rez):-
	D<N,
	Next is D+1,
	!,
	divizori(N,Next,Col,Rez).

adaugare([],Col,Col):-!.
adaugare([H|T],Col,Rez):-
	adaugaSF(Col,H,Col1),
	divizori(H,2,Col1,Div),
	!,
	adaugare(T,Div,Rez).

adaugareP(L,Rez):- adaugare(L,[],Rez).
