package model;

/**
 * Created by Asia on 2015-11-29.
 */
public class Material {
    private String name;
    private int constantBurn;
    private int constantSmoke;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getConstantSmoke() {
        return constantSmoke;
    }

    public void setConstantSmoke(int constantSmoke) {
        this.constantSmoke = constantSmoke;
    }

    public int getConstantBurn() {
        return constantSmoke;
    }

    public void setConstantBurn(int constantBurn) {
        this.constantSmoke = constantBurn;
    }
}
