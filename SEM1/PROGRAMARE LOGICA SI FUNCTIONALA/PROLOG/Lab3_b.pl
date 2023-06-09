% Se da o lista eterogena, formata din numere intregi si liste de
% numere. Sa se sorteze fiecare sublista fara pastrarea dublurilor. De
% ex:[1,2,[4,1,4],3,6,[7,10,1,3,9],5,[1,1,1],7] =>
%    [1,2,[1,4],3,6,[1,3,7,9,10],5,[1],7]


% nrAparitii(L:Lista, E:Element, N:Intreg)
% Lista=Element*
% Modele de flux:(i, i, o), (i, i, i)
% L - lista in care numaram aparitiile elementului E
% E - elementul al carui aparitii le vom numara în lista L
% N - rezultatul, numarul de aparitii ale lui E in lista L

nrAparitii([],_,0):-!.
nrAparitii([H|T],E,N):-
	H=E,
	!,
	nrAparitii(T,E,N1),
	N is N1+1.
nrAparitii([H|T],E,N):-
	H\=E,
	nrAparitii(T,E,N).


% inserareSortat(E:Element, L:Lista, Rez:Lista)
% Lista=Element*
% Modele de flux: (i,i,o), (i,i,i)
% E - elementul pe care il inseram sortat in L
% L - lista in care dorim sa fie inserat elementul E
% Rez - lista in care este inserat corect elementul E in lista L

insereazaSortat(E,[],[E]):-!.
insereazaSortat(E,[H|T],[E|[H|T]]):- E<H,!.
insereazaSortat(E,[H|T],[H|Rez]):- insereazaSortat(E,T,Rez).


% elimina(L:Lista, C:Lista, R:Lista)
% Lista=Element*
% Modele de flux: (i,i,o),(i,i,i)
% L - lista din care eliminam elementele care apar de mai multe ori
% C - lista colectoare in care colectam elementele listei L in ordine
% crescatoare
% R -lista rezultat, obținută prin eliminarea elementelor care apar de
% mai multe ori

elimina([],Col,Col):-!.
elimina([H|T],Col,R):-
	nrAparitii(T,H,N),
	N>0,
	!,
	elimina(T,Col,R).
elimina([H|T],Col,R):-
	nrAparitii(T,H,N),
	N=0,
	insereazaSortat(H,Col,Rez),
	elimina(T,Rez,R).


% parcurgere(L:ListaEterogena, R:ListaEterogena)
% Modele de flux: (i,o), (i,i)
% ListaEterogena=(Lista/Element)*
% L - lista pe care o parcurg pentru a sorta sublistele sale fara
% dubluri
% R - lista rezultat in care sublistele listei L sunt sortate crescator
% fara dubluri

parcurgere([],[]):-!.
parcurgere([H|T],[H|R]):-
	not(is_list(H)),
	!,
	parcurgere(T,R).
parcurgere([H|T],[Rez|R]):-
	is_list(H),
	elimina(H,[],Rez),
	parcurgere(T,R).





