package fr.pa1007.trobotframework;

import com.google.gson.reflect.TypeToken;
import fr.pa1007.trobotframework.event.ModuleLoadedEvent;
import fr.pa1007.trobotframework.info.ModuleInfo;
import fr.pa1007.trobotframework.manager.EventManager;
import fr.pa1007.trobotframework.manager.ModuleManager;
import fr.pa1007.trobotframework.server.ServerUDP;
import fr.pa1007.trobotframework.utils.Module;
import fr.pa1007.trobotframework.utils.Utils;
import fr.pa1007.trobotframework.utils.json.App;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loader extends Thread {

    private static final String PATH = "modules/";

    private static final String MODULE_FILE = "module.json";
    private static final String MOBILE_FILE = "app.json";


    private Logger log;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public Loader() {
        log = Utils.getLogger();
    }

    @Override
    public void run() {
        log.info("Module search started");
        try {
            Files.walk(Paths.get(PATH), 1).filter(Loader::testJar).forEach(path -> {
                BufferedReader bufferedReaderMOD;
                BufferedReader bufferedReaderAPP;
                ModuleInfo     moduleInfo;
                log.printf(Level.TRACE, "Start loading %s ........", path);
                log.trace("Acquiring "
                          + MODULE_FILE + " and " + MOBILE_FILE
                          + " from file "
                          + path.toFile().getName()
                          + " .......");

                String file = path.toFile().getAbsolutePath();
                try {
                    if (!file.startsWith("/")) {
                        file = "/" + file;
                    }
                    URL              inputURL = new URL("jar:file:" + file + "!/" + MODULE_FILE);
                    JarURLConnection conn     = (JarURLConnection) inputURL.openConnection();
                    bufferedReaderMOD = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    URL              url     = new URL("jar:file:" + file + "!/" + MOBILE_FILE);
                    JarURLConnection connMob = (JarURLConnection) url.openConnection();
                    bufferedReaderAPP = new BufferedReader(new InputStreamReader(connMob.getInputStream()));

                }
                catch (IOException e) {
                    log.error("Error while loading the module info", e);
                    return;
                }
                log.trace("Loading complete, start reading " + MODULE_FILE);
                moduleInfo = readModuleInfo(bufferedReaderMOD);
                if (moduleInfo.isIncomplete()) {
                    log.warn(String.format("%s not complete, module not loaded", MODULE_FILE));
                    return;
                }
                log.trace(String.format("Loading complete of %s now loading %s .....", MODULE_FILE, MOBILE_FILE));
                try {
                    StringBuilder sb = new StringBuilder();
                    while (bufferedReaderAPP.ready()) {
                        sb.append(bufferedReaderAPP.readLine());
                    }
                    String json      = sb.toString();
                    App    jsonValid = Utils.isJSONValid(json, App.class);
                    if (jsonValid == null) {
                        log.warn(String.format("Json from file %s in %s is not valid .", MOBILE_FILE, file));
                        return;
                    }
                    else {
                        if (!jsonValid.getName().equals(moduleInfo.getName())) {
                            log.error(String.format(
                                    "The name from the app.json (%s) is not the same as the module (%s). \n Stopping loading !",
                                    jsonValid.getName(),
                                    moduleInfo.getName()
                            ));
                            return;
                        }
                    }

                    log.trace(String.format("Loading complete of %s now loading .jar .....", MOBILE_FILE));
                    try {
                        ClassLoader loader = new URLClassLoader(new URL[]{path.toUri().toURL()});
                        Class<? extends Module> runClass = loader.loadClass(moduleInfo.getMainClass()).asSubclass(
                                Module.class);
                        Constructor<? extends Module> ctor = runClass.getConstructor();
                        Module                        mod  = ctor.newInstance();
                        EventManager.addListener(ModuleLoadedEvent.class, mod);
                        mod.setInfos(moduleInfo);
                        mod.setLogger(LogManager.getLogger(moduleInfo.getName()));
                        while (json.startsWith("{")) {
                            json = json.substring(1);
                        }
                        int port = ServerUDP.getPort();
                        json = "{ \"port\":\"" + port + "\", " + json;
                        mod.setAppJson(json);
                        mod.setPort(port);
                        ModuleManager.registerModule(mod, moduleInfo);
                    }
                    catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        log.error("There is an error while loading the jar " + path, e);
                        return;
                    }
                    catch (ClassNotFoundException e) {
                        log.error("The file put into the module.json isn't the main class of the jar "
                                  + path, e);
                        return;
                    }
                    catch (ClassCastException e) {
                        log.error("The main class exist but doesn't implement Module");
                        return;
                    }
                    log.info("Loading complete of " + file);
                }
                catch (IOException e) {
                    log.error("Error while loading the app info", e);
                }

            });
        }
        catch (IOException e) {
            log.error("Error while walking the files", e);
        }


    }

    private ModuleInfo readModuleInfo(BufferedReader bufferedReaderMOD) {
        Type clazzListType = new TypeToken<ModuleInfo>() {}.getType();
        return Utils.GSON.fromJson(bufferedReaderMOD, clazzListType);
    }

    private static boolean testJar(Path path) {
        return path.toString().endsWith(".jar");
    }
}
