import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class FileSharer {
    private HashMap<Integer,String > availableFiles;

    public FileSharer(){
        availableFiles=new HashMap<>();
    }
    public int offerFile(String filePath){
        int port;
         while(true){
            port=UploadUtils.generateCode();
           if( !availableFiles.containsKey(port)){
                availableFiles.put(port, filePath);
                return port;

            }
         }
    }

    public void startFileSharer(int port){
        String filePath=availableFiles.get(port);
        if(filePath==null){
            System.out.println("No file is associated with  port: "+port);
            return;
        }
        try (ServerSocket serverSocket=new ServerSocket(port)){
            System.out.println("Serving file "+new File(filePath).getName()+" ON PORT "+port);           
            Socket clientSocket =new ServerSocket().accept(); 
            System.out.println("Client connection:"+clientSocket.getInetAddress());
            new Thread(new FileSenderHandler(clientSocket,filePath)).start();
        } catch (IOException e) {
            // TODO: handle exception
            System.err.println("ERROR HANDLING FILE SERVER ON PORT "+port);
        }
    }
    private static class FileSenderHandler implements  Runnable{
        private final Socket clientSocket;
        private final String filePath;
       public FileSenderHandler(Socket clienSocket,String filePath){
            this.clientSocket=clienSocket;
            this.filePath=filePath;
        }
        @Override
        public void run() {
            try(FileInputStream fis=new FileInputStream(filePath)) {
                    OutputStream oos=clientSocket.getOutputStream();
                    String fileName=new File(filePath).getName();
                    String header= "Filename: "+fileName+"\n";
                    oos.write(header.getBytes());
                    byte buffer[]=new byte[4096];
                    int byteRead;
                    while ((byteRead=fis.read(buffer))!=-1) {
                        oos.write(buffer,0,byteRead);                        
                    }
                    System.out.println("File "+fileName+ " sent to "+clientSocket.getInetAddress());

            } catch (IOException e) {
System.err.println("Error while sending fil to the cleint "+e.getMessage());  
          }
          finally{
            try {
                clientSocket.close();
            } catch (Exception e) {
                System.err.println("Error closing socket :"+e.getMessage()); 
            }
          }
            
        }
    }

}
