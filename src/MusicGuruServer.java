import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    static Map<Integer, ArrayList<String>> musicData = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        int portNumber = 5000;

        parseData();

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle the client
                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // Create input and output streams for communication
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println("1950 - 2009");
                int year = Integer.parseInt(in.readLine());

                //out.println("The 5th song from " + year + " was I'm Just Ken");
                out.println(getSong(year));
                System.out.println("Client requested top song");

                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves random song from the top 10 of the selected year
     *
     * @param year
     * @return
     */
    public static String getSong(int year) throws UnknownHostException {
        Scanner sc = new Scanner(musicData.get(year).get(new Random().nextInt(9) + 1));
        sc.useDelimiter("[\\s.]+");

        String output = "In " + year + " the number " + sc.nextInt() + " song was ";
        while(sc.hasNext()){
            output += sc.next() + " ";
        }

        return output + " (" + InetAddress.getLocalHost().getHostAddress() + ") ";
    }

    /**
     * Parses the music data from txt file into a hashmap
     *
     * @throws FileNotFoundException
     */
    public static void parseData() throws FileNotFoundException {
        File data = new File("./Top 10 hits.txt");
        Scanner sc = new Scanner(data);

        while(sc.hasNextInt()){
            int year = sc.nextInt();
            ArrayList<String> list = new ArrayList<>();
            while(!sc.hasNextInt() && sc.hasNextLine()){
                list.add(sc.nextLine());
            }
            musicData.put(year, list);
        }

    }
}
