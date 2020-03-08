package fr.pa1007.trobotframework.utils;

import fr.pa1007.trobotframework.event.ApplicationSelectedEvent;
import fr.pa1007.trobotframework.info.ModuleInfo;
import fr.pa1007.trobotframework.listener.ModuleLoadedListener;
import org.apache.logging.log4j.Logger;

public abstract class Module implements ModuleLoadedListener {

    /**
     * The info of the module
     */
    protected ModuleInfo infos;

    /**
     * The logger of the module
     */
    protected Logger logger;

    /**
     * The json sent to the app
     */
    protected String appJson;

    /**
     * The port for UDP communication
     */
    protected int port;

    public abstract void appSelected(ApplicationSelectedEvent e);

    public void setInfos(ModuleInfo infos) {
        this.infos = infos;
    }

    public ModuleInfo getInfo() {
        return infos;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getAppJson() {
        return appJson;
    }

    public void setAppJson(String appJson) {
        this.appJson = appJson;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
