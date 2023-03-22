% Sa se scrie un predicat care testeaza egalitatea a doua multimi, fara
% sa se faca apel la diferenta a doua multimi.

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

verifica([],[],_,_).
verifica([H1|T1],[H2|T2],L1,L2):-
	apartine(H1,L2),
	apartine(H2,L1),
	verifica(T1,T2,L1,L2).

egalitate(L1,L2):- verifica(L1,L2,L1,L2).
