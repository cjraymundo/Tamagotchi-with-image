package com.mycompany.finals;

public class Creature {
    private String name;
    private String species;
    private int hunger = 15;
    private int happiness = 15;
    private int energy = 15;
    boolean alive;

    public Creature(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

        public int getEnergy() {
        return energy;
    }

        public int getHunger() {
        return hunger;
    }

        public int getHappiness() {
        return happiness;
    }
    
    public void play() {
        happiness += 8;
        energy -= 5;
        hunger -= 3;
        
        if (happiness > 15) { happiness = 15; }
        if (energy < 0) { energy = 0; }
        if (hunger < 0) { hunger = 0; }
    }

    public void eat() {
        hunger += 8;
        energy -= 1;
        
        if (hunger > 15) { hunger = 15; }
            if (happiness < 0) { happiness = 0; }
            if (energy < 0) { energy = 0; }
    }

    public void sleep() {
        energy += 8;
        hunger -= 2;
        happiness -= 4;
        
        if (energy > 15) { energy = 15; }
            if (happiness < 0) { happiness = 0; }
            if (hunger < 0) { hunger = 0; }
    }

    public String Status() {
        return String.format("<html><b>%s</b> (%s) &nbsp;|&nbsp; Hunger: %d &nbsp;|&nbsp; Happiness: %d &nbsp;|&nbsp; Energy: %d</html>", 
                name, species, hunger, happiness, energy);
    }
    
    public boolean isDead() {
    // Example: If energy or happiness drops to 0 or below, or hunger goes too high (e.g., 100)
    if (energy <= 0 || happiness <= 0 || hunger >= 100) {
        return true;
    }
    return false;
}

public String getDeathReason() {
    if (energy <= 0) return name + " ran out of energy and collapsed from exhaustion!";
    if (happiness <= 0) return name + " became too depressed and ran away...";
    if (hunger >= 100) return name + " starved to death...";
    return name + " has passed away.";
}
}