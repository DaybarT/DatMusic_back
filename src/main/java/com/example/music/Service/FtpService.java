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
    public boolean connectToFTP() throws IOException {
      
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
            throw new IOException("Error conectando al servidor FTP: " + e.getMessage());
        }

        return success;
    }

    // Subir un archivo
    public String uploadFile(String id,MultipartFile localFilePath) throws IOException{
        String fileName = "";
        try {
            
            // Obtener el InputStream del archivo subido
            InputStream inputStream = localFilePath.getInputStream();
            
            String data = localFilePath.getContentType();
            System.out.println(data);
            if (null == data){
                throw new IOException("Tipo de dato no soportado: " + data);
            }
            else switch (data) {
                case "audio/mpeg" -> {
                    fileName = id + ".mp3";
                    ftpClient.changeWorkingDirectory("/audio");
                }
                case "image/jpeg" -> {
                    fileName = id + ".jpeg";
                    ftpClient.changeWorkingDirectory("/image");
                }
                default -> throw new IOException("Tipo de dato no soportado: " + data);
            }

            boolean done = ftpClient.storeFile(fileName, inputStream);

            if (done) {
                inputStream.close();
                return "El archivo se ha subido con éxito.";
                

            } else {
                return"Error al subir el archivo.";
            }
        }catch (IOException e) {
            throw new IOException("Error al subir el archivo: " + e.getMessage());
        }
    }

    // Descargar un archivo
    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException{
        try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            if (success) {
                System.out.println("El archivo se ha descargado con éxito.");
            } else {
                throw new IOException("Error al descargar el archivo.");
            }
        } catch (IOException e) {
            throw new IOException("Ocurrió un error al descargar el archivo: " + e.getMessage());
        }
    }
    

    // Cerrar la conexión FTP
    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                System.out.println("Desconectado del servidor FTP");
                System.out.println(ftpClient);
            } catch (IOException e) {
                throw new IOException("Error al desconectar" + e.getMessage());
            }
        }
    }


    public void deleteSong(String id) throws IOException{
        try {
             // Cambiar al directorio /audio
             if (ftpClient.changeWorkingDirectory("/audio")) {
                System.out.println("Estoy en el directorio /audio.");
                System.out.println(Arrays.asList(ftpClient.listFiles()));
            }

               
        } catch (IOException e) {
            throw new IOException("Error fatal al eliminar:" + e.getMessage());

        }
    }

}
