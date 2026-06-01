package com.despertador.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

public class Schedule {
    
    // EnumSet es mucho más eficiente y claro que usar un array de booleanos
    private EnumSet<DayOfWeek> activeDays;
    private boolean isOneTime;

    /**
     * Constructor. Por defecto, creamos un horario sin días asignados.
     * @param isOneTime true si la alarma solo debe sonar una vez y luego desactivarse.
     */
    public Schedule(boolean isOneTime) {
        this.activeDays = EnumSet.noneOf(DayOfWeek.class); // Inicializa un conjunto vacío
        this.isOneTime = isOneTime;
    }

    /**
     * Añade un día específico para que la alarma suene.
     */
    public void addDay(DayOfWeek day) {
        this.activeDays.add(day);
        this.isOneTime = false; // Si le añadimos días de repetición, ya no es de un solo uso
    }

    /**
     * Quita un día específico de la repetición.
     */
    public void removeDay(DayOfWeek day) {
        this.activeDays.remove(day);
    }

    /**
     * Comprueba si la alarma debe sonar en la fecha indicada.
     */
    public boolean isRingingDay(LocalDate date) {
        // Si es de un solo uso, asumimos que debe sonar el día que toque su próxima hora
        if (isOneTime) {
            return true; 
        }
        // Si es repetitiva, comprobamos si el día de la semana de la fecha coincide
        return activeDays.contains(date.getDayOfWeek());
    }

    // --- Métodos de conveniencia (Opcionales pero muy útiles) ---

    public void setEveryday() {
        this.activeDays = EnumSet.allOf(DayOfWeek.class);
        this.isOneTime = false;
    }

    public void setWeekdays() {
        this.activeDays = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        this.isOneTime = false;
    }

    public void setWeekends() {
        this.activeDays = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        this.isOneTime = false;
    }

    // --- GETTERS Y SETTERS ---

    public boolean isOneTime() { return isOneTime; }
    public void setOneTime(boolean oneTime) { this.isOneTime = oneTime; }
    public EnumSet<DayOfWeek> getActiveDays() { return activeDays; }
}