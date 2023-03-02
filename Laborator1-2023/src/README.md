Commitul cu "properly treated exception..." era valabil pt laboratorul 2 si am incurcat
proiectul in care am dat push. In istoric se vede ca am pus pe github compulsory +homework +bonus dinainte 
de al doilea laborator deci sper sa nu fie probleme legate de deadline.

Homework: Am creat un Latin Matrix folosind permutari; in functie de dimensiunea imputului aleg daca
printez rezultatele functiile sau daca doar execut functiile.

Bonus : 1. Am gandit ca nodurile unui graf Cn sa fie conectate intre ele in felul urmator:
nodul 1 are muchii cu  nodul n si nodul 2
nodul 2 are muchii cu nodul 1 si cu nodul 3
nodul k are muchii cu nodurile k-1 si k+1

2. Am creat o functie care valideaza daca o matrice de adiacenta poate fi potrivita pentru un graf regulat sau nu
(adica verifica daca toate nodurile au acelasi grad);
Creez matricea astfel:
- Iau pe rand nodurile, verific daca mai au "locuri disponibile in lista de adiacenta" (adica daca si-au atins sau nu
gradul maxim); 
- daca mai iau locuri, atunci generez o permutare random a numerelor de la 1 la n, si iau pe rand
nodurile din permutare si vad daca le pot uni cu nodul meu sau nu(in functie de asta modific listele de adiacenta
si matricea de adiacenta). Motivul pentru care generez permutari random e pentru ca o matrice de adiacenta a unui
graf regulat nu are o forma generala, deci incerc sa "ghicesc".
- la final, verific daca e o matrice valida pentru un graf regulat sau nu
