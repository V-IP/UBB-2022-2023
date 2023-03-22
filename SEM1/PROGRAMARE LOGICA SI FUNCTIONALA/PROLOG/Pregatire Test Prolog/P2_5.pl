% Sa se determine pozitiile elementului maxim dintr-o lista liniara. De
% ex: poz([10,14,12,13,14], L) va produce L = [2,5].

inversare([],Col,Col):-!.
inversare([H|T],Col,Rez):-
	inversare(T,[H|Col],Rez).

poz([],_,_,Col,Col).
poz([H|T],Max,N,Col,Rez):-
	N1 is N+1,
	H=Max,
	!,
	poz(T,Max,N1,[N1|Col],Rez).
poz([H|T],Max,N,_,Rez):-
	N1 is N+1,
	H>Max,
	!,
	poz(T,H,N1,[N1],Rez).
poz([_|T],Max,N,Col,Rez):-
	N1 is N+1,
	poz(T,Max,N1,Col,Rez).

pozP([H|T],R):-
	poz(T,H,1,[1],Rez),
	inversare(Rez,[],R).
