HOMEWORK:
- am facut DAO pt toate categoriile de tabele, cu tot felul de functii ce sunt testate in Homework
- am folosit Apache commons DBCP pt pool
- Am dat import artistilor si genreurilor din baza de date de pe site, folosind "import data from file" din intellij
- am incercat sa fac un DAtabaseInsertionTool, prin care sa adaug albumle, astfel : cand citeam un nume de artist, il cautam cu ajutorul ArtistDAO in baza de date si luam id-ul
lui si il adaugam in baza de date cu albume; However cred ca atingea limita superioara de conexiuni la DB or sth pt ca dupa 7 inserari se oprea
- pot gasi workaround si sa modific tabelele si in loc de artist id sa retin artist name la albume, pt ca numele e unic oricum, si sa inserez ca atare.
- UPDATE: am gasit workaround si functioneaza, am dat update la albume;
