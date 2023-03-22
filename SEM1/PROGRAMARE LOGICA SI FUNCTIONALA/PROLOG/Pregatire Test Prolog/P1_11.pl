% Sa se scrie un predicat care sa testeze daca o lista formata din
% numere intregi are aspect de "vale"(o multime se spune ca are aspect
% de "vale" daca elementele descresc pana la un moment dat, apoi cresc.
% De ex. [10,8,6,9,11,13]

vale([_],0):-!.
vale([H1,H2|T],1):-
	H1>H2,
	!,
	vale([H2|T],1).
vale([H1,H2|T],1):-
	H1<H2,
	!,
	vale([H2|T],0).
vale([H1,H2|T],0):-
	H1<H2,
	!,
	vale([H2|T],0).

valeP([H1,H2|T]):-
	H1>H2,
	vale([H2|T],1).
