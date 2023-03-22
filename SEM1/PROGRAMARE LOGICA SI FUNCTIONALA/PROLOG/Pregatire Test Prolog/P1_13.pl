% Sa se scrie un predicat care transforma o lista intr-o multime, in
% ordinea ultimei aparitii. Exemplu: [1,2,3,1,2] e transformat in
% [3,1,2].

nrAparitii([],_,0):-!.
nrAparitii([H|T],E,N):-
	nrAparitii(T,E,N1),
	H=E,
	!,
	N is N1+1.
nrAparitii([_|T],E,N):- nrAparitii(T,E,N).

transforma([],[]).
transforma([H|T],Rez):-
	transforma(T,Rez1),
	nrAparitii(T,H,N),
	N=0,
	!,
	Rez=[H|Rez1].
transforma([_|T],Rez):- transforma(T,Rez).
