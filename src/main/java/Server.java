import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Title: Server
 * Descrption: TODO
 * Date:2019-12-15 23:09
 * Email:woo0nise@gmail.com
 * Company:www.j2ee.app
 *
 * @author R4v3zn
 * @version 1.0.0
 */
public interface Server extends Remote {

    /**
     * 执行命令
     * @param cmd 执行命令
     * @param clientPwd 连接密码
     * @param  clientOs 客户端操作系统
     * @return
     * @throws RemoteException
     */
    String execCmd(String cmd, String clientPwd, String clientOs) throws RemoteException;
}
