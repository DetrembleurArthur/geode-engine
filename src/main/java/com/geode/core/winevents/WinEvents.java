package com.geode.core.winevents;

import com.geode.core.key.KeyInput;
import com.geode.core.mouse.MouseInput;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class WinEvents {
    public interface CharCallback extends WindowCallback {
        void trigger(Character character);
        static CharCallback tagged(Object tag, CharCallback callback) {
            return new CharCallback() {
                @Override
                public void trigger(Character character) {
                    callback.trigger(character);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CloseCallback extends WindowCallback {
        void trigger();
        static CloseCallback tagged(Object tag, CloseCallback callback) {
            return new CloseCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScaleXCallback extends WindowCallback {
        void trigger(Float x);
        static ScaleXCallback tagged(Object tag, ScaleXCallback callback) {
            return new ScaleXCallback() {
                @Override
                public void trigger(Float x) {
                    callback.trigger(x);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScaleYCallback extends WindowCallback {
        void trigger(Float y);
        static ScaleYCallback tagged(Object tag, ScaleYCallback callback) {
            return new ScaleYCallback() {
                @Override
                public void trigger(Float y) {
                    callback.trigger(y);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScaleCallback extends WindowCallback {
        void trigger(Vector2f scale);
        static ScaleCallback tagged(Object tag, ScaleCallback callback) {
            return new ScaleCallback() {
                @Override
                public void trigger(Vector2f scale) {
                    callback.trigger(scale);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CursorEnteredCallback extends WindowCallback {
        void trigger();
        static CursorEnteredCallback tagged(Object tag, CursorEnteredCallback callback) {
            return new CursorEnteredCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CursorExitedCallback extends WindowCallback {
        void trigger();
        static CursorExitedCallback tagged(Object tag, CursorExitedCallback callback) {
            return new CursorExitedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CursorXCallback extends WindowCallback {
        void trigger(Double x);
        static CursorXCallback tagged(Object tag, CursorXCallback callback) {
            return new CursorXCallback() {
                @Override
                public void trigger(Double x) {
                    callback.trigger(x);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CursorYCallback extends WindowCallback {
        void trigger(Double y);
        static CursorYCallback tagged(Object tag, CursorYCallback callback) {
            return new CursorYCallback() {
                @Override
                public void trigger(Double y) {
                    callback.trigger(y);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface CursorPosCallback extends WindowCallback {
        void trigger(Vector2f pos);
        static CursorPosCallback tagged(Object tag, CursorPosCallback callback) {
            return new CursorPosCallback() {
                @Override
                public void trigger(Vector2f pos) {
                    callback.trigger(pos);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface DropCallback extends WindowCallback {
        void trigger(String[] items);
        static DropCallback tagged(Object tag, DropCallback callback) {
            return new DropCallback() {
                @Override
                public void trigger(String[] items) {
                    callback.trigger(items);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface FocusedCallback extends WindowCallback {
        void trigger();
        static FocusedCallback tagged(Object tag, FocusedCallback callback) {
            return new FocusedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface UnfocusedCallback extends WindowCallback {
        void trigger();
        static UnfocusedCallback tagged(Object tag, UnfocusedCallback callback) {
            return new UnfocusedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface FbWidthCallback extends WindowCallback {
        void trigger(Integer width);
        static FbWidthCallback tagged(Object tag, FbWidthCallback callback) {
            return new FbWidthCallback() {
                @Override
                public void trigger(Integer width) {
                    callback.trigger(width);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface FbHeightCallback extends WindowCallback {
        void trigger(Integer height);
        static FbHeightCallback tagged(Object tag, FbHeightCallback callback) {
            return new FbHeightCallback() {
                @Override
                public void trigger(Integer height) {
                    callback.trigger(height);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface FbSizeCallback extends WindowCallback {
        void trigger(Vector2i size);
        static FbSizeCallback tagged(Object tag, FbSizeCallback callback) {
            return new FbSizeCallback() {
                @Override
                public void trigger(Vector2i size) {
                    callback.trigger(size);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface IconifyCallback extends WindowCallback {
        void trigger();
        static IconifyCallback tagged(Object tag, IconifyCallback callback) {
            return new IconifyCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface RestoreCallback extends WindowCallback {
        void trigger();
        static RestoreCallback tagged(Object tag, RestoreCallback callback) {
            return new RestoreCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface JoystickConnectedCallback extends WindowCallback {
        void trigger();
        static JoystickConnectedCallback tagged(Object tag, JoystickConnectedCallback callback) {
            return new JoystickConnectedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface JoystickDisconnectedCallback extends WindowCallback {
        void trigger();
        static JoystickDisconnectedCallback tagged(Object tag, JoystickDisconnectedCallback callback) {
            return new JoystickDisconnectedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface KeyCallback extends WindowCallback {
        void trigger(KeyInput keyInput);
        static KeyCallback tagged(Object tag, KeyCallback callback) {
            return new KeyCallback() {
                @Override
                public void trigger(KeyInput keyInput) {
                    callback.trigger(keyInput);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface MaximizedCallback extends WindowCallback {
        void trigger();
        static MaximizedCallback tagged(Object tag, MaximizedCallback callback) {
            return new MaximizedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface MinimizedCallback extends WindowCallback {
        void trigger();
        static MinimizedCallback tagged(Object tag, MinimizedCallback callback) {
            return new MinimizedCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface MonitorConnected extends WindowCallback {
        void trigger();
        static MonitorConnected tagged(Object tag, MonitorConnected callback) {
            return new MonitorConnected() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface MonitorDisconnected extends WindowCallback {
        void trigger();
        static MonitorDisconnected tagged(Object tag, MonitorDisconnected callback) {
            return new MonitorDisconnected() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface MouseCallback extends WindowCallback {
        void trigger(MouseInput mouseInput);
        static MouseCallback tagged(Object tag, MouseCallback callback) {
            return new MouseCallback() {
                @Override
                public void trigger(MouseInput mouseInput) {
                    callback.trigger(mouseInput);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface XCallback extends WindowCallback {
        void trigger(Integer x);
        static XCallback tagged(Object tag, XCallback callback) {
            return new XCallback() {
                @Override
                public void trigger(Integer x) {
                    callback.trigger(x);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface YCallback extends WindowCallback {
        void trigger(Integer y);
        static YCallback tagged(Object tag, YCallback callback) {
            return new YCallback() {
                @Override
                public void trigger(Integer y) {
                    callback.trigger(y);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface PositionCallback extends WindowCallback {
        void trigger(Vector2i position);
        static PositionCallback tagged(Object tag, PositionCallback callback) {
            return new PositionCallback() {
                @Override
                public void trigger(Vector2i pos) {
                    callback.trigger(pos);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface RefreshCallback extends WindowCallback {
        void trigger();
        static RefreshCallback tagged(Object tag, RefreshCallback callback) {
            return new RefreshCallback() {
                @Override
                public void trigger() {
                    callback.trigger();
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScrollXCallback extends WindowCallback {
        void trigger(Double x);
        static ScrollXCallback tagged(Object tag, ScrollXCallback callback) {
            return new ScrollXCallback() {
                @Override
                public void trigger(Double x) {
                    callback.trigger(x);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScrollYCallback extends WindowCallback {
        void trigger(Double y);
        static ScrollYCallback tagged(Object tag, ScrollYCallback callback) {
            return new ScrollYCallback() {
                @Override
                public void trigger(Double y) {
                    callback.trigger(y);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface ScrollCallback extends WindowCallback {
        void trigger(Vector2f scroll);
        static ScrollCallback tagged(Object tag, ScrollCallback callback) {
            return new ScrollCallback() {
                @Override
                public void trigger(Vector2f scroll) {
                    callback.trigger(scroll);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface WidthCallback extends WindowCallback {
        void trigger(Integer width);
        static WidthCallback tagged(Object tag, WidthCallback callback) {
            return new WidthCallback() {
                @Override
                public void trigger(Integer width) {
                    callback.trigger(width);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface HeightCallback extends WindowCallback {
        void trigger(Integer height);
        static HeightCallback tagged(Object tag, HeightCallback callback) {
            return new HeightCallback() {
                @Override
                public void trigger(Integer height) {
                    callback.trigger(height);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }

    public interface SizeCallback extends WindowCallback {
        void trigger(Vector2i size);
        static SizeCallback tagged(Object tag, SizeCallback callback) {
            return new SizeCallback() {
                @Override
                public void trigger(Vector2i size) {
                    callback.trigger(size);
                }

                @Override
                public Object tag() {
                    return tag;
                }
            };
        }
    }
}
