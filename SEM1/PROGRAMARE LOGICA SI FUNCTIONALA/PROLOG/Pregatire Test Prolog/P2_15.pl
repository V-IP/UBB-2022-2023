%15.a)Sa se determine cea mai lunga secventa de numere pare consecutive dintr-o lista
%(daca sunt mai multe secvente delungime maxima, una dintre ele).

adauglafinal([],E,[E]).
adauglafinal([H|T],E,[H|R]):-
	adauglafinal(T,E,R).

size([],0).
size([_|T],R):-
	size(T,R1),
	R is R1 + 1.

secvPare([],L,C,L):-
	size(L,N),
	size(C,M),
	N >= M.
secvPare([],L,C,C):-
	size(L,N),
	size(C,M),
	N < M.
secvPare([H|T],L,C,R):-
	0 is H mod 2,
	adauglafinal(C,H,R1),
	secvPare(T,L,R1,R).
secvPare([_|T],L,C,R):-
	size(L,N),
	size(C,M),
	M >= N,
	secvPare(T,C,[],R).
secvPare([_|T],L,C,R):-
	size(L,N),
	size(C,M),
	M < N,
	secvPare(T,L,[],R).
