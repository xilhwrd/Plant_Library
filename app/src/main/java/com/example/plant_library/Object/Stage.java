package com.example.plant_library.Object;

public class Stage {
    private String StageName;
    private int StageDay1, StageDay2, StageDay3, StageDay4 ;

    public Stage() {
    }

    public Stage(String stageName, int stageDay1, int stageDay2, int stageDay3, int stageDay4) {
        StageName = stageName;
        StageDay1 = stageDay1;
        StageDay2 = stageDay2;
        StageDay3 = stageDay3;
        StageDay4 = stageDay4;
    }

    public String getStageName() {
        return StageName;
    }

    public void setStageName(String stageName) {
        StageName = stageName;
    }

    public int getStageDay1() {
        return StageDay1;
    }

    public void setStageDay1(int stageDay1) {
        StageDay1 = stageDay1;
    }

    public int getStageDay2() {
        return StageDay2;
    }

    public void setStageDay2(int stageDay2) {
        StageDay2 = stageDay2;
    }

    public int getStageDay3() {
        return StageDay3;
    }

    public void setStageDay3(int stageDay3) {
        StageDay3 = stageDay3;
    }

    public int getStageDay4() {
        return StageDay4;
    }

    public void setStageDay4(int stageDay4) {
        StageDay4 = stageDay4;
    }
}
