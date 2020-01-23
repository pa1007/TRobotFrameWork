package fr.pa1007.trobotframework.utils;

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

    public void setInfos(ModuleInfo infos) {
        this.infos = infos;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setAppJson(String appJson) {
        this.appJson = appJson;
    }

    public ModuleInfo getInfo() {
        return infos;
    }
}
