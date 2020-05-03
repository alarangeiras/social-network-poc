package br.com.allanlarangeiras.socialnetwork.util;

public class TestUtil {

    private final String host;
    private final int port;

    public TestUtil(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String createUrlWithPort(String relativeUrl) {
        return new StringBuilder()
                .append(host)
                .append(port)
                .append(relativeUrl)
                .toString();
    }

}
