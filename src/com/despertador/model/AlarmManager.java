package com.despertador.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmManager {
    
    private List<Alarm> alarms;
    private boolean vacationMode;
    
    // Dependencia para las estadísticas (aparecerá en rojo temporalmente)
    private SleepStatistics sleepStats; 

    public AlarmManager(SleepStatistics sleepStats) {
        this.alarms = new ArrayList<>();
        this.vacationMode = false; // Por defecto no estamos de vacaciones
        this.sleepStats = sleepStats;
    }

    /**
     * Añade una nueva alarma al gestor.
     */
    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
        System.out.println("Alarma '" + alarm.getLabel() + "' añadida al sistema.");
    }

    /**
     * Elimina una alarma buscando por su identificador único (UUID).
     */
    public boolean deleteAlarm(UUID alarmId) {
        return alarms.removeIf(alarm -> alarm.getId().equals(alarmId));
    }

    /**
     * Activa o desactiva una alarma específica.
     */
    public boolean toggleAlarm(UUID alarmId) {
        for (Alarm alarm : alarms) {
            if (alarm.getId().equals(alarmId)) {
                alarm.setActive(!alarm.isActive());
                System.out.println("Alarma '" + alarm.getLabel() + "' " + (alarm.isActive() ? "ACTIVADA" : "DESACTIVADA"));
                return true;
            }
        }
        return false;
    }

    /**
     * Alterna el Modo Vacaciones (Silencia todas las alarmas de golpe).
     */
    public void toggleVacationMode() {
        this.vacationMode = !this.vacationMode;
        System.out.println("Modo vacaciones " + (this.vacationMode ? "ACTIVADO. Todas las alarmas silenciadas." : "DESACTIVADO. Alarmas en funcionamiento."));
    }

    /**
     * Devuelve una lista de las alarmas que están actualmente activas.
     */
    public List<Alarm> getUpcomingAlarms() {
        List<Alarm> activeAlarms = new ArrayList<>();
        for (Alarm alarm : alarms) {
            if (alarm.isActive()) {
                activeAlarms.add(alarm);
            }
        }
        return activeAlarms;
    }

    /**
     * Comprueba todas las alarmas contra la hora actual y dispara las que coincidan.
     */
    public List<Alarm> checkAndTriggerAlarms(LocalDateTime currentTime) {
        List<Alarm> triggeredAlarms = new ArrayList<>();

        if (vacationMode) {
            // Si estamos de vacaciones, ignoramos el proceso completo
            return triggeredAlarms; 
        }

        for (Alarm alarm : alarms) {
            if (alarm.shouldTrigger(currentTime)) {
                alarm.trigger();
                triggeredAlarms.add(alarm);
            }
        }
        return triggeredAlarms;
    }

    public boolean isVacationMode() { return vacationMode; }
    public SleepStatistics getSleepStats() { return sleepStats; }
}