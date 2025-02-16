import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ClienteFTP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FTPClient cliente = new FTPClient();

        String serverFTP = "192.168.1.190";

        System.out.println("Nos vamos a conectar a " + serverFTP);
        try {
            cliente.connect(serverFTP);

            if(!FTPReply.isPositiveCompletion(cliente.getReplyCode())){
                cliente.disconnect();
                System.out.println("Conexión al servidor rechazada");
                System.exit(0);
            }else{
                System.out.println("Introduzca el nombre de usuario:");
                String usuario = sc.nextLine();

                if(usuario.equals("anonymous")){
                    String clave = "";

                    if(cliente.login(usuario,clave)){
                        System.out.println("Que desea realizar:\n0.Salir\n1.Ver archivos del directorio\n2.Descargar un archivo");
                        int eleccion = sc.nextInt();
                        sc.nextLine();

                        while(eleccion!=0){
                            switch (eleccion){
                                case 1:
                                    cliente.enterLocalPassiveMode();
                                    FTPFile[] listaArchivos = cliente.listFiles();
                                    System.out.println("Archivos del directorio " + cliente.printWorkingDirectory()+ ":");
                                    for(FTPFile file: listaArchivos){
                                        System.out.println(file.getName());
                                    }
                                    break;
                                case 2:
                                    System.out.println("Escriba el nombre del archivo a descargar");
                                    String nombreArchivo = sc.nextLine();

                                    cliente.setFileType(FTPClient.BINARY_FILE_TYPE);
                                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(nombreArchivo));
                                    if(cliente.retrieveFile(nombreArchivo,out)){
                                        System.out.println("Archivo descargado correctamente");
                                    }else{
                                        System.out.println("El archivo no se ha podido descargar o no existe");
                                    }
                                    break;
                                default:
                                    System.out.println("Seleccione una opción correcta");
                                    break;
                            }
                            System.out.println("Que desea realizar:\n0.Salir\n1.Ver archivos del directorio\n2.Descargar un archivo");
                            eleccion = sc.nextInt();
                            sc.nextLine();
                        }
                    }else{
                        cliente.disconnect();
                        System.out.println("Conexión rechazada");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Escriba la contraseña");
                    String clave = sc.nextLine();

                    if(cliente.login(usuario,clave)){
                        System.out.println("\nQue desea realizar:\n0.Salir\n1.Ver archivos del directorio\n2.Descargar un archivo\n3.Cargar un archivo");
                        int eleccion = sc.nextInt();
                        sc.nextLine();

                        while(eleccion!=0){
                            switch (eleccion){
                                case 1:
                                    cliente.enterLocalPassiveMode();
                                    FTPFile[] listaArchivos = cliente.listFiles();
                                    System.out.println("Archivos del directorio " + cliente.printWorkingDirectory()+ ":");
                                    for(FTPFile file: listaArchivos){
                                        System.out.println(file.getName());
                                    }
                                    break;
                                case 2:
                                    System.out.println("Escriba el nombre del archivo a descargar");
                                    String nombreArchivo = sc.nextLine();

                                    cliente.setFileType(FTPClient.BINARY_FILE_TYPE);
                                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(nombreArchivo));
                                    if(cliente.retrieveFile(nombreArchivo,out)){
                                        System.out.println("Archivo descargado correctamente");
                                    }else{
                                        System.out.println("El archivo no se ha podido descargar o no existe");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Escriba el nombre del archivo a cargar");
                                    String nombre = sc.nextLine();

                                    cliente.setFileType(FTPClient.BINARY_FILE_TYPE);
                                    try{
                                        DataInputStream in = new DataInputStream(new FileInputStream(nombre));
                                        cliente.storeFile(nombre,in);
                                    } catch (FileNotFoundException e) {
                                        System.out.println("El archivo no existe");
                                    }
                                    break;
                                default:
                                    System.out.println("Seleccione una opción correcta");
                                    break;
                            }
                            System.out.println("\nQue desea realizar:\n0.Salir\n1.Ver archivos del directorio\n2.Descargar un archivo\n3.Cargar un archivo");
                            eleccion = sc.nextInt();
                            sc.nextLine();
                        }
                    }else{
                        cliente.disconnect();
                        System.out.println("Usuario o contraseña incorrectos");
                        System.exit(0);
                    }
                }

                if (cliente.logout()) {
                    System.out.println("Logout del servidor");
                } else{
                    System.out.println("Error al hacer logout");
                }
                cliente.disconnect();
                System.out.println("Fin de la conexión");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}