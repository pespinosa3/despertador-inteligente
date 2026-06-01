package com.despertador.model;

public class StandardAlarm implements AlarmStrategy {
    
    @Override
    public void trigger(Alarm alarm) {
        System.out.println(">>> [ALARMA ESTÁNDAR ACTIVADA] <<<");
        // Reproduce el sonido de golpe al volumen configurado
        alarm.getSoundProfile().play();
    }
}