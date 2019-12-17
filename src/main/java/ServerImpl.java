import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Title: ServerImpl
 * Descrption: TODO
 * Date:2019-12-15 23:11
 * Email:woo0nise@gmail.com
 * Company:www.j2ee.app
 *
 * @author R4v3zn
 * @version 1.0.0
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private static String pwd = null;
    ServerImpl(String pwd) throws RemoteException {
        super();
        this.pwd = pwd;
    }


    /**
     * 执行命令
     * @param cmd 执行命令
     * @param clientPwd 连接密码
     * @param  clientOs 客户端操作系统
     * @return
     * @throws RemoteException
     */
    public String execCmd(String cmd, String clientPwd, String clientOs) throws RemoteException {
        if(cmd == null || "".equals(cmd)){
            return "commond not null";
        }
        if(pwd != null && !pwd.equals(clientPwd)){
            return "Incorrect password";
        }
        cmd = cmd.trim();
        StringBuilder result = new StringBuilder();
        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;
        String os = System.getProperty("os.name");
        os = os.toLowerCase();
        if(os.contains("win")){
            if(cmd.contains("ping") && !cmd.contains("-n")){
                cmd += " -n 4";
            }
        }else{
            if(cmd.contains("ping") && !cmd.contains("-n")){
                cmd += " -t 4";
            }
        }
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            if(clientOs != null){
                clientOs = clientOs.toLowerCase();
            }else{
                clientOs = "";
            }
            if(clientOs.contains("win")){
                bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
                bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"));
            }else{
                bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            }
            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
        } catch (InterruptedException e) {
            String msg = strConver(e.getMessage(),clientOs);
            return cmd+" execute error,msg："+msg;
        } catch (UnsupportedEncodingException e) {
            String msg = strConver(e.getMessage(),clientOs);
            return cmd+" execute error,msg："+msg;
        } catch (IOException e) {
            return cmd+" execute error,msg: not found commond";
        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);
            if (process != null) {
                process.destroy();
            }
        }
        if(result == null || "".equals(result)){
            return cmd+" execute ok！";
        }else{
            return result.toString();
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
            }
        }
    }

    static String strConver(String str, String os){
        String msg = "";
        if(os.contains("win")){
            try {
                msg = new String(str.getBytes(),"GBK");
            } catch (UnsupportedEncodingException ex) {
                msg = str;
            }
        }else{
            try {
                msg = new String(str.getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                msg = str;
            }
        }
        return msg;
    }
}
