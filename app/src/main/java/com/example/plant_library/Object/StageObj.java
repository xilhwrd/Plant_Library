package com.example.plant_library.Object;

public class StageObj {
    String stageTime;
    int IdResource;
    String stageName;

    public StageObj(String stageTime, int idResource, String stageName) {
        this.stageTime = stageTime;
        IdResource = idResource;
        this.stageName = stageName;
    }

    public String getStageTime() {
        return stageTime;
    }

    public void setStageTime(String stageTime) {
        this.stageTime = stageTime;
    }

    public int getIdResource() {
        return IdResource;
    }

    public void setIdResource(int idResource) {
        IdResource = idResource;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
