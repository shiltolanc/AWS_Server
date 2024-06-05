import java.io.*;
import java.net.*;

public class HealthCheck {
    static Socket s;
    public static void main(String[] args) throws IOException {
        while (true){
            s =  new Socket("localhost", 1234);
            s.close();
        }
    }
}
