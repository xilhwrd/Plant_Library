package com.example.plant_library.Object;

import java.util.Map;

public class Favorite {
    private String userId;
    private Map<String, Boolean> plantIds;

    public Favorite() {
        // Bắt buộc để Firebase có thể chuyển đổi từ JSON sang đối tượng Java
    }

    public Favorite(String userId, Map<String, Boolean> plantIds) {
        this.userId = userId;
        this.plantIds = plantIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Boolean> getPlantIds() {
        return plantIds;
    }

    public void setPlantIds(Map<String, Boolean> plantIds) {
        this.plantIds = plantIds;
    }
}




