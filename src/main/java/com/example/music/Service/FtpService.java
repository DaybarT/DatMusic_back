package com.example.music.Service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FtpService {

    private FTPClient ftpClient;

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.pass}")
    private String pass;

    // Conectar al servidor FTP
    public boolean connectToFTP() {
      
        boolean success = false;
        try {

            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            System.out.println("Conectado al servidor FTP: " + ftpClient);

            success = true;

        } catch (IOException e) {
            System.err.println("Error conectando al servidor FTP: " + e.getMessage());
        }

        return success;
    }

    // Subir un archivo
    public void uploadFile(String id,MultipartFile localFilePath){
        String fileName = "";
        try {
            
            // Obtener el InputStream del archivo subido
            InputStream inputStream = localFilePath.getInputStream();
            
            String data = localFilePath.getContentType();
            System.out.println(data);
            if ("audio/mpeg".equals(data)){

                fileName = id + ".mp3";

                ftpClient.changeWorkingDirectory("/audio");
            }
            else if ("image/jpeg".equals(data)) {
                fileName = id + ".jpeg";

                ftpClient.changeWorkingDirectory("/image");
            }
            else{
                throw new IOException("Tipo de dato no soportado: " + data);
            }

            boolean done = ftpClient.storeFile(fileName, inputStream);

            if (done) {
                System.out.println("El archivo se ha subido con éxito.");
                inputStream.close();

            } else {
                System.out.println("Error al subir el archivo.");
            }
        }catch (IOException e) {
            System.err.println("Error al subir el archivo: " + e.getMessage());
        }
    }

    // Descargar un archivo
    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            if (success) {
                System.out.println("El archivo se ha descargado con éxito.");
            } else {
                System.out.println("Error al descargar el archivo.");
            }
        }
    }

    // Cerrar la conexión FTP
    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("Desconectado del servidor FTP");
                System.out.println(ftpClient);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void deleteSong(String id){
        try {
             // Cambiar al directorio /audio
             if (ftpClient.changeWorkingDirectory("/audio")) {
                System.out.println("Estoy en el directorio /audio.");
                System.out.println(Arrays.asList(ftpClient.listFiles()));
            }

               
        } catch (Exception e) {
            System.err.println("Error fatal al eliminar: " + e.getMessage());

        }
    }

}
