% Sa se scrie un predicat care substituie intr-o lista un element prin
% altul.

substituire([],_,_,[]).
substituire([H|T],E,N,Rez):-
	substituire(T,E,N,Rez1),
	H=E,
	!,
	Rez=[N|Rez1].
substituire([H|T],E,N,[H|Rez]):- substituire(T,E,N,Rez).
