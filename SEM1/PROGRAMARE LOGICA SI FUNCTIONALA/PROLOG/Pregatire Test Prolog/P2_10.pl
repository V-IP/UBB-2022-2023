% Se da o lista de numere intregi. Se cere sa se adauge in lista dupa
% 1-ul element, al 3-lea element, al 7-lea elemen, al 15-lea element … o
% valoare data e.

adaugaSF([],E,[E]):-!.
adaugaSF([H|T],R,[H|Rez]):- adaugaSF(T,R,Rez).

adauga([],1,_,E,Col,[E|Col]):-!.
adauga([],_,_,_,Col,Col):-!.
adauga([H|T],1,C,E,Col,Rez):-
	N is C*2,
	!,
	adaugaSF(Col,H,Col1),
	adaugaSF(Col1,E,Col2),
	adauga(T,N,N,E,Col2,Rez).
adauga([H|T],C,P,E,Col,Rez):-
	N is C-1,
	!,
	adaugaSF(Col,H,Col1),
	adauga(T,N,P,E,Col1,Rez).

adaugaP(L,E,R):-
	adauga(L,1,1,E,[],R).
