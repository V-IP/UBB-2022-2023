%Sa se scrie un predicat care testeaza egalitatea a doua multimi, fara sa se faca apel la diferenta a doua multimi.

%apartine(E:Element,L:Lista)
%Lista=Element*
%Modele de flux: (i,i)
%E - elementul pe care il cautam in lista L
%L - lista in care verificam daca apartine si E

apartine(E,[E|_]):- !.
apartine(E,[_|T]):-
	 apartine(E,T).

%apel(L1:Lista,L2:Lista)
%Lista=Element*
%Modele de flux: (i,i)
%L1 - lista ale carei elemente le cautam in lista L2
%L2 - lista in care verificam daca apartin elementele listei L1

apel([],_):- !.
apel([H|T],L):-
	apartine(H,L),
	apel(T,L).

%egalitate(L1:Lista,L2:Lista)
%Lista=Element*
%Modele de flux: (i,i)
%L1 - prima lista la care ii verificam egalitatea cu L2
%L2 - a doua lista la care ii verificam egalitatea cu L1

egalitate(L1,L2):-
	apel(L1,L2),
	apel(L2,L1).

% SAU
% apel([],[],_,_):- !.
% apel([H1|T1],[H2|T2],L1,L2):-
%	apartine(H1,L2),
%	apartine(H2,L1),
%	apel(T1,T2,L1,L2).
% egalitate(L1,L2):- apel(L1,L2,L1,L2).
