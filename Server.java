import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


public class Server {
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server(args);
            }
        });
    }
    
    private Server(String[] args) {
        JFrame frame = new JFrame("vlcj");
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        frame.setContentPane(mediaPlayerComponent);
        
        //TO DO! choose the correct arguments for the methods below. Add more method calls as necessary
        //frame.setLocation(...);
        frame.setLocation(100, 100);//localizacion en la pantalla (su esquina izq superior).
        //frame.setSize(...);
        frame.setSize(800, 800);//tamaño de la ventana en la localizacion que le ponemos.
        
        //TO DO!! configure the video delivery via RTP
        String media = "file:///home/parsac/AudioVisuales/Practica_AudioVisuales/movie.mp4";
        String options = formatRtpStream("127.0.0.1", 5004);

        System.out.println("Streaming '" + media + "' to '" + options + "'");
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);//cambiado
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

        System.out.println("Media playing ->" + 
        mediaPlayer.playMedia(media,
            options,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep")
        ); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //
    }
    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }
}