% Sa se scrie un predicat care intoarce intersectia a doua multimi.

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

intersectie([],_,[]).
intersectie([H|T],L2,Rez):-
	intersectie(T,L2,Rez1),
	apartine(H,L2),
	!,
	Rez=[H|Rez1].
intersectie([_|T],L2,Rez):- intersectie(T,L2,Rez).
