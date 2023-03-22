% Sa se scrie un predicat care intoarce reuniunea a doua multimi.
% ex: [2,3,4]U[1,2,3] => [1,2,3,4]

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

reunit([],L1,_,L1).
reunit([H|T],L1,L2,Rez):-
	reunit(T,L1,L2,Rez1),
	not(apartine(H,L1)),
	!,
	Rez=[H|Rez1].
reunit([_|T],L1,L2,Rez):- reunit(T,L1,L2,Rez).

reuniune(L1,L2,Rez):- reunit(L2,L1,L2,Rez).
