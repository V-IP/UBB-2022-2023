% Sa se scrie un predicat care substituie intr-o lista un element
% printr-o alta lista.


substitutie([],_,_,[]).
substitutie([H|T],E,L,Rez):-
	substitutie(T,E,L,Rez1),
	H=E,
	Rez=[L|Rez1].
substitutie([H|T],E,L,Rez):-
	substitutie(T,E,L,Rez1),
	not(H=E),
	Rez=[H|Rez1].
