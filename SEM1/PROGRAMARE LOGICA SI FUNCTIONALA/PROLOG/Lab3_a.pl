% Sa se sorteze o lista cu eliminarea dublurilor. De ex: [4,2,6,2,3,4]
% => [2,3,4,6]

% inserareSortat(E:Element, L:Lista, Rez:Lista)
% Lista=Element*
% Modele de flux: (i,i,o), (i,i,i)
% E - elementul pe care il inseram sortat in L
% L - lista in care dorim sa fie inserat elementul E
% Rez - lista in care este inserat corect elementul E in lista L

insereazaSortat(E,[],[E]):-!.
insereazaSortat(E,[H|T],[E|[H|T]]):- E<H,!.
insereazaSortat(E,[H|T],[H|Rez]):- insereazaSortat(E,T,Rez).


% nrAparitii(L:Lista, E:Element, N:Intreg)
% Lista=Element*
% Modele de flux: (i, i, o), (i, i, i)
% L - lista in care numaram aparitiile elementului E
% E - elementul al carui aparitii le vom numara în lista L
% N - rezultatul, numarul de aparitii ale lui E in lista L

nrAparitii([],_,0).
nrAparitii([H|T],E,N):-
	H=E,
	!,
	nrAparitii(T,E,N1),
	N is N1+1.
nrAparitii([H|T],E,N):-
	H\=E,
	nrAparitii(T,E,N).


% elimina(L:Lista,C:Lista,R:Lista)
% Lista=Element*
% Modele de flux: (i,i,o), (i,i,i)
% L - lista din care eliminam elementele care apar de mai multe ori
% C - lista colectoare in care colectam elementele listei L in ordine
% crescatoare
% R -lista rezultat, obținută prin eliminarea elementelor care apar de
% mai multe ori

elimina([],Col,Col).
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

eliminaMain(L,R):- elimina(L,[],R).








