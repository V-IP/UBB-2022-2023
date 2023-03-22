% Sa se scrie un predicat care sterge toate aparitiile unui anumit atom
% dintr-o lista.

sterge([],_,[]).
sterge([H|T],E,Rez):-
	sterge(T,E,Rez),
	H=E,
	!.
sterge([H|T],E,Rez):-
	sterge(T,E,Rez1),
	Rez=[H|Rez1].
