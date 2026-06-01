package com.despertador;

import com.despertador.model.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SIMULADOR DE DESPERTADOR INTELIGENTE ===\n");

        // 1. Inicializar el "Cerebro" (Manager y Estadísticas)
        SleepStatistics stats = new SleepStatistics();
        AlarmManager manager = new AlarmManager(stats);

        // 2. Crear una Alarma Estándar (Para ir al trabajo)
        Schedule workSchedule = new Schedule(false);
        workSchedule.setWeekdays(); // De Lunes a Viernes
        SoundProfile loudBeep = new SoundProfile("beep_fuerte.mp3", 8); // Volumen 8
        SnoozeManager standardSnooze = new SnoozeManager(5, 3); // Posponer 5 min, máx 3 veces
        AlarmStrategy standardStrategy = new StandardAlarm();

        Alarm morningAlarm = new Alarm(
            LocalTime.of(7, 0), "Trabajo", workSchedule, loudBeep, standardSnooze, standardStrategy
        );
        manager.addAlarm(morningAlarm);

        // 3. Crear una Alarma Circadiana (Para el fin de semana)
        Schedule weekendSchedule = new Schedule(false);
        weekendSchedule.setWeekends(); // Sábado y Domingo
        SoundProfile softNature = new SoundProfile("pajaros.mp3", 4); // Volumen 4
        SnoozeManager relaxSnooze = new SnoozeManager(10, 1); // Posponer 10 min, máx 1 vez
        AlarmStrategy circadianStrategy = new CircadianAlarm(20); // 20 min de amanecer progresivo

        Alarm weekendAlarm = new Alarm(
            LocalTime.of(9, 30), "Relax Fin de Semana", weekendSchedule, softNature, relaxSnooze, circadianStrategy
        );
        manager.addAlarm(weekendAlarm);

        // --- SIMULACIÓN DE EVENTOS ---

        System.out.println("\n--- SIMULANDO: Lunes 7:00 AM ---");
        // Forzamos a que el sistema crea que es Lunes a las 7:00
        LocalDateTime mondayMorning = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY).withHour(7).withMinute(0);
        manager.checkAndTriggerAlarms(mondayMorning);

        System.out.println("\n--- SIMULANDO: Usuario pulsa SNOOZE ---");
        morningAlarm.snooze(mondayMorning);
        stats.recordSnooze();

        System.out.println("\n--- SIMULANDO: Usuario apaga la alarma tras 5 min ---");
        morningAlarm.stop();
        stats.recordWakeUp(5); // Se levantó con 5 min de retraso

        System.out.println("\n--- SIMULANDO: Sábado 9:30 AM ---");
        LocalDateTime saturdayMorning = LocalDateTime.now().with(java.time.DayOfWeek.SATURDAY).withHour(9).withMinute(30);
        manager.checkAndTriggerAlarms(saturdayMorning);

        System.out.println("\n--- SIMULANDO: Activar Modo Vacaciones ---");
        manager.toggleVacationMode();
        System.out.println("Intentando que suene la alarma de trabajo el Martes 7:00 AM...");
        LocalDateTime tuesdayMorning = LocalDateTime.now().with(java.time.DayOfWeek.TUESDAY).withHour(7).withMinute(0);
        manager.checkAndTriggerAlarms(tuesdayMorning); // No sonará porque el modo vacaciones corta el paso

        // Mostrar el reporte final
        System.out.println("\n" + stats.generateReport());
    }
}