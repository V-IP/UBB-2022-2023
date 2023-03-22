% Sa se scrie un predicat care determina cel mai mic multiplu comun
% ale elementelor unei liste formate din numere intregi.
% Ex: [2,4,6,8] => 24

cmmmc2(E1,E2,Rez):-
	E1 < E2,
	!,
	E is E2-E1,
	cmmmc2(E1,E,Rez).
cmmmc2(E1,E2,Rez):-
	E1 > E2,
	!,
	E is E1-E2,
	cmmmc2(E,E2,Rez).
cmmmc2(E1,_,E1):-!.

mult([],R,R):-!.
mult([H|T],0,Rez):-
	cmmmc2(H,H,R),
	!,
	mult(T,R,Rez).
mult([H|T],D,Rez):-
	cmmmc2(H,D,R),
	C is H*D/R,
	mult(T,C,Rez).

cmmmc(L,Rez):- mult(L,0,Rez).
