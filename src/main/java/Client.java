import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Title: Client
 * Descrption: 连接工具
 * Date:2019-12-15 23:51
 * Email:woo0nise@gmail.com
 * Company:www.j2ee.app
 *
 * @author R4v3zn
 * @version 1.0.0
 */
public class Client {

    public static void main(String[] args) throws RemoteException {
        if(args ==null ||args.length < 3){
            System.out.println(Client.class.getName()+" <port> <ip> <name>");
            System.exit(-1);
            return;
        }
        Integer port = Integer.parseInt(args[0]);
        String ip = args[1];
        String name = args[2];
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(ip,port);
            System.out.println("[*] name --> "+name+" host --> "+ip+":"+port+" client ok!");
        } catch (RemoteException e) {
            System.err.println("[-] name --> "+name+" host --> "+ip+":"+port+" client error!");
            System.exit(-1);
            return;
        }
        Server server = null;
        try {
            server = (Server)registry.lookup(name);
            System.out.println("[*] load --> "+name+" ok!");
        } catch (RemoteException e) {
            System.err.println("[*] load --> "+name+" error!");
            System.exit(-1);
            return;
        } catch (NotBoundException e) {
            System.err.println("[*] load --> "+name+" error, "+name+" not found!");
            System.exit(-1);
            return;
        }
        while (true){
            System.out.print("[*] please input cmd : ");
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                String cmd = scanner.nextLine();
                System.out.println(server.execCmd(cmd));
            }
        }
    }
}
