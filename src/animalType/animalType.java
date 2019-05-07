/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animalType;

/**
 *
 * @author Edd993Surface
 */
public enum animalType {
    
    CRAPAUD("Crapaud"),
    TRITON("Triton");

    private final String animalName;

    animalType(String animalName) {
        this.animalName = animalName;
    }
    
    public String getName(){
        return animalName;
    }


}
