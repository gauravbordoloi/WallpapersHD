package com.gmonetix.wallpapershdultimate.helper;

public class ColorsHelpAdapter {
    private int colors;
    private String colorsName;

    public ColorsHelpAdapter(int colors , String colorsName){
        this.colors = colors;
        this.colorsName = colorsName;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public String getColorsName() {
        return colorsName;
    }

    public void setColorsName(String colorsName) {
        this.colorsName = colorsName;
    }
}
