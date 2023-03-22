% Definiti un predicat care determina suma a doua numere scrise in
% reprezentare de lista.
% [[6,8,9],[1,2,0]] => [8,0,9]

inverseaza([],Col,Col).
inverseaza([H|T],Col,Rez):-
	inverseaza(T,[H|Col],Rez).

suma([],[],0,Col,Col):-!.
suma([],[],CF,Col,[CF|Col]):-!.
suma([],[H|T],CF,Col,Rez):-
	S is H+CF,
	U is S mod 10,
	C is S div 10,
	!,
	suma([],T,C,[U|Col],Rez).
suma([H|T],[],CF,Col,Rez):-
	S is H+CF,
	U is S mod 10,
	C is S div 10,
	!,
	suma([],T,C,[U|Col],Rez).
suma([H1|T1],[H2|T2],CF,Col,Rez):-
	S is H1+H2+CF,
	U is S mod 10,
	C is S div 10,
	!,
	suma(T1,T2,C,[U|Col],Rez).

sumaP(L1,L2,Rez):-
	inverseaza(L1,[],L11),
	inverseaza(L2,[],L22),
	suma(L11,L22,0,[],Rez).
