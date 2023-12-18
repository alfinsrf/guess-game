import java.awt.*;

public class CommonConstants {
    // variable untuk akses data
    public static final String DATA_PATH = "resources/data.txt"; //data bahan untuk games
    public static final String IMAGE_PATH = "resources/1.jpg"; //data foto
    public static final String FONT_PATH = "resources/Cartoonero.ttf"; //data font 

    // untuk atur warna apps
    public static final Color PRIMARY_COLOR = Color.decode("#5F6F52"); //warna utama
    public static final Color SECONDARY_COLOR = Color.decode("#B99470"); //warna kedua
    public static final Color BACKGROUND_COLOR = Color.decode("#A9B388"); //warna background

    // untuk atur frame apps
    public static final Dimension FRAME_SIZE = new Dimension(540, 760); //ukuran aplikasi
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int)(FRAME_SIZE.height * 0.42)); //ukuran tombol
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/2), (int)(FRAME_SIZE.height/6)); //ukuran pop up panel
}