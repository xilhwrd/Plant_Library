package com.example.plant_library.Object;

import java.util.Map;

public class Plants {
    private int PlantID, CategoryID,  AirPurifying;
    private String ScientificName, CommonName, Family, Genus, Species, Description, GrowthRate,
             WaterRequirements, CareRequirements, SoilType, PHRange,
            TemperatureRange, Bloomtime,Propagation, Size, PlantImage;
//    private Map<String, String> LightRequirements;
    private LightRequirements LightRequirements;
//    private String LightRate,LightStage1, LightStage2, LightStage3, LightStage4;
    public Plants(){}

    public Plants(int plantID, int categoryID, int airPurifying, String scientificName, String commonName, String family,
                  String genus, String species, String description, String growthRate,
                  String lightRate, String lightStage2, String lightStage3, String lightStage4, String lightStage1,
                  String waterRequirements, String careRequirements,
                  LightRequirements lightRequirements,
                  String soilType, String PHRange, String temperatureRange, String bloomtime,
                  String propagation, String size, String plantImage ) {
        this.PlantID = plantID;
        this.CategoryID = categoryID;
        this.AirPurifying = airPurifying;
        this.ScientificName = scientificName;
        this.CommonName = commonName;
        this.Family = family;
        this.Genus = genus;
        this.Species = species;
        this.Description = description;
        this.GrowthRate = growthRate;
        this. WaterRequirements = waterRequirements;
        this.CareRequirements = careRequirements;
        this. SoilType = soilType;
        this.PHRange = PHRange;
        this.TemperatureRange = temperatureRange;
        this.Bloomtime = bloomtime;
        this. Propagation = propagation;
        this.Size = size;
        this. PlantImage = plantImage;
        this.LightRequirements = lightRequirements;
//        this.LightRate = lightRate;
//        this.LightStage2 = lightStage2;
//        this.LightStage3 = lightStage3;
//        this.LightStage4 = lightStage4;
//        this.LightStage1 = lightStage1;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getPlantID() {
        return PlantID;
    }

    public void setPlantID(int plantID) {
        PlantID = plantID;
    }

    public String getScientificName() {
        return ScientificName;
    }

    public void setScientificName(String scientificName) {
        ScientificName = scientificName;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public String getGenus() {
        return Genus;
    }

    public void setGenus(String genus) {
        Genus = genus;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGrowthRate() {
        return GrowthRate;
    }

    public void setGrowthRate(String growthRate) {
        GrowthRate = growthRate;
    }



    public String getWaterRequirements() {
        return WaterRequirements;
    }

    public void setWaterRequirements(String waterRequirements) {
        WaterRequirements = waterRequirements;
    }

    public String getCareRequirements() {
        return CareRequirements;
    }

    public void setCareRequirements(String careRequirements) {
        CareRequirements = careRequirements;
    }

    public String getSoilType() {
        return SoilType;
    }

    public void setSoilType(String soilType) {
        SoilType = soilType;
    }

    public String getPHRange() {
        return PHRange;
    }

    public void setPHRange(String PHRange) {
        this.PHRange = PHRange;
    }

    public String getTemperatureRange() {
        return TemperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        TemperatureRange = temperatureRange;
    }

    public String getBloomtime() {
        return Bloomtime;
    }

    public void setBloomtime(String bloomtime) {
        Bloomtime = bloomtime;
    }

    public String getPropagation() {
        return Propagation;
    }

    public void setPropagation(String propagation) {
        Propagation = propagation;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPlantImage() {
        return PlantImage;
    }

    public void setPlantImage(String plantImage) {
        PlantImage = plantImage;
    }

    public int getAirPurifying() {
        return AirPurifying;
    }

    public void setAirPurifying(int airPurifying) {
        AirPurifying = airPurifying;
    }

    public com.example.plant_library.Object.LightRequirements getLightRequirements() {
        return LightRequirements;
    }

    public void setLightRequirements(com.example.plant_library.Object.LightRequirements lightRequirements) {
        LightRequirements = lightRequirements;
    }


    //    public Map<String, String> getLightRequirements() {
//        return LightRequirements;
//    }
//
//    public void setLightRequirements(Map<String, String> lightRequirements) {
//        LightRequirements = lightRequirements;
//    }

//    public String getLightRate() {
//        return LightRate;
//    }
//
//    public void setLightRate(String lightRate) {
//        LightRate = lightRate;
//    }
//
//    public String getLightStage2() {
//        return LightStage2;
//    }
//
//    public void setLightStage2(String lightStage2) {
//        LightStage2 = lightStage2;
//    }
//
//    public String getLightStage3() {
//        return LightStage3;
//    }
//
//    public void setLightStage3(String lightStage3) {
//        LightStage3 = lightStage3;
//    }
//
//    public String getLightStage4() {
//        return LightStage4;
//    }
//
//    public void setLightStage4(String lightStage4) {
//        LightStage4 = lightStage4;
//    }
//
//    public String getLightStage1() {
//        return LightStage1;
//    }
//
//    public void setLightStage1(String lightStage1) {
//        LightStage1 = lightStage1;
//    }
}
