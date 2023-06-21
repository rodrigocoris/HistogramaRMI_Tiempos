package histograma;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FormularioHistograma extends JFrame {
    private Server server;

    public FormularioHistograma(Server server) throws RemoteException {
        this.server = server;
        server.registerClient();

        setSize(1010, 530);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Histogramas");
        setLocationRelativeTo(null);
        add(new Panel());
    }

    final class Panel extends JPanel {

        private final JPanel panelCentral = new JPanel();
        private final JPanel panelSur = new JPanel();
        private final JButton buttonSelectImage = new JButton("Seleccionar imagen"), buttonDrawNormal = new JButton("Dibujar histogramas");
        private final JLabel labelImageOne = new JLabel(), labelPathImage = new JLabel("", SwingConstants.CENTER);
        private final JLabel labelImageTwo = new JLabel();
        private final JScrollPane panelImagen = new JScrollPane();
        private final JPanel panelRed = new JPanel();
        private final JPanel panelGreen = new JPanel();
        private final JPanel panelBlue = new JPanel();
        private final JPanel panelGray = new JPanel();
        private final JButton buttonForkJoin = new JButton("Fork/Join");
        private final JButton buttonClear = new JButton("Limpiar");
        private final JButton buttonExecutorServices = new JButton("Executor Services");
        private final JLabel labelSecuencial = new JLabel("Secuencial: 0.0 ms", SwingConstants.CENTER);
        private final JLabel labelForkJoin = new JLabel("Fork/Join: 0.0 ms", SwingConstants.CENTER);
        private final JLabel labelExecutorServices = new JLabel("Executor Services: 0.0 ms", SwingConstants.CENTER);

        private final JButton buttonGetImages = new JButton("Obtener imagenes");
        private JPanel panelImages;
        // image
        private BufferedImage image;

        Panel() {
            setBackground(Color.red);
            setLayout(new BorderLayout());
            addPanels();
            addEvents();
        }

        void addPanels() {
            // panelCenter.setBackground(Color.BLACK);
            // panelCenter.add(image);
            JPanel panelC = new JPanel(new GridLayout(1, 2)), panelL = new JPanel(new BorderLayout()), panelR = new JPanel(new GridLayout(2, 2));
            //
            //labelImage.setPreferredSize(new Dimension(300, 300));
            //labelImage.setMaximumSize(new Dimension(300, 300));
            panelImagen.setPreferredSize(new Dimension(300, 350));
            panelImagen.setMaximumSize(new Dimension(300, 350));
            // panelImage.setSize(100, 100);
            // panelImagen.setViewportView(labelImageOne);
            // add labelImageOne and labelImageTwo to panelImagen
            panelImages = new JPanel(new GridLayout(1, 2));
            // set this color as background: #141E30
            // panelImages.setBackground(new Color(20, 30, 48));
            panelImages.add(labelImageOne);
            panelImages.add(labelImageTwo);
            panelImagen.setViewportView(panelImages);

            // panelImagen.setViewportView(labelImageOne);
            // panelImagen.setViewportView(labelImageTwo);
            panelL.add(panelImagen, BorderLayout.CENTER);
            JPanel panelLSouth = new JPanel(new GridLayout(4, 1));
            panelLSouth.add(buttonSelectImage);
            panelLSouth.add(buttonGetImages);
            panelLSouth.add(buttonDrawNormal);
            panelLSouth.add(labelPathImage);
            panelL.add(panelLSouth, BorderLayout.SOUTH);

            // panel derecho
            panelR.setPreferredSize(new Dimension(500, 160));
            panelR.setMaximumSize(new Dimension(500, 160));

            panelRed.setPreferredSize(new Dimension(150, 80));
            panelRed.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelRed);
            panelGreen.setPreferredSize(new Dimension(150, 80));
            panelGreen.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelGreen);
            panelBlue.setPreferredSize(new Dimension(150, 80));
            panelBlue.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelBlue);
            panelGray.setPreferredSize(new Dimension(150, 80));
            panelGray.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelGray);

            panelC.add(panelL);
            panelC.add(panelR);
            panelC.setBackground(Color.red);
            panelCentral.add(panelC);
            add(panelCentral, BorderLayout.CENTER);

            panelSur.setLayout(new GridLayout(3, 4));
            // panelSouth.setBackground(Color.red);
            panelSur.add(buttonDrawNormal);
            panelSur.add(buttonForkJoin);
            panelSur.add(buttonExecutorServices);
            panelSur.add(labelSecuencial);
            panelSur.add(labelForkJoin);
            panelSur.add(labelExecutorServices);
            panelSur.add(buttonClear);
            //panelSouth.add(buttonLimpiar);

            add(panelSur, BorderLayout.SOUTH);
        }

        void addEvents() {
            buttonSelectImage.addActionListener(e -> {
                // image.setIcon(new ImageIcon("/users/ramos/foto3.jpg"));
                JFileChooser fileChooser = new JFileChooser();
                // select only images jpg
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
                fileChooser.setFileFilter(filter);
                if (fileChooser.showOpenDialog(labelImageOne) == JFileChooser.APPROVE_OPTION) {
                    // BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());
                    // labelImagen.setIcon(new ImageIcon(fileChooser.getSelectedFile().toString()));
                    // labelPathImage.setText(fileChooser.getSelectedFile().toString());
                    // System.out.println(fileChooser.getSelectedFile());

                    // Convertir imagen a byte[]
                    try {
                        BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", baos);
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        // Enviar imagen al servidor
                        // enviar solo el nombre del archivo
                        if (server.getImageOne() == null || server.getImageTwo() == null) {
                            server.uploadImage(imageInByte, fileChooser.getSelectedFile().getName());
                            JOptionPane.showMessageDialog(null, "Imagen enviada al servidor", "Información", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Ya se han enviado dos imagenes al servidor", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Recibir imagen del servidor
                        // byte[] imageOutByte = server.getImage();
                        // BufferedImage imgOut = ImageIO.read(new ByteArrayInputStream(imageOutByte));
                        // labelImagen.setIcon(new ImageIcon(imgOut));
                        // labelPathImage.setText(fileChooser.getSelectedFile().toString());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al leer la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            buttonGetImages.addActionListener(e -> {
                try {
                    if (server.getImageOne() != null && server.getImageTwo() != null) {
                        // Recibir imagen del servidor
                        byte[] imageOutByte = server.getImageOne();
                        BufferedImage imgOut = ImageIO.read(new ByteArrayInputStream(imageOutByte));

                        // Escalar imagen width = 150, calcular el height en base al width y seleccionar como imagen de labelImageOne
                        int width = 480;
                        int height = (int) (((double) imgOut.getHeight() / (double) imgOut.getWidth()) * (double) width);
                        Image scaledImage = imgOut.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        int w = scaledImage.getWidth(null) / 2;
                        int h = scaledImage.getHeight(null);
                        BufferedImage bi1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                        Graphics g = bi1.getGraphics();
                        g.drawImage(scaledImage, 0, 0, null);
                        g.dispose();
                        labelImageOne.setIcon(new ImageIcon(bi1));
                        // labelImageOne.setIcon(new ImageIcon(scaledImage));

                        // labelImageOne.setIcon(new ImageIcon(imgOut));
                        // Recibir imagen del servidor
                        imageOutByte = server.getImageTwo();
                        imgOut = ImageIO.read(new ByteArrayInputStream(imageOutByte));
                        // Escalar imagen width = 150, calcular el height en base al width y seleccionar como imagen de labelImageTwo
                        // width = 245;
                        height = (int) (((double) imgOut.getHeight() / (double) imgOut.getWidth()) * (double) width);
                        scaledImage = imgOut.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        // cortar a la mitad la imagen
                        w = scaledImage.getWidth(null) / 2;
                        h = scaledImage.getHeight(null);
                        int s = scaledImage.getWidth(null) - w;
                        BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                        g = bi2.getGraphics();
                        g.drawImage(scaledImage, 0, 0, w, h, s, 0, s + w, h, null);
                        g.dispose();
                        labelImageTwo.setIcon(new ImageIcon(bi2));

                        // combinar bi1 y bi2
                        width = bi1.getWidth() + bi2.getWidth();
                        height = bi1.getHeight();
                        BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        g = bi3.getGraphics();
                        g.drawImage(bi1, 0, 0, null);
                        g.drawImage(bi2, bi1.getWidth(), 0, null);
                        g.dispose();
                        image = bi3;

                        // labelImageTwo.setIcon(new ImageIcon(scaledImage));
                        // labelPathImage.setText(fileChooser.getSelectedFile().toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Faltan imágenes en el servidor", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al leer la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            buttonDrawNormal.addActionListener(e -> {
                labelImageOne.setIcon(new ImageIcon(image));
                labelImageTwo.setIcon(null);
                panelImagen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                Histograma objHistograma = new Histograma();
                long startTime = System.currentTimeMillis();
                int[][] histograma = objHistograma.histograma(image);
                DibujarGrafico objDibujarGrafico = new DibujarGrafico();
                for (int i = 0; i < 5; i++) {
                    int[] histogramaCanal = new int[256];
                    System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                    if (i == 0) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelRed, Color.red);
                    }
                    if (i == 1) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelGreen, Color.green);
                    }
                    if (i == 2) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelBlue, Color.blue);
                    }
                    if (i == 4) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelGray, Color.gray);
                    }
//******************************************************************************************************
                    try {
                        Thread.sleep(20);  // Retraso de 100 milisegundos (0.1 segundos)
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
        }
//******************************************************************************************************
                }
                long endTime = System.currentTimeMillis();
                try {
                    server.setTime(String.valueOf(endTime - startTime), "Secuencial");
                } catch (IOException ex){
                    ex.printStackTrace();
                    
                }
                labelSecuencial.setText("Secuencial: " + (endTime - startTime) + " ms");

            });

            buttonForkJoin.addActionListener(e -> {
                labelImageOne.setIcon(new ImageIcon(image));
                labelImageTwo.setIcon(null);
                panelImagen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                Histograma objHistograma = new Histograma();
                long startTime = System.currentTimeMillis();
                int[][] histograma = objHistograma.histograma(image);
                DibujarGrafico objDibujarGrafico = new DibujarGrafico();
                for (int i = 0; i < 5; i++) {
                    int[] histogramaCanal = new int[256];
                    System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                    if (i == 0) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelRed, Color.red);
                    }
                    if (i == 1) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelGreen, Color.green);
                    }
                    if (i == 2) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelBlue, Color.blue);
                    }
                    if (i == 4) {
                        objDibujarGrafico.crearHistograma(histogramaCanal, panelGray, Color.gray);
                    }

                }
                long endTime = System.currentTimeMillis();
                 try {
                    server.setTime(String.valueOf(endTime - startTime), "ForkJoin");
                } catch (IOException ex){
                    ex.printStackTrace();
                    
                }
                labelForkJoin.setText("ForkJoin: " + (endTime - startTime) + " ms");

            });

            
            buttonExecutorServices.addActionListener(e -> {
                labelImageOne.setIcon(new ImageIcon(image));
                labelImageTwo.setIcon(null);
                panelImagen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                Histograma objHistograma = new Histograma();
                int[][] histograma = objHistograma.histograma(image);
                ExecutorService exec = Executors.newFixedThreadPool(4);
                long startTime = System.currentTimeMillis();
                exec.execute(new Executor(histograma, panelRed, panelGreen, panelBlue, panelGray));

                long endTime = System.currentTimeMillis();
                 try {
                    server.setTime(String.valueOf(endTime - startTime), "Executor");
                } catch (IOException ex){
                    ex.printStackTrace();
                    
                }
                labelExecutorServices.setText("Executor: " + (endTime - startTime) + " ms");

            });

            buttonClear.addActionListener(e -> {
                labelSecuencial.setText("");
                labelForkJoin.setText("");
                labelExecutorServices.setText("");
            });

        }
    }
}
