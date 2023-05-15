La homework:
- atat in gameclient cat si in clientthread(in server), programul ruleaza diferit atunci cand clientul se afla intr-un joc;
- in joc, flowul e cam asta:
  - serverul trimite un mesaj legat de starea jocului:  ended sau ongoing
  - client threads citesc dintr-un fisier starea jocului si afiseaza pe ecranul clientilor cum arata matricea
  - in functie de acest mesaj, jocul continua sau nu
  - serverul afiseaza fiecarui jucator pe ecran, a carui tura este(adica afiseaza your_turn daca el tb sa mute, sau opponent's turn daca adversarul muta)
  - daca sunt in clientthread-ul cu id x, si e tura jucatorului de pe acest clientthread, atunci in server citesc si ce mutari face jucatorul
  - daca nu e tura jucatorului de pe clientthread-ul cu id x, atunci in server nu citesc comenzi de la client, ci astept un update de la game(astept sa se modifice
  nr de miscari facute, semn ca adversarul a terminat mutarea)
  - cum arata o mutare: jucatorul specifica coordonatele, iar in clasa Game, se modifica gameboard-ul, se scrie in fisier cum arata board-ul in acel moment si se verifica
  daca e vreun castigator
  
  - pt a incepe un joc : un jucator da comanda NEW_GAME; in server se creeaza un joc nou, dar care nu incepe inca, deoarece nu are doi jucatori
  - cand un jucator da JOIN_GAME si apoi introduce id-ul unui joc valid, se da update la nr de jucatori din acel joc iar jocul incepe, flowul ce urmeaza fiind cel de mai sus
  - nu am fct chestia aia cu timer dar am facut-o la laboratorul cu roboti si ca idee ma gandeam sa am un thread separat care tine timpul si care stie, in fiecare moment, a carui jucator este tura;
  threadul va scadea, la fiecare secunda, cate o secunda din timpul jucatorului care muta, si daca timpul se scurge trimite un semnal catre thread-ul jucatorului al carui timp s-a terminat
