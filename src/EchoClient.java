import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Invalid EchoClient input\nShould be \n\tjava EchoClient <host_name> <port_number>");
			System.exit(1);
		}
		
		// The server host name and port number are passed as arguments in the function call from CL
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		
		// Try-with-resources statement automatically closes all declared resources to prevent leaks
		try(	
				Socket echoSocket = new Socket(hostName, portNumber);	// The socket to connect with the server
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);	// The utility we transmit through the socket with
				
				// InputStreamReader is bytes->characters, BufferedReader provides more efficient reading of these characters (through use of a buffer)
				BufferedReader incoming = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));	// The utility we read through the socket with
				BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));	// How the user inputs the String to send
				) {
			
			String userInput;
			while((userInput = userInputStream.readLine()) != null) {
				out.println(userInput);
				System.out.println("\nOutput:" + userInput);
				System.out.println("Receiving:" + incoming.readLine());
			}
			
			System.out.println("Connection terminating...");
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
		
		System.out.println("Program terminating...");
	}

}
