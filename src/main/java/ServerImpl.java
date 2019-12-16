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

    ServerImpl() throws RemoteException {
        super();
    }
    /**
     * 执行命令
     * @param cmd
     * @return
     * @throws RemoteException
     */
    public String execCmd(String cmd) throws RemoteException {
        if(cmd == null || "".equals(cmd)){
            return "执行命令不能为空";
        }
        cmd = cmd.trim();
        StringBuilder result = new StringBuilder();
        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;
        String os = System.getProperty("os.name");
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
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
        } catch (InterruptedException e) {
            return cmd+" 执行错误，错误信息："+e.getMessage();
        } catch (UnsupportedEncodingException e) {
            return cmd+" 执行错误，错误信息："+e.getMessage();
        } catch (IOException e) {
            return cmd+" 执行错误，错误信息："+e.getMessage();
        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);
            if (process != null) {
                process.destroy();
            }
        }
        if(result == null || "".equals(result)){
            return cmd+" 执行完毕！";
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
}
