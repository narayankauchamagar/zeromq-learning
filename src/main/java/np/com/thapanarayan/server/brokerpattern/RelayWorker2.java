package np.com.thapanarayan.server.brokerpattern;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

//  Hello World worker
//  Connects REP socket to tcp://*:5560
//  Expects "Hello" from client, replies with "World"
public class RelayWorker2
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("Running Worker 2");
        try (ZContext context = new ZContext()) {
            //  Socket to talk to server
            Socket responder = context.createSocket(SocketType.REP);
            responder.connect("tcp://localhost:5560");

            while (!Thread.currentThread().isInterrupted()) {
                //  Wait for next request from client
                String string = responder.recvStr(0);
                System.out.printf("Received request: [%s]\n", string);

                //  Do some 'work'
                Thread.sleep(1000);

                //  Send reply back to client
                System.out.println("Replying Response for Request: " + string );
                responder.send("World 2");
            }
        }
    }
}
