

package com.wntime.modules.sys.form;

import java.io.Serializable;

/**
 * 密码表单

 */

public class PasswordForm implements Serializable {
    private static final long serialVersionUID = 5085299025814363834L;
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
