package com.geode.core;

import com.geode.core.controller.Controller;
import com.geode.core.reflections.Singleton;
import com.geode.exceptions.GeodeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ControllerManager implements Initializable, Closeable, Updateable {

    private static final Logger logger = LogManager.getLogger(ControllerManager.class);

    private static ControllerManager instance;

    private final List<Controller> controllers = new ArrayList<>();
    private int controllerNumber = 0;

    public ControllerManager() throws GeodeException {
        if(instance == null)
            instance = this;
        else throw new GeodeException("controller manager is a singleton");
    }

    public static ControllerManager getInstance() {
        return instance;
    }

    @Override
    public void init() throws GeodeException {
        for(int i = 0; i <= GLFW.GLFW_JOYSTICK_LAST; i++) {
            controllers.add(null);
            declareController(i);
        }
    }

    public void declareController(int jid) {
        Controller controller = new Controller(jid);
        if(controller.isPresent() && controllers.get(jid) == null) {
            connectController(jid);
        }
    }

    public void connectController(int jid) {
        Controller controller = new Controller(jid);
        controllers.set(jid, controller);
        controllerNumber++;
        logger.info("controller connected: {}:{}", jid, controller.getJoystickName());
    }

    public void disconnectController(int jid) {
        logger.info("controller disconnected: {}", jid);
        controllers.set(jid, null);
        controllerNumber--;
    }

    public Controller getController(int jid) {
        return controllers.get(jid);
    }

    public Controller getAvailableController() {
        Optional<Controller> controller = controllers.stream().filter(Controller::isPresent).findFirst();
        return controller.orElse(null);
    }

    public boolean hasControllers() {
        return controllerNumber > 0;
    }

    public int getControllerNumber() {
        return controllerNumber;
    }

    @Override
    public void close() throws Exception {
        controllers.clear();
        controllerNumber = 0;
    }

    @Override
    public void update() {
        for(Controller controller : controllers) {
            if(controller != null)
                controller.update();
        }
    }
}
