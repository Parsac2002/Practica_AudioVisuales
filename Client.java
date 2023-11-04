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

        //argumento "false" para que no se genere un evento de edicion.
        

        //Go Button:
        //Definition of "go" button.

        JButton goButton = new JButton("Go");//creamos nuevo boton
        urlPane.add(goButton, BorderLayout.EAST);//lo ponemos en el panel de control
        goButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mediaPlayerComponent.getMediaPlayer().isPlaying() == false){ //si hay contenido reproduciendose, no deja salir de la pantalla.
                    try{
                        URL url = new URL(textField.getText());
                        //formato de URL para tener mas control sobre el formato de esta cadena de texto.
                        if(url != null){
                            html_page.setPage(url);
                            //contentPane.setVisible(false);
                             
                             JScrollPane scrollPane = new JScrollPane(html_page);
                             contentPane.removeAll();
                             contentPane.add(scrollPane);
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
                
                    String video_url = textField.getText();
                    if(video_url !=null){
                        if(mediaPlayerComponent.getMediaPlayer().isPlaying() == false){
                            mediaPlayerComponent.getMediaPlayer().playMedia(video_url);
                            mediaPlayerComponent.getMediaPlayer().play();
                            System.out.println("Playing");
                            
                        }// si quito este "if" cuando le de a Play y ya estabamos viendo el video, no empieza desde el principio.
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
                mediaPlayerComponent.getMediaPlayer().pause();
                System.out.println("Video pausado = " + mediaPlayerComponent.getMediaPlayer().isPlaying());
            }
        });
        
        
        
        //TO DO! implement a STOP button to stop video playback and exit the application.
        JButton stopButton = new JButton("Stop");
        controlsPane.add(stopButton,BorderLayout.SOUTH);
        
        stopButton.addActionListener(new ActionListener() { //para atrapar los eventos que genere el boton cuando se pulse.
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().stop();
            }
        });
        
        

        //Makes visible the window
        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        
    }
    
}

