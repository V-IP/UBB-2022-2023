% Definiti un predicat care determina succesorul unui numar reprezentat
% cifra cu cifra intr-o lista. De ex: [1,9,3,5,9,9] => [1 9 3 6 0 0]

inverseaza([],Col,Col):-!.
inverseaza([H|T],Col,Rez):- inverseaza(T,[H|Col],Rez).

succesor([],0,Col,Col):-!.
succesor([],CF,Col,[CF|Col]):-!.
succesor([H|T],CF,Col,Rez):-
	S is H+CF,
	U is S mod 10,
	C is S div 10,
	succesor(T,C,[U|Col],Rez).

succesorP(L,Rez):-
	inverseaza(L,[],[H|T]),
	P is H+1,
	U is P mod 10,
	C is P div 10,
	succesor(T,C,[U],Rez).
