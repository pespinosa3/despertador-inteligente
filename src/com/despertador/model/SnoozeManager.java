package com.despertador.model;

public class SnoozeManager {
    
    private int snoozeDurationMinutes; // Tiempo que se retrasa la alarma (ej. 9 minutos)
    private int maxSnoozeCount;        // Límite de veces que se puede posponer (ej. 3 veces)

    public SnoozeManager(int snoozeDurationMinutes, int maxSnoozeCount) {
        this.snoozeDurationMinutes = snoozeDurationMinutes;
        this.maxSnoozeCount = maxSnoozeCount;
    }

    /**
     * Comprueba si la alarma aún puede ser pospuesta o si ya alcanzó el límite.
     */
    public boolean canSnooze(int currentSnoozeCount) {
        // Si el límite es 0 o negativo, asumimos que no hay límite (snooze infinito)
        if (maxSnoozeCount <= 0) {
            return true;
        }
        return currentSnoozeCount < maxSnoozeCount;
    }

    // --- GETTERS Y SETTERS ---

    public int getSnoozeDurationMinutes() {
        return snoozeDurationMinutes;
    }

    public void setSnoozeDurationMinutes(int snoozeDurationMinutes) {
        this.snoozeDurationMinutes = snoozeDurationMinutes;
    }

    public int getMaxSnoozeCount() {
        return maxSnoozeCount;
    }

    public void setMaxSnoozeCount(int maxSnoozeCount) {
        this.maxSnoozeCount = maxSnoozeCount;
    }
}