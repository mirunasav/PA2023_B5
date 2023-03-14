Compulsory si Homework au fost prezentate la laborator;
Bonus:
- Nu mi s-a parut foarte clar ce inseamna maximally 2 - connected graph, asa incat am doua interpretari ale cerintei:
  1. Type of Solution : "FIND_2CONNECTED_COMPONENTS"
  - gasesc componentele conexe care sunt 1-conexe (adica sunt conexe si au puncte de articulatie) sau 2-conexe (sunt conexe si
  nu au puncte de articulatie, adica scotand un nod, nu le deconectez). Cum gasesc aceste componente?
     - Pas 1. gasesc componentele conexe prin functia createConnectedComponents din clasa Utils;
     - Pas 2. Verific daca au puncte de articulatie; daca da -> sunt 1conexe, adica maxim 2 conexe, deci e ok
                                                  daca nu-> 
      - Pas 3. Scot doua noduri random si verific daca graful ramane conex. Daca se deconecteaza, atunci e clar ca el nu e 3-conex, 
      deci este maxim 2-conex, deci e ok.
  2. Type of Solution : "FIND_MAXIMALLY2CONNECTED_COMPONENTS"
    - Am folosit urmatoarea definitie de pe Wikipedia a unui graf maximally 2 connected: 
     "A graph is said to be maximally connected if its connectivity equals its minimum degree";
      In cazul nostru : un graf e maximally 2 connnected daca e 2connected si gradul minim al unui nod este 2.
    - Pas 1. Gasesc componentele conexe, ca in varianta de mai sus
    - Pas 2. Pentru fiecare componenta, verific daca e 2 conexa (functia is2Connected(...) ) si daca gradul minim al grafului este 2.

In plus, mai am un Type Of Solution : " FIND_ARTICULATION_POINTS", care cand e dat ca parametru constructorului clasei Solution,
gaseste toate punctele de articulatie ale grafului.

Mentiuni: 
- in Network, conexiunile sunt generate random (deci la fiecare run, graful este diferit);
- Am creat JUnit tests pentru 2 functii : pentru una care testeaza algoritmul isMaximally2Connected(a doua definitie) pe un graf
despre care stiu ca e Maximally 2 connected; al doilea test e pentru functia isArticulationPoint, dar inca nu am testat pe un exemplu
so it's not yet working;
    
    
