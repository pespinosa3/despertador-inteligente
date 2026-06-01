package com.despertador.model;

import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;

public class Alarm {
    // Identificador único (facilita la eliminación y edición en listas)
    private final UUID id;
    private LocalTime time;
    private String label;
    private boolean isActive;
    
    // Objetos de soporte (Composición)
    private Schedule schedule;
    private SoundProfile soundProfile;
    private SnoozeManager snoozeManager;
    private AlarmStrategy alarmStrategy;

    // Estado dinámico para controlar el Snooze (posponer)
    private LocalDateTime nextSnoozeTime;
    private int currentSnoozeCount;

    public Alarm(LocalTime time, String label, Schedule schedule, 
                 SoundProfile soundProfile, SnoozeManager snoozeManager, 
                 AlarmStrategy alarmStrategy) {
        this.id = UUID.randomUUID();
        this.time = time;
        this.label = label;
        this.isActive = true; // Nace activa por defecto
        this.schedule = schedule;
        this.soundProfile = soundProfile;
        this.snoozeManager = snoozeManager;
        this.alarmStrategy = alarmStrategy;
        this.nextSnoozeTime = null;
        this.currentSnoozeCount = 0;
    }

    /**
     * Evalúa si la alarma debería sonar en una fecha y hora determinadas.
     */
    public boolean shouldTrigger(LocalDateTime currentDateTime) {
        if (!isActive) return false;

        // Si está en modo snooze, la prioridad absoluta es la hora calculada del snooze
        if (nextSnoozeTime != null) {
            return currentDateTime.toLocalTime().equals(nextSnoozeTime.toLocalTime()) 
                && currentDateTime.toLocalDate().equals(nextSnoozeTime.toLocalDate());
        }

        // Si no está pospuesta, comprueba el horario normal programado
        return currentDateTime.toLocalTime().equals(this.time) 
            && schedule.isRingingDay(currentDateTime.toLocalDate());
    }

    /**
     * Ejecuta la estrategia de sonido asociada (Normal o Despertar Circadiano).
     */
    public void trigger() {
        if (alarmStrategy != null) {
            alarmStrategy.trigger(this);
        }
    }

    /**
     * Pospone la alarma utilizando las reglas del SnoozeManager.
     */
    public void snooze(LocalDateTime currentTime) {
        if (snoozeManager.canSnooze(currentSnoozeCount)) {
            currentSnoozeCount++;
            int minutes = snoozeManager.getSnoozeDurationMinutes();
            this.nextSnoozeTime = currentTime.plusMinutes(minutes);
            System.out.println("Alarma '" + label + "' pospuesta por " + minutes + " minutos.");
        } else {
            System.out.println("Límite de 'Snooze' alcanzado. Debes detener la alarma por completo.");
        }
    }

    /**
     * Detiene el ciclo de sonido actual y limpia el estado del snooze.
     */
    public void stop() {
        this.nextSnoozeTime = null;
        this.currentSnoozeCount = 0;
        System.out.println("Alarma '" + label + "' detenida.");
    }

    // --- GETTERS Y SETTERS (Encapsulación obligatoria) ---

    public UUID getId() { return id; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; if(!active) stop(); }
    public SoundProfile getSoundProfile() { return soundProfile; }
    public void setSoundProfile(SoundProfile soundProfile) { this.soundProfile = soundProfile; }
    public void setAlarmStrategy(AlarmStrategy alarmStrategy) { this.alarmStrategy = alarmStrategy; }
}