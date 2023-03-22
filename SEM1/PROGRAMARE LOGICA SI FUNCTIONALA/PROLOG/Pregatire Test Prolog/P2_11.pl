% Se da o lista de numere intregi. Se cere sa se scrie de 2 ori in lista
% fiecare numar prim.

prim(N,N):-!.
prim(N,D):-
	D<N,
	R is N mod D,
	not(R=0),
	Next is D+1,
	prim(N,Next).

dublare([],[]).
dublare([H|T],Rez):-
	prim(H,2),
	!,
	dublare(T,Rez1),
	Rez=[H|[H|Rez1]].
dublare([H|T],Rez):-
	dublare(T,Rez1),
	Rez=[H|Rez1].
