package com.despertador.model;

public class SoundProfile {
    
    private String soundFile; // Simularemos la ruta del archivo, ej: "naturaleza.mp3"
    private int volumeLevel;  // Rango de volumen del 1 al 10

    public SoundProfile(String soundFile, int volumeLevel) {
        this.soundFile = soundFile;
        // Usamos el setter directamente en el constructor para aprovechar su validación
        setVolumeLevel(volumeLevel); 
    }

    /**
     * Simula la reproducción del sonido (recuerda que la práctica prohíbe interfaces gráficas o hardware real).
     */
    public void play() {
        System.out.println("Reproduciendo tono: [" + soundFile + "] a volumen: " + volumeLevel);
    }

    // --- GETTERS Y SETTERS ---

    public String getSoundFile() { 
        return soundFile; 
    }
    
    public void setSoundFile(String soundFile) { 
        this.soundFile = soundFile; 
    }
    
    public int getVolumeLevel() { 
        return volumeLevel; 
    }
    
    /**
     * Establece el volumen aplicando una regla de negocio para mantenerlo entre 1 y 10.
     */
    public void setVolumeLevel(int volumeLevel) {
        if (volumeLevel < 1) {
            this.volumeLevel = 1;
        } else if (volumeLevel > 10) {
            this.volumeLevel = 10;
        } else {
            this.volumeLevel = volumeLevel;
        }
    }
}