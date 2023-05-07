Compulsory :
- am creat clasa singleton Persistence manager care imi creeaza o instanta de tip entitymanagerfactory
- am creat entities pt artists si albums
- am scris o clasa test ca ccea din slides dar am aceasta eroare :"No Persistence provider for EntityManager named default" si nu inteleg de ce
Homework:
- am incercat sa creez un AbstractEntity si sa fac toate entitatile sa o mosteneasca dar nu a functionat pt ca ar fi tb ca si acel AbstractEntity sa fie la randul sau un Entity, in relatie cu un tabel
- am creat un AbstractREpository folosind generics si am creat cateva metode generice in cadrul rau
- am creat Repositories pt Albums Genres si Artists si l am modificat pe cel deja existent pentru a extinde clasa abstractrepository
- am creat relatie one to one unidirectionala de la albums la artists pt ca desi un artist poate avea mai multe albume, tabelele din lab 8 sunt de asa natura incat doar in albume sa retin id-ul artistului, nu si invers; nu am vrut sa modific baza de date si toate intrarile din ea doar pt asta
- am creat un tool ca sa generez random nume de artisti si albume si sa le insereze in DB; however imi da o eroare ciudata, imi zice ca nu pot opera pe un closed Entity Manager (desi nu e closed, se inchide f random); am crezut ca problema e pt ca incerc sa deschid aceasta variabila statica a clasei AbstractRepository din alta clasa asa incat am incercat sa fac metoda createAlbum si in abstractRepository, fix ca la celelalte metode, si tot nu a mers

update am gasit problema, apelez o functie care imi inchide acel entity manager dar tb sa refactorizez tot ca sa rezolv;
also ca sa vad cat dureaza sa se execute inserarile tb sa fac o operatie super simpla pe care am mai fct-o in vreo 5 laboratoare
