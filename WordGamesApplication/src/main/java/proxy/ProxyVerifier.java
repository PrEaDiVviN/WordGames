package proxy;

import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import exceptions.InvalidConfigurationException;
import exceptions.NoAvailableProxyException;

import java.util.ArrayList;
import java.util.List;

public class ProxyVerifier {
    private long responseWaitTimeMilliseconds;
    private String href;

    private boolean passingConfiguration() {
        if(this.href != null && responseWaitTimeMilliseconds > 0)
            return true;
        return false;
    }

    public boolean verifyProxy(Proxy proxy) throws InvalidConfigurationException {
        if(!passingConfiguration())
            throw new InvalidConfigurationException("The configuration is not set properly! See href and responseWaitTimeMilliseconds!");
        UserAgent userAgent = new UserAgent();
        userAgent.settings.responseTimeout = responseWaitTimeMilliseconds;
        userAgent.setProxyHost(proxy.getIp());
        userAgent.setProxyPort(proxy.getPort());
        try {
            userAgent.visit(href);
        } catch (ResponseException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Proxy> filterActiveProxies(List<Proxy> toVerifyList) throws NoAvailableProxyException {
        //cream lista de proxiuri
        List<Proxy> activeProxies = new ArrayList<>();
        //setam agentul cu detaliile userului
        UserAgent userAgent = new UserAgent();
        userAgent.settings.responseTimeout = responseWaitTimeMilliseconds;

        //parcurgem lista si le adaugam doar pe cele care sunt valabile
        for (int i = 0; i < toVerifyList.size(); i++) {
            boolean adauga = false;
            try {
                userAgent.setProxyHost(toVerifyList.get(i).getIp());
                userAgent.setProxyPort(toVerifyList.get(i).getPort());
                userAgent.visit(href);
                adauga = true;
            } catch (ResponseException e) {
                //e.printStackTrace();
            }
            if(adauga)
                activeProxies.add(toVerifyList.get(i));
        }
        if(activeProxies.size() > 0)
            return activeProxies;
        throw new NoAvailableProxyException("All the proxies in the list are invalid for HTTP tunneling!");
    }

    public ProxyVerifier(long responseWaitTimeMilliseconds, String href) {
        this.responseWaitTimeMilliseconds = responseWaitTimeMilliseconds;
        this.href= href;
    }


    public long getResponseWaitTimeMilliseconds() {
        return responseWaitTimeMilliseconds;
    }

    public void setResponseWaitTimeMilliseconds(long responseWaitTimeMilliseconds) {
        this.responseWaitTimeMilliseconds = responseWaitTimeMilliseconds;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static void main(String[] args) {
        ProxyVerifier proxyVerifier =  new ProxyVerifier(9000,"https://sin0nime.com/dex/");
        /*
        TEST => ONE PROXY
        try {
           System.out.println("Este valabil proxy-ul? " + proxyVerifier.verifyProxy(new Proxy("188.240.71.213", 3128)));
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        */
        /*
        TEXT => MULTIPLE PROXIES
        List<Proxy> listProxy = new ArrayList<>();
        listProxy.add(new Proxy("188.240.71.213",3128));
        listProxy.add(new Proxy("51.178.220.22", 80));
        listProxy.add(new Proxy("34.122.40.247", 3128));
        listProxy.add(new Proxy("51.178.220.22", 83));
        List<Proxy> availableProxies = null;
        try {
            availableProxies = proxyVerifier.filterActiveProxies(listProxy);
        } catch (NoAvailableProxyException e) {
            e.printStackTrace();
        }
        for (Proxy proxy: availableProxies) {
            System.out.println(proxy.getIp() + ":" + proxy.getPort());
        }
         */
    }
}
