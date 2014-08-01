/*
 * Copyright 2013 FortSoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.fortsoft.wicket.jade.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author Decebal Suiu
 */
public class Start {

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		int port = Integer.parseInt(System.getProperty("jetty.port", "8081"));
		connector.setPort(port);
        server.addConnector(connector);

		// the orders of handlers is very important!
		/*
		ContextHandler contextHandler = new ContextHandler();
		contextHandler.setContextPath("/res");
		contextHandler.setResourceBase("./upload/images/");
		contextHandler.addHandler(new ResourceHandler());

		server.addHandler(contextHandler);
		*/

		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setServer(server);
		webAppContext.setContextPath("/");
		webAppContext.setWar("src/main/webapp");

		// add virtual host but DON"T FORGET to add stores with these hosts
//		String[] virtualHosts = { "127.0.0.1", "127.0.0.2" };
//		webAppContext.setVirtualHosts(virtualHosts);

		server.setHandler(webAppContext);

		// START JMX SERVER
		// MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		// MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		// server.getContainer().addEventListener(mBeanContainer);
		// mBeanContainer.start();

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER (" + port + "), PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			// while (System.in.available() == 0) {
			// Thread.sleep(5000);
			// }
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

}