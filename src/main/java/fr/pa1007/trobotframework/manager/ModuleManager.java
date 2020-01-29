package fr.pa1007.trobotframework.manager;

import fr.pa1007.trobotframework.event.ModuleLoadedEvent;
import fr.pa1007.trobotframework.info.ModuleInfo;
import fr.pa1007.trobotframework.utils.Module;
import fr.pa1007.trobotframework.utils.Utils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class ModuleManager {

    private static final Map<Module, ModuleInfo> registeredModule = Collections.synchronizedMap(new HashMap<>());
    private static final Logger                  logger           = Utils.getLogger();

    private static List<String> appsInfo = new ArrayList<>();

    public static List<Module> getModules() {
        return new ArrayList<>(registeredModule.keySet());
    }

    public static void registerModule(Module module, ModuleInfo info) {
        logger.trace("Loading ... module");

        if (!registeredModule.containsValue(info)) {

            if (!registeredModule.containsKey(module)) {
                registeredModule.put(module, info);

                appsInfo.add(module.getAppJson());

                EventManager.forceTrigger(new ModuleLoadedEvent(info), module);
            }
            else {
                module.getLogger().printf(
                        Level.ERROR,
                        "Trying to register a module with main class already in it %s  ,  %s ",
                        info,
                        module
                );
            }
        }
        else {
            logger.printf(Level.ERROR, "Trying to register a module with already seen infos %s", info);
        }
    }

    /**
     * {
     * "Module":{
     * "name":"test",
     * "usedPage":[
     * {
     * "name":"movement page",
     * "page":{
     * "type":"Joystick",
     * "buttons":{
     * "10":{
     * "display":"D",
     * "send":"D"
     * },
     * "11":{
     * "display":"E",
     * "send":"E"
     * },
     * "12":{
     * "display":"F",
     * "send":"F"
     * },
     * "20":{
     * "display":"G",
     * "send":"G"
     * },
     * "21":{
     * "display":"START",
     * "send":"START"
     * },
     * "22":{
     * "display":"STOP",
     * "send":"STOP"
     * },
     * "00":{
     * "display":"A",
     * "send":"A"
     * },
     * "01":{
     * "display":"B",
     * "send":"B"
     * },
     * "02":{
     * "display":"C",
     * "send":"C"
     * }
     * }
     * }
     * },
     * {
     * "name":"Video view",
     * "page":{
     * "type":"Video",
     * "url":"test.fr"
     * }
     * }
     * ]
     * }
     * }
     *
     * @return
     */
    public synchronized static String generateJSONApp() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"Module\":{\"name\":\"RaspConnector\",\"usedPage\":[");
        for (int i = 0; i < appsInfo.size(); i++) {
            String s = appsInfo.get(i);
            sb.append(s);
            if (i != appsInfo.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]}}");
        return sb.toString();
    }
}
