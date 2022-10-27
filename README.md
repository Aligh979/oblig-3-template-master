# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Ali Alghadban s362111 s362111@oslomet.no


# Oppgavebeskrivelse

oppgave 1
når vi skal legg inn en ny node så løsning består av to deler først finn vi plass hvor den skal ligges i treet.
andre delen er å lage ny node på den verdien vi fikk i metoden og la den peke til riktig foreldre og barna.

oppgave 2
vi skal telle hvor mange ganger er den verdien gjentatt i treet. først levere vi 0 hvis verdien er null.
etterpå lager vi en node og gi den verdien av rot noden og gå i while løkke gjenom treet og teller vi
hver gang vi har den samme verdi.

oppgave 3
vi skal lage to metoder den ene skal retunere første noden i Postorden og andre metoden skal retunere 
andre node i Postorden. i metoden førstePostorden gå vi i while løkka til den første blad noden fra venstre.
da er den første noden i Postorden og retunere vi den.
metoden nestePostorden vi skal sjekke om treete er tomt eller har bare en node eller 
noden har kun et barn til høyre eller har kun et barn til venstre eller har to barn.

oppgave 4
vi skal lage to metoder den første metoden Postorden skal implementeres uten bruk av rekusjon og
uten bruk av hjelpevariabler som stack / queue, da kaller vi førstePostorden og gå i while løkka 
og kalle nestePostorden til at vi kommer til null.  For den andre rekursive metoden skal du lage 
et rekursivt kall som traverserer treet i postorden rekkefølge.
    
