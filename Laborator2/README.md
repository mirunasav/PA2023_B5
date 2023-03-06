In compulsory : am o versiune la care am dat commit acum mai multe zile (inainte de laboratorul 2), 
pe care ulterior am schimbat-o pentru a trata exceptiile mai elegant (cu try/catch in loc de System.out.println("Ceva mesaj de Eroare!"));

In Homework : Am modelat locatiile sub forma unui graf si am creat liste de adiacenta
(un nod = o locatie; o muchie intre doua noduri = exista drum intre acele locatii);
Am facut un BFS din nodul sursa al instantei problemei, si am marcat nodurile
ca vizitate sau nu. Daca la final nodul destinatie era vizitat, inseamna ca problema are solutie.

In Bonus:
- Folosesc algoritmul lui Dijkstra in doua feluri pentru a calcula
shortest/fastest path de la sursa la destinatie : o data iau in considerare distanta,
a doua data iau in considerare timpul de parcurgere al unui traseu

- Generez instante random ale problemei, verific sa fie valide, si apoi testez algoritmul pe aceste instante


