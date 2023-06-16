package com.yj510.studyplanner;

public class ClassInfo {
    String name;
    String cycle;
    String hex_color;

    public ClassInfo() { }
    public ClassInfo(String name, String cycle, String hex_color) {
        this.name = name;
        this.cycle = cycle;
        this.hex_color = hex_color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getHex_color() {
        return hex_color;
    }

    public void setHex_color(String hex_color) {
        this.hex_color = hex_color;
    }
}
