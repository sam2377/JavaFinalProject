package chatAddDb;


public class MyServerSocket {
    public static void main(String[] args) {
    	System.out.println("Server is Start");
        new ServerListener().start();  
    }
}
