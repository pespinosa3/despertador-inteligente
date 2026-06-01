package com.despertador.model;

public class SleepStatistics {
    
    private int totalSnoozes;
    private int alarmsMissed;
    private int onTimeWakes;

    public SleepStatistics() {
        this.totalSnoozes = 0;
        this.alarmsMissed = 0;
        this.onTimeWakes = 0;
    }

    /**
     * Registra cada vez que el usuario pulsa el botón de posponer.
     */
    public void recordSnooze() {
        this.totalSnoozes++;
    }

    /**
     * Registra un despertar. Si hubo retraso (snoozes previos), lo cuenta, 
     * pero si el retraso es 0, cuenta como un despertar puntual.
     */
    public void recordWakeUp(int delayMinutes) {
        if (delayMinutes == 0) {
            this.onTimeWakes++;
        }
    }

    /**
     * Registra una alarma que sonó pero que el usuario nunca apagó.
     */
    public void recordMissedAlarm() {
        this.alarmsMissed++;
    }

    /**
     * Genera un pequeño informe en texto para mostrar las estadísticas del usuario.
     */
    public String generateReport() {
        return """
               === PERFIL DE SUEÑO Y PUNTUALIDAD ===
               Despertares puntuales: %d
               Veces pospuestas (Snoozes): %d
               Alarmas ignoradas: %d
               =====================================
               """.formatted(onTimeWakes, totalSnoozes, alarmsMissed);
    }

    // --- GETTERS ---
    
    public int getTotalSnoozes() { return totalSnoozes; }
    public int getAlarmsMissed() { return alarmsMissed; }
    public int getOnTimeWakes() { return onTimeWakes; }
}