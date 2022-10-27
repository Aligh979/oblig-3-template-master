package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {

    private static final class Node<T>
    {
        private T verdi;
        private Node<T> venstre, høyre;
        private Node<T> forelder;


        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SBinTre(Comparator<? super T> c)
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public void nullstill() {
        if (!tom()){
            nullstill(rot);
        }
        rot= null;
        antall=0;
        endringer++;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }
        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;
        int cmp = 0;

        while (p != null)
        {
            q = p;
            cmp = comp.compare(verdi,p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }
        p = new Node<>(verdi,q);

        if (q == null) rot = p;
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        endringer++;
        return true;
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot, q = null;

        while (p != null)  //går gjenom treet for å finne noden vi skal fjernes
        {
            int cmp = comp.compare(verdi,p.verdi);
            if (cmp < 0) { q = p; p = p.venstre; }
            else if (cmp > 0) { q = p; p = p.høyre; }
            else break;
        }
        if (p == null) return false;  // noden ikke finnes i treet

        if (p.venstre == null || p.høyre == null) // noden har et barn
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre; // sjekk om noden har høyre eller venstre barn
            if (p == rot) rot = b; // vi skal fjerne rot noden

            else if (p == q.venstre){
                q.venstre=b;
                if (b!=null){
                    b.forelder=q;
                }
            }
            else{
                q.høyre = b;
            }

            if (b!=null){
                b.forelder = q;
            }

        }
        else
        {
            Node<T> s = p, r = p.høyre;
            while (r.venstre != null)
            {
                s = r;
                r = r.venstre;
            }

            p.verdi = r.verdi;

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }
        endringer++;
        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antallFjernet = 0;
        if (!tom()){
            while (fjern(verdi)) {
                antallFjernet++;
            }
        }

        return antallFjernet;

    }

    public int antall(T verdi) {
        int antallForkomster =0;
        // først sjekker vi null verdi
        if (verdi==null){
            return 0;
        }
        Node <T> p = rot;
        while (p!=null) {
            int com = comp.compare(verdi, p.verdi);
            if (com < 0)
                p = p.venstre;
            else {
                if (com == 0)
                    antallForkomster++;
                p = p.høyre;
            }
        }
        return antallForkomster;

    }

    public void nullstill(Node<T> rot) {
        if (!tom()) nullstill(rot);
        this.rot = null;
        antall = 0;
        endringer = 0;
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        if (p==null){
            throw new NoSuchElementException("Treet er tomt!");
        }
        while (true){
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {


        if (p==null){
            throw new NoSuchElementException("Treet er tomt!");
        }

        else if (p.forelder ==null){
            p= null;
        }

        else if (p==p.forelder.høyre){
            p=p.forelder;
        }

        else if (p==p.forelder.venstre){

            if (p.forelder.høyre==null){
                p=p.forelder;
            }

            else

                p= førstePostorden(p.forelder.høyre);
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        if (rot==null)
            return;
        Node<T> q= førstePostorden(rot);
        oppgave.utførOppgave(q.verdi);
        Node<T> r =nestePostorden(q);
        while (r!=null){
            oppgave.utførOppgave(r.verdi);
            r= nestePostorden(r);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p==null){
            return;
        }
        if (p.venstre!=null){
            postordenRecursive(p.venstre,oppgave);
        }
        if (p.høyre!=null){
            postordenRecursive(p.høyre,oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList<>();
        ArrayDeque<Node> kø = new ArrayDeque<Node>();

        kø.addLast(rot);
        while (!kø.isEmpty()){

            Node<T> current = kø.removeFirst();

            if (current.venstre!= null){
                kø.addLast(current.venstre);

            }
            if (current.høyre!=null){
                kø.addLast(current.høyre);
            }
            liste.add(current.verdi);

        }
        return liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<K>(c);
        for (int i = 0; i<data.size();i++){
            tre.leggInn(data.get(i));
        }
        return tre;
    }

}
