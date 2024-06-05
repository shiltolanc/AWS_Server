import java.io.*;
import java.net.*;
import java.util.Random;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().split(" ");

        String serverAddress = input[0];
        int serverPort = Integer.parseInt(input[1]);
        //int year = Integer.parseInt(input[2]);

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server at " + serverAddress + ":" + serverPort);

            // Create input and output streams for communication
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            int exit = 0;
            while (true) {

                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
                if(exit > 0){
                    break;
                }

                System.out.print("Which date? (or 'exit' to quit): ");
                message = userInput.readLine();
                int year = Integer.parseInt(message);
                if(year < 1950 || year > 2009){
                    year = new Random().nextInt(1950,2009);
                    message = String.valueOf(year);
                    System.out.println("Specified year out of range (1950-2009) using random date instead: " + year);
                }

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(message);

                exit++;
            }

            System.out.print("Completed query, exiting client");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
