% Definiti un predicat care determina predecesorul unui numar
% reprezentat cifra cu cifra intr-o lista. De ex: [1,9,3,6,0,0] => [1 9
% 3 5 9 9]

inverseaza([],Col,Col):-!.
inverseaza([H|T],Col,Rez):- inverseaza(T,[H|Col],Rez).

predecesor([H],CF,Col,[H|Col]):-
	not(H-CF=:=0),
	!.
predecesor([H],CF,Col,Col):-
	H-CF=:=0,
	!.
predecesor([H|T],CF,Col,Rez):-
	H-CF<0,
	!,
	D is H+10-CF,
	predecesor(T,1,[D|Col],Rez).
predecesor([H|T],CF,Col,Rez):-
	!,
	D is H-CF,
	predecesor(T,0,[D|Col],Rez).

predecesorP(L,Rez):-
	inverseaza(L,[],L1),
	predecesor(L1,1,[],Rez).
