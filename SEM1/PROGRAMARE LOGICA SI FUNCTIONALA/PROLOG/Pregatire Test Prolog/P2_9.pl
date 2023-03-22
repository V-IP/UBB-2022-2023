% Dandu-se o lista liniara numerica, sa se stearga toate secventele de
% valori consecutive. Ex: sterg([1, 2, 4, 6, 7, 8, 10], L) va produce
% L=[4, 10].

adaugaSF([],R,[R]).
adaugaSF([H|T],R,[H|Rez]):- adaugaSF(T,R,Rez).

sterg([],Col,Col,_):-!.
sterg([H1,H2|T],Col,Rez,_):-
	H1=:=H2-1,
	!,
	sterg([H2|T],Col,Rez,H1).
sterg([H|T],Col,Rez,L):-
	H=:=L+1,
	!,
	sterg(T,Col,Rez,H).
sterg([H|T],Col,Rez,_):-
	adaugaSF(Col,H,Col1),
	sterg(T,Col1,Rez,H).

stergP(L,R):- sterg(L,[],R,-1).

