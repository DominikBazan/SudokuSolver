import javax.swing.*;
import java.awt.*;

public class SudokuTabela extends JComponent {

    Pole[][] plansza;

    public SudokuTabela(Pole[][] plansza){
        this.plansza = plansza;
    }

    public void paint(Graphics g) {

        int a = 50, marX = 15, marY = -10;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //g.setColor(Color.black);
                g.drawRect(a * i, a * j, a, a);
            }
        }
        g.drawRect(a*3+5, 0, 0, a*9);
        g.drawRect(a*6+5, 0, 0, a*9);
        g.drawRect(0, a*3+5, a*9, a*3);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String cyfra = Integer.toString(plansza[i][j].pewna);
                if(cyfra.equals("0")) cyfra = "";
                if(plansza[i][j].podstawowa){
                    g.setColor(Color.black);
                    g.setFont(new Font("SansSerif",Font.BOLD,30));
                } else {
                    g.setColor(Color.darkGray);
                    g.setFont(new Font("SansSerif",Font.PLAIN,27));
                }
                //g.drawString(cyfra, a*i+marX, a*(j+1)-marY);
                g.drawString(cyfra, a*(j)+marX, a*(i+1)+marY);

            }
        }
        // to ma być wypisywanie opcji
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int prop = 0; prop < 9; prop++) {
                    if(plansza[i][j].propozycja[prop]) {
                        String propChar = Integer.toString(prop+1);
                        g.setColor(Color.darkGray);
                        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
                        g.drawString(propChar, a * j + 5*prop/*+ marX*/, a * (i+1)/* + marY*/);
                    //!!!!!!!!!!
                    }
                }
            }
        }

        repaint();

    }
}
