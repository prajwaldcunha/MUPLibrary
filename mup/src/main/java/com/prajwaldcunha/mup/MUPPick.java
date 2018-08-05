package com.prajwaldcunha.mup;


public class MUPPick {

    private static MUPPick instance;

    public static MUPPick init() {
        if(instance == null) {
            instance = new MUPPick();
        }
        return instance;
    }




}
