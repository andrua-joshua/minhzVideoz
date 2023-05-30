package com.example.minhzvideos.handler;

import java.io.Serializable;

public class Setting implements Serializable {
    private boolean loadFromDiskByAdmin;
    private boolean loadOnTopByAdmin;
    private boolean loadFromLogsByAdmin;
    private boolean saveToLogsByAdmin;
    private boolean backUplogByAdmin;
    private boolean autoSave;
    private String passCode;

    public Setting(boolean loadFromDiskByAdmin, boolean loadOnTopByAdmin,
                   boolean loadFromLogsByAdmin, boolean saveToLogsByAdmin,
                   boolean backUplogByAdmin, boolean autoSave, String passCode) {
        this.loadFromDiskByAdmin = loadFromDiskByAdmin;
        this.loadOnTopByAdmin = loadOnTopByAdmin;
        this.loadFromLogsByAdmin = loadFromLogsByAdmin;
        this.saveToLogsByAdmin = saveToLogsByAdmin;
        this.backUplogByAdmin = backUplogByAdmin;
        this.autoSave = autoSave;
        this.passCode = passCode;
    }

    public Setting() {
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public boolean isLoadFromDiskByAdmin() {
        return loadFromDiskByAdmin;
    }

    public void setLoadFromDiskByAdmin(boolean loadFromDiskByAdmin) {
        this.loadFromDiskByAdmin = loadFromDiskByAdmin;
    }

    public boolean isLoadOnTopByAdmin() {
        return loadOnTopByAdmin;
    }

    public void setLoadOnTopByAdmin(boolean loadOnTopByAdmin) {
        this.loadOnTopByAdmin = loadOnTopByAdmin;
    }

    public boolean isLoadFromLogsByAdmin() {
        return loadFromLogsByAdmin;
    }

    public void setLoadFromLogsByAdmin(boolean loadFromLogsByAdmin) {
        this.loadFromLogsByAdmin = loadFromLogsByAdmin;
    }

    public boolean isSaveToLogsByAdmin() {
        return saveToLogsByAdmin;
    }

    public void setSaveToLogsByAdmin(boolean saveToLogsByAdmin) {
        this.saveToLogsByAdmin = saveToLogsByAdmin;
    }

    public boolean isBackUplogByAdmin() {
        return backUplogByAdmin;
    }

    public void setBackUplogByAdmin(boolean backUplogByAdmin) {
        this.backUplogByAdmin = backUplogByAdmin;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }
}
