import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

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
            System.out.println(Main.class.getName()+" <port> <ip> [pwd]");
            System.exit(-1);
            return;
        }
        Integer port = Integer.parseInt(args[0]);
        String ip = args[1];
        String pwd = "";
        try {
            pwd = args[2];
            pwd = pwd.trim();
        }catch (Exception e){}
        System.setProperty("java.rmi.server.hostname",ip);
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(port);
            String clientName = getRandomString(16);
            Server rmiServer = new ServerImpl(pwd);
            registry.bind(clientName, rmiServer);
            System.out.println("Run server ok, server host --> "+ip+":"+port+ " name --> "+clientName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
