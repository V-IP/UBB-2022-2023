% Sa se scrie un predicat care se va satisface daca o lista are numar
% par de elemente si va esua in caz contrar, fara sa se numere
% elementele listei.

lungime([]).
lungime([_,_|T]):-
	lungime(T).
