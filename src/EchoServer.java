import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid EchoServer input\nShould be \n\tjava EchoServer <port_number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try (
				ServerSocket socket = new ServerSocket(portNumber);
				Socket clientSocket = socket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
				BufferedReader incoming = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			String inputLine;
			while ((inputLine = incoming.readLine()) != null) {
				out.println(inputLine);
				System.out.println("Just received and echoed:" + inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

}
