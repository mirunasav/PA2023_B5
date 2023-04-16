Compulsory:
- am urmat oarecum exemplul din slideurile
de laborator, cu mici modificari. Am adaugat in explorationMap
campul "totalVisitedCells" pe care il actualizez
mereu cand se viziteaza o celula noua. Cand numarul de celule vizitate
devine egal cu nr total de celule al matricei, robotii se opresc din a explora.
- am implementat conditia ca un robot sa nu se poata duce intr-o celula deja vizitata
- cand creez in main un nou Exploration, ii dau ca parametru
dimensiunea matricei si nr de roboti creati.
- exemplu : pentru Exploration (5,2), un posibil output este urmatorul:
```
threadul pentru robotul  Robot0 a inceput!
threadul pentru robotul  Robot1 a inceput!
Robotul Robot0 a visitat randul 0, coloana 2
Robotul Robot0 a visitat randul 2, coloana 0
Robotul Robot0 a visitat randul 0, coloana 0
Robotul Robot0 a visitat randul 1, coloana 1
Robotul Robot0 a visitat randul 2, coloana 1
Robotul Robot0 a visitat randul 2, coloana 3
Robotul Robot0 a visitat randul 1, coloana 3
Robotul Robot1 a visitat randul 0, coloana 1
Robotul Robot1 a visitat randul 3, coloana 0
Robotul Robot0 a visitat randul 3, coloana 1
Robotul Robot0 a visitat randul 1, coloana 0
Robotul Robot1 a visitat randul 1, coloana 2
Robotul Robot0 a visitat randul 3, coloana 3
Robotul Robot1 a visitat randul 3, coloana 2
Robotul Robot0 a visitat randul 2, coloana 2
Robotul Robot1 a visitat randul 0, coloana 3
Robotul Robot0 s-a oprit pentru ca s-a verificat toata matricea
Robotul Robot1 s-a oprit pentru ca s-a verificat toata matricea
```

HOMEWORK
- in clasa RobotController, citesc de la tastatura cand primesc comenzi
(de tipul "nume comanda" "nume robot" - de exemplu, pause Robot0) si se executa
respectiva comanda. Cand toti robotii sunt stopped, se opreste si RobotControllerul
  (si fiind pe acelasi thread cu exploration, se opreste si tot programul);
-Pentru algoritm am incercat o gramada de variante, implementate ca un DFS + backtracking,
si pe langa ca era super mult chin, rula si ft greu; asa ca am schimbat conditia de a nu putea fi "calcata"
o celula care a fost deja vizitata, si am lasat roboteii sa calce pe celulele care au fost deja vizitate (fara sa le mai populeze cu tokeni);
Am facut un algoritm de tip DFs in care in loc de lista de adiacenta, am un neighbour list in care retin toate
"pozitiile eligibile" (functia generateNeighbourPositions).Le pun pe acestea intr-o lista
pe care o iterez si fac un DFS recursiv. Asa,cand un robot se blocheaza, se intoarce la ultima casuta din care avea alte variante de a merge.
El continua sa viziteze casute si le populeaza cu tokeni doar daca nu a facut asta deja alt robot inaintea lui.

Algoritmul se cheama runAlgorithm si apeleaza recursiveRun(); Pt a face ca robotii sa mearga random,
ca la compulsory, atunci in functia run a fiecaruia tb apelata functia randomRun();

- pt timer, am facut un alt thread de tip daemon care
  -ori se opreste cand e trecuta limita de timp (in cod e pusa vreo 6 secunde ca sa nu dureze o gramada ),
si atunci opreste toti robotii
- ori se opreste cand s-a terminat de vizitat matricea si in consecinta se opreste programul; inainte sa se opreasca,
afiseaza timpul total;
- In robot controller, inainte sa se opreasca,

BONUS
- am creat un graf : mai intai am inceput sa creez un graf random si am fct toate functiile necesare,
apoi mi am dat seama ca nu s-ar crea un graf conex asa ca am creat un graf conex random cu ajutorul jgrapht.
- cam ca la matrice, am un explorationGraph (in loc de explorationmap) care e implementat ca un Singleton
- Robotii viziteaza graful intr-o maniera dfs, pana se termina tot graful de vizitat
- am creat un vertexFactory ca sa pot crea un graf random conex
- clasa vertex contine un id unic fiecarui nod precum si functii de vizitare
- dat fiind ca graful este shared intre roboti, acestia au acces pe rand la nodurile sale
- exemplu de output:
```agsl

([1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 ], [{1 ,6 }, {10 ,6 }, {9 ,7 }, {8 ,9 }, {5 ,9 }, {1 ,5 }, {10 ,1 }, {2 ,1 }, {3 ,1 }, {5 ,6 }, {10 ,7 }, {8 ,2 }, {9 ,1 }, {7 ,6 }, {5 ,4 }, {4 ,7 }, {3 ,6 }])
threadul pt robotul Robot0 a inceput
threadul pt robotul Robot1 a inceput
Robotul  Robot0 a explorat nodul 4
Robotul  Robot1 a explorat nodul 9
Robotul  Robot1 a explorat nodul 7
Robotul  Robot0 a explorat nodul 5
Robotul  Robot1 a explorat nodul 10
Robotul  Robot0 a explorat nodul 1
Robotul  Robot1 a explorat nodul 6
Robotul  Robot0 a explorat nodul 3
Robotul  Robot1 a explorat nodul 8
Robotul  Robot0 a explorat nodul 2
Robotul Robot1 s- a oprit
Robotul Robot0 s- a oprit
s-a terminat de explorat graful
s-a terminat de explorat graful
```
Cum graful e conex, mereu se termina de explorat. Din nou, robotii au voie sa treaca printr-un nod deja vizitat 
doar ca pur si simplu nu il mai marcheaza ca vizitat.


https://www2.mathematik.tu-darmstadt.de/~disser/pdfs/DereniowskiDisserKosowskiPajakUznanski13.pdf
