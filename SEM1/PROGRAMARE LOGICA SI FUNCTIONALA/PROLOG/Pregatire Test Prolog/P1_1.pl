% Sa se scrie un predicat care intoarce diferenta a doua multimi.

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

diferenta([],_,[]).
diferenta([H|T],L2,Rez):-
	not(apartine(H,L2)),
	!,
	diferenta(T,L2,Rez1),
	Rez = [H|Rez1].
diferenta([_|T],L2,Rez):-
	diferenta(T,L2,Rez).
