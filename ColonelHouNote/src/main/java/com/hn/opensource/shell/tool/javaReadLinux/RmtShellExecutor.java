package com.svn.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RmtShellExecutor {

private Connection conn;
    
    private String     ip;
    
    private String     usr;
    
    private String     psword;
    private String     charset = Charset.defaultCharset().toString();

    private static final int TIME_OUT = 1000 * 5 * 60;

    
    public RmtShellExecutor(ShellParam param) {
        this.ip = param.getIp();
        this.usr = param.getUsername();
        this.psword = param.getPassword();
    }

    
    public RmtShellExecutor(String ip, String usr, String ps) {
        this.ip = ip;
        this.usr = usr;
        this.psword = ps;
    }

    
    private boolean login() throws IOException {
        conn = new Connection(ip);
        conn.connect();
        return conn.authenticateWithPassword(usr, psword);
    }

    
    public int exec(String cmds) throws Exception {
        InputStream stdOut = null;
        InputStream stdErr = null;
        String outStr = "";
        String outErr = "";
        int ret = -1;
        try {
            if (login()) {
                // Open a new {@link Session} on this connection
                Session session = conn.openSession();
                // Execute a command on the remote machine.
                session.execCommand(cmds);
                
                stdOut = new StreamGobbler(session.getStdout());
                outStr = processStream(stdOut, charset);
                
                stdErr = new StreamGobbler(session.getStderr());
                outErr = processStream(stdErr, charset);
                
                session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
                
               /* System.out.println("outStr=" + outStr);
                System.out.println("outErr=" + outErr);*/
                
                ret = session.getExitStatus();
            } else {
                throw new Exception("登录远程机器失败" + ip); // 自定义异常类 实现略
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            /*IOUtils.closeQuietly(stdOut);
            IOUtils.closeQuietly(stdErr);*/
        }
        return ret;
    }

    
    private String processStream(InputStream in, String charset) throws Exception {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }
  
    public void auth_r() throws Exception{
    	RmtShellExecutor exe = new RmtShellExecutor("10.2.48.169", "saf", "saf");
        // 执行mys.sh 参数为java Know dummy
       exe.exec("sh /home/saf/myshell/auth_r.sh");
    }
    
    public void auth_rw() throws Exception{
    	RmtShellExecutor exe = new RmtShellExecutor("10.2.48.169", "saf", "saf");
        // 执行mys.sh 参数为java Know dummy
       exe.exec("sh /home/saf/myshell/auth_rw.sh");
    }
    
    
    public void auth_w() throws Exception{
    	RmtShellExecutor exe = new RmtShellExecutor("10.2.48.169", "saf", "saf");
        // 执行mys.sh 参数为java Know dummy
       exe.exec("sh /home/saf/myshell/auth_w.sh");
    }
    
    public void auth_none() throws Exception{
    	RmtShellExecutor exe = new RmtShellExecutor("10.2.48.169", "saf", "saf");
        // 执行mys.sh 参数为java Know dummy
       exe.exec("sh /home/saf/myshell/auth_none.sh");
    }
    
    
    
    public static void main(String args[]) throws Exception {
        RmtShellExecutor exe = new RmtShellExecutor("10.2.48.169", "saf", "saf");
        // 执行mys.sh 参数为java Know dummy
        System.out.println(exe.exec("sh /home/saf/myshell/test.sh"));
    }


}
