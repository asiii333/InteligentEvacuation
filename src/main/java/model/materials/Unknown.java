package model.materials;

import model.Material;

/**
 * Created by Asia on 2016-01-05.
 */
public class Unknown extends Material{
    public Unknown(){
        setName("unknown");
        setColor("gray");
        setConstantBurn(5);
        setConstantSmoke(5);
    }
}
