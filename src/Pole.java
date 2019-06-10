public class Pole {

    boolean podstawowa;
    int pewna;
    boolean[] propozycja;

    public Pole(int a, boolean czyPodstawowa) {
        propozycja = new boolean[9];
        for (int i = 0; i < 9; i++) {
            propozycja[i] = true;
        }
        pewna = a;
        if(czyPodstawowa) podstawowa = true;
    }

    public void setPewna(int cyfra){ pewna = cyfra; }
    public void oczyszczeniePewnejZPropozycji(){
        for (int k = 0; k < 9; k++) {
            propozycja[k] = false;
        }
    }
}