package com.example.plant_library.Object;

public class Plants {
    private int PlantID, CategoryID,  AirPurifying;;
    private String ScientificName, CommonName, Family, Genus, Species, Description, GrowthRate,
            LightRequirements, WaterRequirements, CareRequirements, SoilType, PHRange,
            TemperatureRange, Bloomtime,Propagation, Size, PlantImage;

    public Plants(){}
    public Plants(int plantID,int categoryID, int airPurifying, String scientificName, String commonName, String family,
                  String genus, String species, String description, String growthRate,
                  String lightRequirements, String waterRequirements, String careRequirements,
                  String soilType, String PHRange, String temperatureRange, String bloomtime,
                  String propagation, String size, String plantImage) {
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
        this.LightRequirements = lightRequirements;
        this. WaterRequirements = waterRequirements;
        this.CareRequirements = careRequirements;
        this. SoilType = soilType;
        this.PHRange = PHRange;
        this.TemperatureRange = temperatureRange;
        this.Bloomtime = bloomtime;
        this. Propagation = propagation;
        this.Size = size;
        this. PlantImage = plantImage;
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

    public String getLightRequirements() {
        return LightRequirements;
    }

    public void setLightRequirements(String lightRequirements) {
        LightRequirements = lightRequirements;
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
}
