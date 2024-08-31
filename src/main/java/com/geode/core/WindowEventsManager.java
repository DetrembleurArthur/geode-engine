package com.geode.core;

import com.geode.core.winevents.*;
import com.geode.exceptions.GeodeException;

public class WindowEventsManager implements Initializable, AutoCloseable {

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

    public WindowEventsManager(Window window) {
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
    }


    @Override
    public void init() throws GeodeException {
        windowSizeEventHandler.init();
        windowCloseEventHandler.init();
        windowFramebufferSizeEventHandler.init();
        windowContentScaleEventHandler.init();
        windowPosEventHandler.init();
        windowIconifyEventHandler.init();
        windowMaximizedEventHandler.init();
        windowFocusedEventHandler.init();
        windowRefreshEventHandler.init();
        windowDropEventHandler.init();
        windowCursorEnterEventHandler.init();
        windowCursorPosEventHandler.init();
        windowScrollEventHandler.init();
        windowCharEventHandler.init();
        windowKeyEventHandler.init();
        windowMonitorEventHandler.init();
        windowMouseEventHandler.init();
        windowJoystickEventHandler.init();
    }

    @Override
    public void close() throws Exception {
        windowSizeEventHandler.close();
        windowCloseEventHandler.close();
        windowFramebufferSizeEventHandler.close();
        windowContentScaleEventHandler.close();
        windowPosEventHandler.close();
        windowIconifyEventHandler.close();
        windowMaximizedEventHandler.close();
        windowFocusedEventHandler.close();
        windowRefreshEventHandler.close();
        windowDropEventHandler.close();
        windowCursorEnterEventHandler.close();
        windowCursorPosEventHandler.close();
        windowScrollEventHandler.close();
        windowCharEventHandler.close();
        windowKeyEventHandler.close();
        windowMonitorEventHandler.close();
        windowMouseEventHandler.close();
        windowJoystickEventHandler.close();
    }

    public WindowEventsManager onWidth(OnWindowSizeEventHandler.WidthCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onHeight(OnWindowSizeEventHandler.HeightCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onSize(OnWindowSizeEventHandler.SizeCallback callback) {
        windowSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onClosed(OnWindowCloseEventHandler.CloseCallback callback) {
        windowCloseEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbWidth(OnWindowFramebufferSizeEventHandler.FbWidthCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbHeight(OnWindowFramebufferSizeEventHandler.FbHeightCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFbSize(OnWindowFramebufferSizeEventHandler.FbSizeCallback callback) {
        windowFramebufferSizeEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScaleX(OnWindowContentScaleEventHandler.ScaleXCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScaleY(OnWindowContentScaleEventHandler.ScaleYCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScale(OnWindowContentScaleEventHandler.ScaleCallback callback) {
        windowContentScaleEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onX(OnWindowPosEventHandler.XCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onY(OnWindowPosEventHandler.YCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onPosition(OnWindowPosEventHandler.PositionCallback callback) {
        windowPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onIconify(OnWindowIconifyEventHandler.IconifyCallback callback) {
        windowIconifyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onRestore(OnWindowIconifyEventHandler.RestoreCallback callback) {
        windowIconifyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMaximized(OnWindowMaximizedEventHandler.MaximizedCallback callback) {
        windowMaximizedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMinimized(OnWindowMaximizedEventHandler.MinimizedCallback callback) {
        windowMaximizedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onFocused(OnWindowFocusedEventHandler.FocusedCallback callback) {
        windowFocusedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onUnfocused(OnWindowFocusedEventHandler.UnfocusedCallback callback) {
        windowFocusedEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onRefresh(OnWindowRefreshEventHandler.RefreshCallback callback) {
        windowRefreshEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onDrop(OnWindowDropEventHandler.DropCallback callback) {
        windowDropEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorEntered(OnWindowCursorEnterEventHandler.CursorEnteredCallback callback) {
        windowCursorEnterEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorExited(OnWindowCursorEnterEventHandler.CursorExitedCallback callback) {
        windowCursorEnterEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorX(OnWindowCursorPosEventHandler.CursorXCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorY(OnWindowCursorPosEventHandler.CursorYCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onCursorPos(OnWindowCursorPosEventHandler.CursorPosCallback callback) {
        windowCursorPosEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScrollX(OnWindowScrollEventHandler.ScrollXCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScrollY(OnWindowScrollEventHandler.ScrollYCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onScroll(OnWindowScrollEventHandler.ScrollCallback callback) {
        windowScrollEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onChar(OnWindowCharEventHandler.CharCallback callback) {
        windowCharEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onKey(OnWindowKeyEventHandler.KeyCallback callback) {
        windowKeyEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMonitorConnected(OnWindowMonitorEventHandler.MonitorConnected callback) {
        windowMonitorEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMonitorDisconnected(OnWindowMonitorEventHandler.MonitorDisconnected callback) {
        windowMonitorEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onMouse(OnWindowMouseEventHandler.MouseCallback callback) {
        windowMouseEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onJoystickConnected(OnWindowJoystickEventHandler.JoystickConnectedCallback callback) {
        windowJoystickEventHandler.addCallback(callback);
        return this;
    }

    public WindowEventsManager onJoystickDisconnected(OnWindowJoystickEventHandler.JoystickDisconnectedCallback callback) {
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
