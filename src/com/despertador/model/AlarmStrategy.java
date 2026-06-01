package com.despertador.model;

public interface AlarmStrategy {
    /**
     * Define cómo se debe activar la alarma.
     */
    void trigger(Alarm alarm);
}