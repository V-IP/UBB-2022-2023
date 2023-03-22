% Intr-o lista L sa se inlocuiasca toate aparitiile unui element E cu
% elementele unei alte liste, L1. De ex: inloc([1,2,1,3,1,4],1,[10,11],X)
% va produce X=[10,11,2,10,11,3,10,11,4].

inversare([],Col,Col):-!.
inversare([H|T],Col,Rez):- inversare(T,[H|Col],Rez).

inloc([],Col,Col).
inloc([H|T],Col,Rez):- inloc(T,[H|Col],Rez).

inlocuire([],_,_,Col,Col).
inlocuire([H|T],E,L,Col,Rez):-
	H=E,
	!,
	inloc(L,Col,Col1),
	inlocuire(T,E,L,Col1,Rez).
inlocuire([H|T],E,L,Col,Rez):-
	inlocuire(T,E,L,[H|Col],Rez).

inlocuireP(L,E,L1,Rez):-
	inlocuire(L,E,L1,[],Rez1),
	inversare(Rez1,[],Rez).
