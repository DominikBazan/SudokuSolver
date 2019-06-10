import java.io.FileNotFoundException;

public class SudokuSolver {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        /*int[][] plansza1 = {
                {4, 6, 0, 8, 0, 5, 0, 0, 3},
                {0, 0, 3, 0, 7, 0, 0, 0, 0},
                {0, 7, 5, 9, 0, 1, 0, 6, 0},
                {0, 8, 4, 0, 0, 0, 0, 7, 0},
                {9, 0, 0, 7, 0, 6, 0, 0, 1},
                {0, 3, 0, 0, 0, 0, 6, 5, 0},
                {0, 9, 0, 4, 0, 2, 8, 3, 0},
                {0, 0, 0, 0, 8, 0, 5, 0, 0},
                {3, 0, 0, 5, 0, 9, 0, 2, 7}
        };*/
        Plansza gra1 = new Plansza("src/Plansze/latwa_01.txt");   //P3 2It
        //Plansza gra2 = new Plansza("src/Plansze/srednia_02.txt"); //P3 3It
        //Plansza gra3 = new Plansza("src/Plansze/trudna_03.txt");  //P3 5It
        //Plansza gra4 = new Plansza("src/Plansze/bardzoTrudna_04.txt");

        //Plansza gra5 = new Plansza("src/Plansze/IntBT01.txt");
        //Plansza gra6 = new Plansza("src/Plansze/IntDiabelskoT02.txt");    //P3 3It
        //Plansza gra7 = new Plansza("src/Plansze/najtrudniejszeT03.txt");
        //Plansza gra8 = new Plansza("src/Plansze/Ekspert01.txt");
        //Plansza gra9 = new Plansza("src/Plansze/SudokuBrainNiumStudios_Ekspert_01.txt");


    }
}

/*
* TO DO:
*   - eliminujKwadrat();
*
*
* */