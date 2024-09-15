package com.geode.core;

import com.geode.core.winevents.mandatory.MandatoryFrameBufferSizeCallback;
import com.geode.core.winevents.mandatory.MandatoryJoystickConnectedCallback;
import com.geode.core.winevents.mandatory.MandatoryJoystickDisconnectedCallback;
import com.geode.core.winevents.mandatory.MandatoryKeyCallback;
import com.geode.core.reflections.Singleton;

import com.geode.core.winevents.*;
import com.geode.exceptions.GeodeException;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class WindowEventsManager implements Initializable, Closeable {

    private static WindowEventsManager instance;
    private final Window window;
    private final OnWindowSizeEventHandler windowSizeEventHandler;
    private final OnWindowCloseEventHandler windowCloseEventHandler;
    private final OnWindowFramebufferSizeEventHandler windowFramebufferSizeEventHandler;
    private final OnWindowContentScaleEventHandler windowContentScaleEventHandler;
    private final OnWindowPosEventHandler windowPosEventHandler;
    private final OnWindowIconifyEventHandler windowIconifyEventHandler;
    private final OnWindowMaximizedEventHandler windowMaximizedEventHandler;
    private final OnWindowFocusedEventHandler windowFocusedEventHandler;
    private final OnWindowRefreshEventHandler windowRefreshEventHandler;
    private final OnWindowDropEventHandler windowDropEventHandler;
    private final OnWindowCursorEnterEventHandler windowCursorEnterEventHandler;
    private final OnWindowCursorPosEventHandler windowCursorPosEventHandler;
    private final OnWindowScrollEventHandler windowScrollEventHandler;
    private final OnWindowCharEventHandler windowCharEventHandler;
    private final OnWindowKeyEventHandler windowKeyEventHandler;
    private final OnWindowMonitorEventHandler windowMonitorEventHandler;
    private final OnWindowMouseEventHandler windowMouseEventHandler;
    private final OnWindowJoystickEventHandler windowJoystickEventHandler;
    private final List<WindowCallbacksHandler> handlers = new ArrayList<>();

    public WindowEventsManager(Window window) throws GeodeException {
        if(instance == null)
            instance = this;
        else
            throw new GeodeException("WindowEventsManager is a singleton");
        this.window = window;
        windowSizeEventHandler = new OnWindowSizeEventHandler(this);
        windowCloseEventHandler = new OnWindowCloseEventHandler(this);
        windowFramebufferSizeEventHandler = new OnWindowFramebufferSizeEventHandler(this);
        windowContentScaleEventHandler = new OnWindowContentScaleEventHandler(this);
        windowPosEventHandler = new OnWindowPosEventHandler(this);
        windowIconifyEventHandler = new OnWindowIconifyEventHandler(this);
        windowMaximizedEventHandler = new OnWindowMaximizedEventHandler(this);
        windowFocusedEventHandler = new OnWindowFocusedEventHandler(this);
        windowRefreshEventHandler = new OnWindowRefreshEventHandler(this);
        windowDropEventHandler = new OnWindowDropEventHandler(this);
        windowCursorEnterEventHandler = new OnWindowCursorEnterEventHandler(this);
        windowCursorPosEventHandler = new OnWindowCursorPosEventHandler(this);
        windowScrollEventHandler = new OnWindowScrollEventHandler(this);
        windowCharEventHandler = new OnWindowCharEventHandler(this);
        windowKeyEventHandler = new OnWindowKeyEventHandler(this);
        windowMonitorEventHandler = new OnWindowMonitorEventHandler(this);
        windowMouseEventHandler = new OnWindowMouseEventHandler(this);
        windowJoystickEventHandler = new OnWindowJoystickEventHandler(this);
        handlers.add(windowSizeEventHandler);
        handlers.add(windowCloseEventHandler);
        handlers.add(windowFramebufferSizeEventHandler);
        handlers.add(windowContentScaleEventHandler);
        handlers.add(windowPosEventHandler);
        handlers.add(windowIconifyEventHandler);
        handlers.add(windowMaximizedEventHandler);
        handlers.add(windowFocusedEventHandler);
        handlers.add(windowRefreshEventHandler);
        handlers.add(windowDropEventHandler);
        handlers.add(windowCursorEnterEventHandler);
        handlers.add(windowCursorPosEventHandler);
        handlers.add(windowScrollEventHandler);
        handlers.add(windowCharEventHandler);
        handlers.add(windowKeyEventHandler);
        handlers.add(windowMonitorEventHandler);
        handlers.add(windowMouseEventHandler);
        handlers.add(windowJoystickEventHandler);
    }

    public static WindowEventsManager getInstance() {
        return instance;
    }

    private void initMandatoryEvents() {
        onFbSize(new MandatoryFrameBufferSizeCallback(WindowManager.getInstance(), Application.getInstance()));
        onKey(new MandatoryKeyCallback());
        onJoystickConnected(new MandatoryJoystickConnectedCallback());
        onJoystickDisconnected(new MandatoryJoystickDisconnectedCallback());
    }


    @Override
    public void init() throws GeodeException {
        for(WindowCallbacksHandler handler : handlers)
            handler.init();
        initMandatoryEvents();
    }

    @Override
    public void close() throws Exception {
        for(WindowCallbacksHandler handler : handlers)
            handler.close();
        handlers.clear();
    }

    public WindowEventsManager removeByTag(Object tag) {
        for(WindowCallbacksHandler handler : handlers) {
            handler.removeByTag(tag);
        }
        return this;
    }

    public WindowEventsManager onWidth(WinEvents.WidthCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onHeight(WinEvents.HeightCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onSize(WinEvents.SizeCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onClosed(WinEvents.CloseCallback callback) {
        windowCloseEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbWidth(WinEvents.FbWidthCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbHeight(WinEvents.FbHeightCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbSize(WinEvents.FbSizeCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScaleX(WinEvents.ScaleXCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScaleY(WinEvents.ScaleYCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScale(WinEvents.ScaleCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onX(WinEvents.XCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onY(WinEvents.YCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onPosition(WinEvents.PositionCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onIconify(WinEvents.IconifyCallback callback) {
        windowIconifyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onRestore(WinEvents.RestoreCallback callback) {
        windowIconifyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMaximized(WinEvents.MaximizedCallback callback) {
        windowMaximizedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMinimized(WinEvents.MinimizedCallback callback) {
        windowMaximizedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFocused(WinEvents.FocusedCallback callback) {
        windowFocusedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onUnfocused(WinEvents.UnfocusedCallback callback) {
        windowFocusedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onRefresh(WinEvents.RefreshCallback callback) {
        windowRefreshEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onDrop(WinEvents.DropCallback callback) {
        windowDropEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorEntered(WinEvents.CursorEnteredCallback callback) {
        windowCursorEnterEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorExited(WinEvents.CursorExitedCallback callback) {
        windowCursorEnterEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorX(WinEvents.CursorXCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorY(WinEvents.CursorYCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorPos(WinEvents.CursorPosCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScrollX(WinEvents.ScrollXCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScrollY(WinEvents.ScrollYCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScroll(WinEvents.ScrollCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onChar(WinEvents.CharCallback callback) {
        windowCharEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onKey(WinEvents.KeyCallback callback) {
        windowKeyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMonitorConnected(WinEvents.MonitorConnected callback) {
        windowMonitorEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMonitorDisconnected(WinEvents.MonitorDisconnected callback) {
        windowMonitorEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMouse(WinEvents.MouseCallback callback) {
        windowMouseEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onJoystickConnected(WinEvents.JoystickConnectedCallback callback) {
        windowJoystickEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onJoystickDisconnected(WinEvents.JoystickDisconnectedCallback callback) {
        windowJoystickEventHandler.addCallback(callback);
        return this;
    }

    public OnWindowSizeEventHandler getWindowSizeEventHandler() {
        return windowSizeEventHandler;
    }

    public OnWindowCloseEventHandler getWindowCloseEventHandler() {
        return windowCloseEventHandler;
    }

    public OnWindowFramebufferSizeEventHandler getWindowFramebufferSizeEventHandler() {
        return windowFramebufferSizeEventHandler;
    }

    public OnWindowContentScaleEventHandler getWindowContentScaleEventHandler() {
        return windowContentScaleEventHandler;
    }

    public OnWindowPosEventHandler getWindowPosEventHandler() {
        return windowPosEventHandler;
    }

    public OnWindowIconifyEventHandler getWindowIconifyEventHandler() {
        return windowIconifyEventHandler;
    }

    public OnWindowMaximizedEventHandler getWindowMaximizedEventHandler() {
        return windowMaximizedEventHandler;
    }

    public OnWindowFocusedEventHandler getWindowFocusedEventHandler() {
        return windowFocusedEventHandler;
    }

    public OnWindowRefreshEventHandler getWindowRefreshEventHandler() {
        return windowRefreshEventHandler;
    }

    public OnWindowDropEventHandler getWindowDropEventHandler() {
        return windowDropEventHandler;
    }

    public OnWindowCursorEnterEventHandler getWindowCursorEnterEventHandler() {
        return windowCursorEnterEventHandler;
    }

    public OnWindowCursorPosEventHandler getWindowCursorPosEventHandler() {
        return windowCursorPosEventHandler;
    }

    public OnWindowScrollEventHandler getWindowScrollEventHandler() {
        return windowScrollEventHandler;
    }

    public OnWindowCharEventHandler getWindowCharEventHandler() {
        return windowCharEventHandler;
    }

    public OnWindowKeyEventHandler getWindowKeyEventHandler() {
        return windowKeyEventHandler;
    }

    public OnWindowMonitorEventHandler getWindowMonitorEventHandler() {
        return windowMonitorEventHandler;
    }

    public OnWindowMouseEventHandler getWindowMouseEventHandler() {
        return windowMouseEventHandler;
    }

    public OnWindowJoystickEventHandler getWindowJoystickEventHandler() {
        return windowJoystickEventHandler;
    }

    public Window getWindow() {
        return window;
    }
}
