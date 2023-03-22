% Sa se scrie un predicat care transforma o lista intr-o multime, in
% ordinea primei aparitii.
% Exemplu: [1,2,3,1,2] e transformat in [1,2,3].

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

transf([],Col,Col):-!.
transf([H|T],Col,Rez):-
	not(apartine(H,Col)),
	!,
	transf(T,[H|Col],Rez).
transf([_|T],Col,Rez):- transf(T,Col,Rez).

inverseaza([],Col,Col).
inverseaza([H|T],Col,Rez):-
	inverseaza(T,[H|Col],Rez).

transforma(L,Rez):-
	transf(L,[],Rez1),
	inverseaza(Rez1,[],Rez).
