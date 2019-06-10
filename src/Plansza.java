import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Plansza {

    Pole[][] plansza;
    boolean koniec;

    Plansza(int[][] plansza) throws InterruptedException {
        this.plansza = new Pole[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean czyPodstawowa;
                if(plansza[i][j] == 0) czyPodstawowa = false;
                else czyPodstawowa = true;
                this.plansza[i][j] = new Pole(plansza[i][j], czyPodstawowa);
            }
        }
        koniec = false;
        rozwiazywanie();
    }
    Plansza(String plik) throws FileNotFoundException, InterruptedException {
        Scanner odczyt = new Scanner(new File(plik));
        try{
            this.plansza = new Pole[9][9];
            for (int i = 0; i < 9; i++) {
                String linijka = odczyt.nextLine();
                for (int j = 0; j < 9; j++) {
                    int cyfra = (linijka.charAt(j))-'0';
                    boolean czyPodstawowa;
                    if(cyfra == 0) czyPodstawowa = false;
                    else czyPodstawowa = true;
                    this.plansza[i][j] = new Pole(cyfra, czyPodstawowa);
                }
            }
        }catch(java.util.NoSuchElementException ex){}
        koniec = false;
        rozwiazywanie();
    }
    void wypiszWTerminalu() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.plansza[i][j].pewna != 0) {
                    System.out.print(this.plansza[i][j].pewna + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }
    void wypiszWSwing() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame window = new JFrame();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setBounds(0, 0, 450, 450);
                window.getContentPane().add(new SudokuTabela(plansza));
                window.setVisible(true);
            }
        });
    }
    void wypiszStanWTerminalu() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(this.plansza[i][j].pewna + " ");
                for (int k = 0; k < 9; k++) {
                    if (this.plansza[i][j].propozycja[k]) {
                        System.out.print(k + 1);
                    } else {
                        System.out.print("-");
                    }
                }
                System.out.print("  ");
            }
            System.out.print("\n");
            if(i==2 || i==5) System.out.print("...........................................................................................");
            System.out.print("\n");

        }
    }
    void poczatkoweOczyszczeniePewnychIZPropozycji() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (plansza[i][j].pewna != 0) {
                    plansza[i][j].oczyszczeniePewnejZPropozycji();
                    nowaPewna2(i,j);
                }
            }
        }
    }
    void nowaPewna2(int i, int j){
        plansza[i][j].oczyszczeniePewnejZPropozycji();
        int a = plansza[i][j].pewna;
        //kolumna i wiersz:
        for (int wk = 0; wk < 9; wk++) {
            plansza[wk][j].propozycja[a - 1] = false;
            plansza[i][wk].propozycja[a - 1] = false;
        }
        //kwadrat:
        int kwX = 0, kwY = 0;
        if(i > 2 && i < 6) kwY = 1;
        else if(i > 5 && i < 9) kwY = 2;
        if(j > 2 && j < 6) kwX = 1;
        else if(j > 5 && j < 9) kwX = 2;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                plansza[x+kwY*3][y+kwX*3].propozycja[a-1] = false;
            }
        }
    }
    void jednaPropozycjaWPolu() {
        // Dla każdego pola sprawdza czy nie została tam tylko jedna propozycja
        int pewnaCyfra = 0, pozX = 0, pozY = 0;
        for (int i = 0; i < 9; i++) {
            //nie:
            for (int j = 0; j < 9; j++) {
                int licznik = 0;
                for(int itProp = 0; itProp < 9; itProp++) {
                    if(plansza[i][j].propozycja[itProp]){
                        licznik++;
                        //if(licznik > 1) continue nie;
                        pewnaCyfra = itProp+1;
                        pozX = i;
                        pozY = j;
                    }
                }
                if (licznik == 1){
                    plansza[pozX][pozY].setPewna(pewnaCyfra);
                    nowaPewna2(pozX, pozY);
                }
            }
        }
    }
    void jedynaPropozycjaWKolumnach() {
        for (int k = 0; k < 9; k++) {
            nastepnaCyfra:
            for (int cyfra = 1; cyfra < 10; cyfra++) {
                int licznik = 0, lokacja = 0;
                for (int i = 0; i < 9; i++) {
                    if (plansza[i][k].pewna == cyfra || licznik > 1) {
                        continue nastepnaCyfra;
                    }
                    if (plansza[i][k].propozycja[cyfra - 1]) {
                        licznik++;
                        if(licznik == 1) lokacja = i;
                    }
                }
                if (licznik == 1) {
                    plansza[lokacja][k].setPewna(cyfra);
                    nowaPewna2(lokacja, k);
                }
            }
        }
    }
    void jedynaPropozycjaWWierszach() {
        for (int w = 0; w < 9; w++) {
            nastepnaCyfra:
            for (int cyfra = 1; cyfra < 10; cyfra++) {
                int licznik = 0, lokacja = 0;
                for (int j = 0; j < 9; j++) {
                    if (plansza[w][j].pewna == cyfra || licznik > 1) {
                        continue nastepnaCyfra;
                    }
                    if (plansza[w][j].propozycja[cyfra - 1]) {
                        licznik++;
                        if(licznik == 1) lokacja = j;
                    }
                }
                if (licznik == 1) {
                    plansza[w][lokacja].setPewna(cyfra);
                    nowaPewna2(w, lokacja);
                }
            }
        }
    }
    void jedynaPropozycjaWKwadracie(){
        for (int kwX = 0; kwX < 3; kwX++) {
            for (int kwY = 0; kwY < 3; kwY++) {
                for(int cyfra = 1; cyfra < 10; cyfra++){
                    int licznik = 0, kordX = 0, kordY = 0;
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if(plansza[x+kwX*3][y+kwY*3].propozycja[cyfra-1]){
                                licznik++;
                                kordX = x+kwX*3;
                                kordY = y+kwY*3;
                            }
                        }
                    }
                    if(licznik == 1){
                        plansza[kordX][kordY].setPewna(cyfra);
                        nowaPewna2(kordX, kordY);
                    }
                }
            }
        }
    }
    void eliminacjaPoParzeWspolliniowejWKwadracie() {

        //Dla każdej cyfry:
        for(int cyfra = 0; cyfra < 9; cyfra++) {
            //Dla 3 wierszy kwadratow:
            for (int rzadKw = 0; rzadKw < 3; rzadKw++) {
                //Dal kazdego wiersza kwadratow:
                //Dla wiersza 1/3:
                for(int kwWWierszu = 0; kwWWierszu < 3; kwWWierszu++) {
                    boolean aa = plansza[0 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean ab = plansza[0 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean ac = plansza[0 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ba = plansza[1 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean bb = plansza[1 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean bc = plansza[1 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ca = plansza[2 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean cb = plansza[2 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean cc = plansza[2 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];

                    if (
                            (   (aa&&ab)||(aa&&ac)||(ab&&ac)   ) &&
                            !ba&&!bb&&!bc&&!ca&&!cb&&!cc
                       ) {
                        //usuniecie propozycji z wiersza 1/3:
                        for(int k = 0; k < 9; k++){
                            if(k != kwWWierszu*3+0 && k != kwWWierszu*3+1 && k != kwWWierszu*3+2){
                                plansza[rzadKw*3+0][k].propozycja[cyfra] = false;
                            }
                        }
                    }
                }
                //Dla wiersza 2/3:
                for(int kwWWierszu = 0; kwWWierszu < 3; kwWWierszu++) {
                    boolean aa = plansza[0 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean ab = plansza[0 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean ac = plansza[0 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ba = plansza[1 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean bb = plansza[1 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean bc = plansza[1 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ca = plansza[2 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean cb = plansza[2 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean cc = plansza[2 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];

                    if (
                            (   (ba&&bb)||(ba&&bc)||(bb&&bc)   ) &&
                                    !aa&&!ab&&!ac&&!ca&&!cb&&!cc
                            ) {
                        //usuniecie propozycji z wiersza 2/3:
                        for(int k = 0; k < 9; k++){
                            if(k != kwWWierszu*3+0 && k != kwWWierszu*3+1 && k != kwWWierszu*3+2){
                                plansza[rzadKw*3+1][k].propozycja[cyfra] = false;
                            }
                        }
                    }
                }
                //Dla wiersza 3/3:
                for(int kwWWierszu = 0; kwWWierszu < 3; kwWWierszu++) {
                    boolean aa = plansza[0 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean ab = plansza[0 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean ac = plansza[0 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ba = plansza[1 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean bb = plansza[1 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean bc = plansza[1 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];
                    boolean ca = plansza[2 + rzadKw * 3][0+3*kwWWierszu].propozycja[cyfra];
                    boolean cb = plansza[2 + rzadKw * 3][1+3*kwWWierszu].propozycja[cyfra];
                    boolean cc = plansza[2 + rzadKw * 3][2+3*kwWWierszu].propozycja[cyfra];

                    if (
                            (   (ca&&cb)||(ca&&cc)||(cb&&cc)   ) &&
                                    !aa&&!ab&&!ac&&!ba&&!bb&&!bc
                            ) {
                        //usuniecie propozycji z wiersza 3/3:
                        for(int k = 0; k < 9; k++){
                            if(k != kwWWierszu*3+0 && k != kwWWierszu*3+1 && k != kwWWierszu*3+2){
                                plansza[rzadKw*3+2][k].propozycja[cyfra] = false;
                            }
                        }
                    }
                }

            }

            //Dla kolumn:


        }
/*
        for (int kwX = 0; kwX < 3; kwX++) {
            for (int kwY = 0; kwY < 3; kwY++) {
                //dla kazdego kwadratu:
                for(int cyfra = 1; cyfra < 10; cyfra++) {
                    //dla kazdej cyfry:
                    //poziomo:
                    for (int i = 0; i < 3; i++) {
                        //dla 3 rzędów:
                        if(     (plansza[kwX*3+i][kwY*3+0].propozycja[cyfra-1] && plansza[kwX*3+i][kwY*3+1].propozycja[cyfra-1]) ||
                                (plansza[kwX*3+i][kwY*3+1].propozycja[cyfra-1] && plansza[kwX*3+i][kwY*3+2].propozycja[cyfra-1]) ||
                                (plansza[kwX*3+i][kwY*3+0].propozycja[cyfra-1] && plansza[kwX*3+i][kwY*3+2].propozycja[cyfra-1])
                                // !!!!!!! plus jeszcze nie może być w innych wierszach w kwadracie
                        ){
                            //usuniecie propozycji z wiersza i:
                            for(int k = 0; k < 9; k++){
                                if(k != kwY*3+0 && k != kwY*3+1 && k != kwY*3+2){
                                    plansza[kwX*3+i][k].propozycja[cyfra-1] = false;
                                }
                            }
                        }
                    }
                    //pionowo:
                    // (. . .)
                }
            }
        }
    */
    }
    boolean czyKoniec(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(plansza[i][j].pewna == 0) return false;
            }
        }
        return true;
    }
    boolean sprawdzenie(){
        int cyfra;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ////dla każdego pola:
                //ładowanie liczby:
                if(plansza[i][j].pewna == 0) continue;
                else cyfra = plansza[i][j].pewna;
                //kolumna:
                for(int wierszTmp = 0; wierszTmp < 9; wierszTmp++){
                    if(wierszTmp == i) continue;
                    else if (cyfra == plansza[wierszTmp][j].pewna) return false;
                }
                //wiersz:
                for(int kolumnaTmp = 0; kolumnaTmp < 9; kolumnaTmp++){
                    if(kolumnaTmp == j) continue;
                    else if (cyfra == plansza[i][kolumnaTmp].pewna) return false;
                }
                //kwadrat:
                int kwX, kwY;

                if(i<3) kwX = 0;
                else if(i<6) kwX = 1;
                else kwX = 2;

                if(j<3) kwY = 0;
                else if(j<6) kwY = 1;
                else kwY = 2;

                for(int wKw = 0; wKw < 3; wKw++){
                    for(int kKw = 0; kKw < 3; kKw++){
                        int kordX = wKw+3*kwX, kordY = kKw+3*kwY;
                        if(kordX == i && kordY == j) continue;
                        else if(plansza[kordX][kordY].pewna == cyfra) return false;
                    }
                }
            }
        }
        return true;
    }

    void rozwiazywanie() throws InterruptedException {
        //wypiszWTerminalu();
        //wypiszStanWTerminalu();
        //wypiszWSwing();

        wypiszWSwing();
        poczatkoweOczyszczeniePewnychIZPropozycji();    //DOBRZE.

        int liczbaInteracji = 0;
        while (!koniec) {
            //wypiszStanWTerminalu();

            jednaPropozycjaWPolu();         //DOBRZE.
        /*    jedynaPropozycjaWKolumnach();   //DOBRZE.
            jedynaPropozycjaWWierszach();   //DOBRZE.
            jedynaPropozycjaWKwadracie();   //DOBRZE.
*/
//            System.out.println("Przed błędem.");
  //          readInt();
            eliminacjaPoParzeWspolliniowejWKwadracie(); //ZLE DZAILA!
    //        System.out.println("Po błędzie.");
      //      readInt();

            liczbaInteracji++;
            System.out.println(liczbaInteracji);
            if(czyKoniec()) break;
            //Thread.sleep(400);
            if(liczbaInteracji==1000){wypiszStanWTerminalu();break;}

            //readInt();
            //Thread.sleep(800);
        }
        System.out.println("Liczba iteracji:\t"+liczbaInteracji);
        System.out.println("Czy prawidłowo:\t"+sprawdzenie());
    }
    static public int readInt() {
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}
/*
To do:
-naprawa: eliminacjaPoParzeWspolliniowejWKwadracie();




 */