import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Client{
    

    private final JFrame frame;
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JScrollPane scrollPane;
    private Boolean in_url = false;
    private Boolean in_video = false;
    
    public static void main(final String[] args) {
        new NativeDiscovery().discover();//busca los binarios de vlc que tienes en tu sistema.
        SwingUtilities.invokeLater(new Runnable() {//Invocacion estandar dde un frame para Swing
            @Override
            public void run() {
                new Client(args);//es la aplicacion con las ventanas que se van a ejecutar.
            }
        });
    }
    
    public Client(String[] args) {
        
        
        frame = new JFrame("Media Player");
        
        //TO DO! choose the correct arguments for the methods below. Add more method calls as necessary
        //frame.setLocation(...);
        frame.setLocation(100, 100);//localizacion en la pantalla (su esquina izq superior).
        //frame.setSize(...);
        frame.setSize(800, 800);//tamaño de la ventana en la localizacion que le ponemos.
        //...
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//accion si le damos al "close"
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                //to release the media player component and associated native resources, 
                //before exiting the application
                System.exit(0);
            }
        });
        
        JPanel contentPane = new JPanel();//panel nuevo
        contentPane.setLayout(new BorderLayout());
        //to create a media player, the simplest is to use an instance of an
        //EmbeddedMediaPlayerComponent

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
        
        JPanel controlsPane = new JPanel();//nuevo panel de control (para los botones)
        //----------------------------------------------------------------------------------------
        //P2:
        //definition of the URL panel inside the same frame:
        //Content pane y dentro de content pane : NORTH -> urlpane y SOUTH -> constrolspane.
        JPanel urlPane = new JPanel();//primero creamos un nuevo panel.
        urlPane.setLayout(new BorderLayout());
        contentPane.add(urlPane, BorderLayout.NORTH);
        //creamos campo de texto.
        JTextField textField = new JTextField(65);
        //textField.setBounds(0, 0, 500, 200);
        urlPane.add(textField, BorderLayout.WEST);
        //----------------------------------------------------------------------------------------
        //Mostramos la pagina HTML con la URL:
        //Crearemos un "editor Pane"
        JEditorPane html_page = new JEditorPane();
        html_page.setEditable(false); //no vamos a editar la pagina web que mostramos.
        html_page.setContentType("text/html");
        //JScrollPane scrollPane = new JScrollPane();
        //argumento "false" para que no se genere un evento de edicion.
        

        //Go Button:
        //Definition of "go" button.

        JButton goButton = new JButton("Go");//creamos nuevo boton
        urlPane.add(goButton, BorderLayout.EAST);//lo ponemos en el panel de control
        goButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                in_video = false;
                
                if(mediaPlayerComponent.getMediaPlayer().isPlaying() == false){ //si hay contenido reproduciendose, no deja salir de la pantalla.
                    try{
                        URL url = new URL(textField.getText());
                        //formato de URL para tener mas control sobre el formato de esta cadena de texto.
                        
                        if(url != null && !(isVideoUrl(url))){//si la URL es valida y no es un fichero de video al que se quiere acceder.
                            System.out.println("in_url = " + in_url);
                            if(in_url == false){//ya si entra 1 vez, se pone a true y no intenta eliminar el video que no hay.
                                html_page.setPage(url);
                                //contentPane.setVisible(false);
                                
                                scrollPane = new JScrollPane(html_page);
                                in_url = true;//Aqui dentro para que si le doy al play no quite el Scrollpanel que no existe y me de un NullPointerException.
                                contentPane.remove(mediaPlayerComponent);
                                contentPane.add(scrollPane, BorderLayout.CENTER);
                                //Solamente si estamos de nuevas quitamos el mediaPlayerComponent.
                            }else{
                                System.out.println("Removing URL Pane...");
                                html_page.setPage(url);
                                //contentPane.remove(scrollPane);
                                //contentPane.add(scrollPane);
                            }
                            //refrescamos el panel
                            contentPane.revalidate();
                            contentPane.repaint();
                            //lo hacemos visible de nuevo.
                            contentPane.setVisible(true); 
                            
                            //Sin scroll:
                            //  contentPane.removeAll();
                            //  contentPane.add(html_page);
                            //  contentPane.revalidate();
                            //  contentPane.repaint();
                            //  contentPane.setVisible(true); 
                        }
                    }catch(Exception url_exception){
                        System.out.println("Exception with URL, go button : " + url_exception);
                    }

                }
            }
        });

        //----------------------------------------------------------------------------------------

        //Definition of PLAY button
        
        //----------------------
        JButton playButton = new JButton("Play");//creamos nuevo boton
        controlsPane.add(playButton);//lo ponemos en el panel de control
        contentPane.add(controlsPane, BorderLayout.SOUTH);
        
        //Handler for PLAY button
        //-----------------------
        playButton.addActionListener(new ActionListener() { //para atrapar los eventos que genere el boton cuando se pulse.
            @Override
            public void actionPerformed(ActionEvent e) {
                //"rtp//://@127.0.0.1:5004"
                // si no reproducimos nada, el método "isPlaying()" devuelve siempre false.
                String video_url_str = textField.getText();
                
                if(video_url_str !=null){
                        try{
                            URL video_url =  new URL(video_url_str);
                            if(in_url == true && isVideoUrl(video_url)){
                                //para que si no es un video, no se refresque la disposicion de nuestro panel.
                                contentPane.remove(scrollPane);
                                contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
                                //refrescamos el panel
                                contentPane.revalidate();
                                contentPane.repaint();
                                //lo hacemos visible de nuevo.
                                contentPane.setVisible(true);
                                in_url = false;  
                                in_video = false;//Solamente la 1a vez que cambiamos de la web al video.
                            }
                            if(in_video == false && isVideoUrl(video_url)){
                                if(mediaPlayerComponent.getMediaPlayer().isPlaying() == false){
                                    mediaPlayerComponent.getMediaPlayer().playMedia(video_url_str);
                                    System.out.println("Playing");
                                    in_video = true;
                                    
                                }// si quito este "if" cuando le de a Play y ya estabamos viendo el video, no empieza desde el principio.
                            }else{
                                //se olvida de la URL y reanuda lo que ya se estaba viendo.
                                mediaPlayerComponent.getMediaPlayer().play();
                                System.out.println("Playing after pausing");
                                System.out.println("in_video = " + in_video);
                            }

                        } catch (Exception play_url_exception) {
                            System.out.println("Exception in URL while trying to play it: " + play_url_exception);
                        }
                    }
                    
                

                
                // if(mediaPlayerComponent.getMediaPlayer().isPlaying() == false){
                //     System.out.println("Paused video");
                //     mediaPlayerComponent.getMediaPlayer().play();
                // }
            }
        });
        
        //TO DO! implement a PAUSE button to pause video playback.
        JButton pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton,BorderLayout.SOUTH);
        
        pauseButton.addActionListener(new ActionListener() { //para atrapar los eventos que genere el boton cuando se pulse.
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mediaPlayerComponent.getMediaPlayer().isPlaying() == true){
                    mediaPlayerComponent.getMediaPlayer().pause();
                    System.out.println("Video pausado = " + mediaPlayerComponent.getMediaPlayer().isPlaying());
                }
            

            }
        });
        
        
        
        //TO DO! implement a STOP button to stop video playback and exit the application.
        JButton stopButton = new JButton("Stop");
        controlsPane.add(stopButton,BorderLayout.SOUTH);
        
        stopButton.addActionListener(new ActionListener() { //para atrapar los eventos que genere el boton cuando se pulse.
            @Override
            public void actionPerformed(ActionEvent e) {
                if(in_url == false){
                    mediaPlayerComponent.getMediaPlayer().stop();
                    in_video = false;//para que vuelva a pedir el video desde 0
                }
            }
        });
        
        

        //Makes visible the window
        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        
    }
    //returns true if it is a video.
    public static boolean isVideoUrl(URL url) {
        String file = url.getFile().toLowerCase();
        return file.endsWith(".mp4") || file.endsWith(".mkv") || file.endsWith(".flv") ||
               file.endsWith(".avi") || file.endsWith(".mov") || file.endsWith(".wmv");
    }

    // private int containsEmbeddedMediaPlayerComponentOrJEditorPane(JPanel contentPane) {
    //     for (Component component : contentPane.getComponents()) {
    //         if (component instanceof EmbeddedMediaPlayerComponent) {
    //             return 1;
    //         } else if (component instanceof JEditorPane) {
    //             return 2;
    //         }
    //     }
    //     return 3;
        
    // }

    
    
    
}

