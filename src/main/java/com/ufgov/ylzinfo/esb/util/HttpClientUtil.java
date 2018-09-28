//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ufgov.ylzinfo.esb.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class HttpClientUtil {
    private static MultiThreadedHttpConnectionManager connectionManager;
    private static HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
    public static final int MAX_TOTAL_CONNECTIONS = 128;
    public static final int MAX_ROUTE_CONNECTIONS = 32;
    public static final int CONNECT_TIMEOUT = 540000;
    public static final int READ_TIMEOUT = 540000;

    static {
        connectionManagerParams.setDefaultMaxConnectionsPerHost(32);
        connectionManagerParams.setMaxTotalConnections(128);
        connectionManagerParams.setBooleanParameter("http.protocol.expect-continue", false);
        connectionManagerParams.setParameter("Connection", "close");
        connectionManagerParams.setParameter("http.connection.timeout", 540000);
        connectionManagerParams.setParameter("http.socket.timeout", 540000);
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(connectionManagerParams);
    }

    public HttpClientUtil() {
    }

    public static HttpClient getHttpClient() {
        return new HttpClient(connectionManager);
    }
}
