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
Robotul Robot1 s-a oprit pentru ca s-a verificat toata matricea```