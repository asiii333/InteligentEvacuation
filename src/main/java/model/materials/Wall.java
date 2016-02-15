package model.materials;

import model.Material;

/**
 * Created by Asia on 2016-02-05.
 */
public class Wall extends Material {
    public Wall() {
        setName("wall");
        setColor("black");
        setConstantBurn(150);
        setConstantSmoke(150);
    }
}