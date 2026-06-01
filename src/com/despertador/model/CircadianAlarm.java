package com.despertador.model;

public class CircadianAlarm implements AlarmStrategy {
    
    private int warmupDurationMinutes; // Minutos que dura el amanecer artificial

    public CircadianAlarm(int warmupDurationMinutes) {
        this.warmupDurationMinutes = warmupDurationMinutes;
    }

    @Override
    public void trigger(Alarm alarm) {
        System.out.println(">>> [DESPERTAR CIRCADIANO ACTIVADO] <<<");
        System.out.println("Iniciando fase de despertar suave de " + warmupDurationMinutes + " minutos...");
        increaseBrightness();
        increaseVolume(alarm.getSoundProfile());
    }

    private void increaseBrightness() {
        System.out.println("Simulando amanecer: Aumentando el brillo de la pantalla gradualmente \u2600\uFE0F");
    }

    private void increaseVolume(SoundProfile profile) {
        System.out.println("Reproduciendo [" + profile.getSoundFile() + "] con volumen ascendente desde 1 hasta " + profile.getVolumeLevel());
    }
}
