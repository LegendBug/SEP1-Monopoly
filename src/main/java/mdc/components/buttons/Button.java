package mdc.components.buttons;

import java.awt.*;

public class Button {
    private Rectangle rect;
    private Image normal;
    private Image hover;
    private Image pressed;
    private ButtonStates state;
    private boolean ifSelected;
    private boolean ifActive;

    public Button(Rectangle rect, Image normal, Image hover, Image pressed) {
        this.rect = rect;
        this.normal = normal;
        this.hover = hover;
        this.pressed = pressed;
        this.state = ButtonStates.NORMAL;
        this.ifSelected = false; // 是否有且仅有当前按键被选择（由外界控制，对内使用）
        this.ifActive = false; // 按键是否被激活
    }

    public Rectangle getRect() {
        return rect;
    }

    public Image getImage() {
        switch (state) {
            case NORMAL -> {
                return normal;
            }
            case HOVER -> {
                return hover;
            }
            case PRESSED ->
            {
                return pressed;
            }
        }
        return null;
    }

    public boolean isIfSelected() {
        return ifSelected;
    }

    public boolean isIfActive() {
        return ifActive;
    }

    public void setSelected(boolean bo) {
        this.ifSelected = bo;
    }

    public void setActive(boolean bo) {
        this.ifActive = bo;
    }

    /**
     * 控制按键当前状态
     * @param mousePoint 鼠标坐标
     * @param ifPressed 鼠标点击
     * @param ifReleased 鼠标释放
     * @param ifEnter 按下回车
     * @return 当鼠标在按键之上时，返回true
     */
    public boolean checkForState(Point mousePoint, boolean ifPressed, boolean ifReleased, boolean ifEnter) {
        if (mousePoint != null && this.rect.contains(mousePoint)) {
            if (ifPressed) this.state = ButtonStates.PRESSED; // 鼠标按下，按压
            else this.state = ButtonStates.HOVER; // 鼠标停留，停留
            if (ifReleased || ifEnter) ifActive = true; // 鼠标放开或按下回车，执行
            return true; // 鼠标在范围内，返回true
        } else {
            if (this.ifSelected) { // 鼠标不在按钮上，控制权留给按键
                if (ifEnter) this.ifActive = true; // 回车被按下，执行
                else this.state = ButtonStates.HOVER; // 按键为选中状态，停留
            } else this.state = ButtonStates.NORMAL; // 未选中，正常状态
        }
        return false;
    }

    public void resetButton() {
        this.state = ButtonStates.NORMAL;
        this.ifSelected = false;
        this.ifActive = false;
    }
}
