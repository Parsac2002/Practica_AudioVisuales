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
                mediaPlayerComponent.getMediaPlayer().playMedia("./movie.mp4");
                mediaPlayerComponent.getMediaPlayer().play();
            }
        });
        
        //TO DO! implement a PAUSE button to pause video playback.
        JButton pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton,BorderLayout.SOUTH);
        
        pauseButton.addActionListener(new ActionListener() { //para atrapar los eventos que genere el boton cuando se pulse.
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().pause();
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

