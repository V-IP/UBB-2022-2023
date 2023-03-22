% Sa se interclaseze fara pastrarea dublurilor doua liste sortate.

inversare([],Col,Col).
inversare([H|T],Col,Rez):-
	inversare(T,[H|Col],Rez).

interclasare([],[],Col,Col):-!.
interclasare([],[H|T],Col,Rez):-
	!,
	interclasare([],T,[H|Col],Rez).
interclasare([H|T],[],Col,Rez):-
	!,
	interclasare(T,[],[H|Col],Rez).
interclasare([H1|T1],[H2|T2],Col,Rez):-
	H1<H2,
	!,
	interclasare(T1,[H2|T2],[H1|Col],Rez).
interclasare([H1|T1],[H2|T2],Col,Rez):-
	H1>H2,
	!,
	interclasare([H1|T1],T2,[H2|Col],Rez).
interclasare([H1|T1],[H2|T2],Col,Rez):-
	H1=H2,
	!,
	interclasare(T1,T2,[H2|Col],Rez).

interclasareP(L1,L2,Rez):-
	interclasare(L1,L2,[],Rez1),
	inversare(Rez1,[],Rez).
