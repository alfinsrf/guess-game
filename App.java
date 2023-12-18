import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable(){ //ini dari java swing untuk eksekusi GUI 
            @Override
            public void run() { //function run ini dari java swing guys
                new Hangman().setVisible(true); //ini GUI nya
            }
        });
    }
}
