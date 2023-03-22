% Sa se scrie un predicat care testeaza daca o lista este multime.

nrAparitii([],_,0).
nrAparitii([H|T],E,N):-
	nrAparitii(T,E,N1),
	H=E,
	!,
	N is N1+1.
nrAparitii([_|T],E,N):- nrAparitii(T,E,N).

testare(_,[]):-!.
testare(L,[H|T]):-
	nrAparitii(L,H,N),
	N=1,
	testare(L,T).

test(L):- testare(L,L).
