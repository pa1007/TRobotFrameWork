package fr.pa1007.trobotframework;

import fr.pa1007.trobotframework.event.ApplicationSelectedEvent;
import fr.pa1007.trobotframework.exception.ModuleNotFoundException;
import fr.pa1007.trobotframework.manager.ModuleManager;
import fr.pa1007.trobotframework.server.ServerTCP;
import fr.pa1007.trobotframework.utils.Module;
import fr.pa1007.trobotframework.utils.Utils;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class Connexion {

    private static final int    PORT = 5565;
    private static       Logger logger;

    public static void main(String[] args) {
        logger = Utils.getLogger();

        logger.debug("Starting Framework ...");

        Loader l = new Loader();
        l.start();

        Thread t = new Thread(() -> {
            try {
                ServerTCP tcp = new ServerTCP(PORT);
                tcp.startConnection();
                tcp.waitMessage();
                tcp.sendMessage(ModuleManager.generateJSONApp());

                while (true) {
                    if (tcp.isOpen()) {
                        String s = tcp.waitMessage();
                        if (s == null){
                            tcp.stopCurrent();
                        }
                        try {
                            Module module =
                                    ModuleManager.getModules().stream().filter(mod -> mod.getInfo().getName().equals(s)).findFirst().orElseThrow(
                                            ModuleNotFoundException::new);
                            new Thread(() -> module.appSelected(new ApplicationSelectedEvent(
                                    "Selected Server",
                                    null,
                                    module
                            ))).start();
                        }
                        catch (ModuleNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        tcp.startConnection();
                        tcp.waitMessage();
                        tcp.sendMessage(ModuleManager.generateJSONApp());
                    }
                }
            }
            catch (IOException | InterruptedException e) {
                logger.error(e);
            }
        });
        t.start();


    }


}
