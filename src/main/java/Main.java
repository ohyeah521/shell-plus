import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Title: Main
 * Descrption: 服务端
 * Date:2019-12-15 23:08
 * Email:woo0nise@gmail.com
 * Company:www.j2ee.app
 *
 * @author R4v3zn
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        if(args == null || args.length < 2){
            System.out.println(Main.class.getName()+" <port> <ip>");
            System.exit(-1);
            return;
        }
        Integer port = Integer.parseInt(args[0]);
        String ip = args[1];
        System.setProperty("java.rmi.server.hostname",ip);
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(port);
            Server rmiServer = new ServerImpl();
            registry.bind("exec", rmiServer);
            System.out.println("Run server ok, server host --> "+ip+":"+port);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
