% Sa se scrie un predicat care elimina dintr-o lista toate elementele
% care se repeta (ex: l=[1,2,1,4,1,3,4] => l=[2,3])

nrAparitii([],_,0).
nrAparitii([H|T],E,N):-
	nrAparitii(T,E,N1),
	H=E,
	!,
	N is N1+1.
nrAparitii([_|T],E,N):-
	nrAparitii(T,E,N).

elimin([],_,[]).
elimin([H|T],L,Rez):-
	elimin(T,L,Rez1),
	nrAparitii(L,H,1),
	!,
	Rez=[H|Rez1].
elimin([_|T],L,Rez):- elimin(T,L,Rez).

elimina(L,Rez):- elimin(L,L,Rez).
