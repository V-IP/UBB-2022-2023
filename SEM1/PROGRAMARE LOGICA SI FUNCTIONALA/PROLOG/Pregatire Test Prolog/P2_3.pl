% Sa se sorteze o lista cu eliminarea dublurilor. De ex: [4,2,6,2,3,4]
% => [2,3,4,6]

insereazaSortat(E,[],[E]):-!.
insereazaSortat(E,[H|T],[E|[H|T]]):-
	E<H,
	!.
insereazaSortat(E,[H|T],[H|Rez]):-
	insereazaSortat(E,T,Rez).

apartine(E,[E|_]):-!.
apartine(E,[_|T]):- apartine(E,T).

sorteaza([],Col,Col).
sorteaza([H|T],Col,Rez):-
	not(apartine(H,Col)),
	!,
	insereazaSortat(H,Col,Col2),
	sorteaza(T,Col2,Rez).
sorteaza([_|T],Col,Rez):-
	sorteaza(T,Col,Rez).


sortare(L,Rez):- sorteaza(L,[],Rez).
