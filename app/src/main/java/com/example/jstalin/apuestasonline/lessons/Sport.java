package com.example.jstalin.apuestasonline.lessons;

import com.example.jstalin.apuestasonline.R;

/**
 * Created by JStalin on 12/01/2018.
 */

public class Sport {

    // Codigos de cada tipo de apusesta
    public static final int CODE_FOOTBALL = 1;
    public static final int CODE_TENNIS = 2;
    public static final int CODE_BASKETBALL = 3;
    public static final int CODE_HANDBALL = 4;

    private int code;
    private String name;
    private int image;

    public Sport(int code,String name, int image) {
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
