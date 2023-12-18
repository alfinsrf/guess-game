import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hangman extends JFrame implements ActionListener {
    //menghitung jumlah tebakan huruf yang salah
    private int incorrectGuesses;

    //menyimpan kata untuk di tebak dari WordDB class
    private String[] wordChallenge;

    private final WordDB wordDB;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel, resultLabel, wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    private Font customFont;

    //constractors
    public Hangman(){
        super("Guess Me Games");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        // inisialisasi variables
        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        createResultDialog();

        //call fungsi addgui
        addGuiComponents();
    }

    private void addGuiComponents(){
        // untuk gambar hangman
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        // display category / clue
        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setFont(customFont.deriveFont(30f));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 28,
                CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );

        // hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setFont(customFont.deriveFont(64f));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        // button huruf
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);

        // create buttons huruf
        for(char c = 'A'; c <= 'Z'; c++){
            JButton button = new JButton(Character.toString(c));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setFont(customFont.deriveFont(22f));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            //pake value ASCII untuk menghitung currentindex
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        // reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(customFont.deriveFont(22f));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(customFont.deriveFont(22f));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);
        getContentPane().add(buttonPanel);
    }

    //override dari lib yang di import di atas
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Restart")){
            resetGame();

            if(command.equals("Restart")){
                resultDialog.setVisible(false);
            }
        }else if(command.equals("Quit")){
            dispose();
            return;
        }else{
            // letter buttons

            // disable button
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            // check apakah pada kata yang ingin di tebak terdapat huruf dari yang ditebak pemain
            if(wordChallenge[1].contains(command)){
                // indicate jika benar
                button.setBackground(Color.GREEN);

                // simpan hidden word pada array char lalu update hidde text nya
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for(int i = 0; i < wordChallenge[1].length(); i++){
                    // update _ ke huruf yang benar
                    if(wordChallenge[1].charAt(i) == command.charAt(0)){
                        hiddenWord[i] = command.charAt(0);
                    }
                }

                // update hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                // pemain berhain menebak
                if(!hiddenWordLabel.getText().contains("*")){
                    // display result label dengan success / menang
                    resultLabel.setText("You're an expert at guessing :D"); //bisa di gantu guys if u want
                    resultDialog.setVisible(true);
                }

            }else{
                // indicate jika salah
                button.setBackground(Color.RED);

                // increment int incorrect
                ++incorrectGuesses;

                // update hangman image
                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".jpg");

                // pemain gagal menebak
                if(incorrectGuesses >= 6){
                    // display result dialog dengan game over / lose conditions
                    resultLabel.setText("Too bad, you can't guess me :("); //bisa di ganti guys if u want
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("I'am" + wordChallenge[1]);
        }

    }

    //function untuk membuat result dialog
    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.setTitle("Result");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(CommonConstants.SECONDARY_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
    }

    //fungsi untuk mengulang game / me reset pertanyaan / clue
    private void resetGame(){
        // load word baru
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;

        // load image hangman dari awal / 1
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

        // update category / clue
        categoryLabel.setText(wordChallenge[0]);

        // update hiddenWord
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        // enable lagi semua buttons / reset semua buttons ke warna semula
        for(int i = 0; i < letterButtons.length; i++){
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }
}