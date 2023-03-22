%Definiti un predicat care selecteaza al n-lea element al unei liste.

%pozitie(L:Lista,N:Intreg,E:Element)
%Lista=Element*
%Modele de flux: (i,i,o), (i,i,i)
%L - lista in care cautam al N-lea element
%N - numarul de ordine al elementului E in lista L
%E - elementul de pe pozitia N in lista L pe care dorim sa il returnam

pozitie([H|_],1,H):- !.
pozitie([_|T],N,E):-
	N1 is N-1,
	pozitie(T,N1,E).
