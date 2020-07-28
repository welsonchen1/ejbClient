package ejbClient;

import java.util.LinkedList;
import java.util.Properties;

import javax.naming.InitialContext;

import com.acer.scheduler.client.RemoteTask;
import com.acer.scheduler.main.TaskCallerBean;

/**
 * @author Welson.Chen Call jboss remove ejb<br>
 *         填入Remote Bean name ,傳參數即可<br>
 *         環境設定<BR>
 *         Jboss Lib :C:\you Scheduler\jboss-as\client<br>
 *         Add lib to project:C:\you Scheduler\jboss-as\client\jbossall-client.jar<br>
 *         Add lib to project:C:\you Scheduler\jboss-as\server\agbsScheduler\deploy\acer-task-scheduler.jar<br>
 */
public class T1 {

    public static void main(String[] args) {
	String port = "1099";
	String url = "jnp://10.36.236.243:" + port;
	try {
	    TaskCallerBean ts = new TaskCallerBean();
	    Object bean = ts.lookupBean("AgbsSeam/KeyComponentInvoiceJob/remote", url);
	    if ((bean != null) && ((bean instanceof com.acer.scheduler.client.RemoteTask))) {
		RemoteTask castedBean = (RemoteTask) bean;
		String beanName = castedBean.name();
		System.out.println("... beanName = " + beanName + "...");
		LinkedList argus = new LinkedList();
		argus.add("1");
		System.out.println("...call(" + argus + ")...");
		Object returnValue = castedBean.call(argus);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static Object lookupBean(String remoteBean, String jnpUrl) throws Exception {
	System.out.println("lookup " + remoteBean + " using " + jnpUrl + "...");
	Properties properties = new Properties();
	properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
	// properties.put("java.naming.factory.url.pkgs", "org.jboss.naming rg.jnp.interfaces");
	properties.put("java.naming.provider.url", jnpUrl);
	InitialContext context = new InitialContext(properties);
	Object bean = context.lookup(remoteBean);
	return bean;
    }
}
