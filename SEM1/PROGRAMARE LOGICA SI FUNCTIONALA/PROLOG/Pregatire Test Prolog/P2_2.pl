% Sa se sorteze o lista cu pastrarea dublurilor. De ex: [4,2,6,2,3,4] =>
% [2,2,3,4,4,6]

inserareSortat(E,[],[E]):-!.
inserareSortat(E,[H|T],[E|[H|T]]):-
	E<H,
	!.
inserareSortat(E,[H|T],[H|Rez]):-
	inserareSortat(E,T,Rez).

sortare([],Col,Col):-!.
sortare([H|T],Col,Rez):-
	inserareSortat(H,Col,Col2),
	sortare(T,Col2,Rez).

sortareP(L,Rez):- sortare(L,[],Rez).
