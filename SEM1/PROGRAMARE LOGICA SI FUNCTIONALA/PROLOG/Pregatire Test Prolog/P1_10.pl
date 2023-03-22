% Sa se intercaleze un element pe pozitia a n-a a unei liste.

interclasare([],_,_,[]):-!.
interclasare([H|T],E,1,Rez):-
	!,
	interclasare(T,E,0,Rez1),
	Rez=[E|[H|Rez1]].
interclasare([H|T],E,N,Rez):-
	N1 is N-1,
	interclasare(T,E,N1,Rez1),
	Rez=[H|Rez1].
