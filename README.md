Pablo Espinosa Pérez

# Sistema de Despertador Inteligente

Este proyecto implementa la lógica interna en Java para una aplicación de despertador inteligente avanzada, desarrollada siguiendo principios de diseño orientado a objetos (POO) y arquitectura limpia.

## 1. Diagrama de Clases UML
Este diagrama representa la estructura de las clases del sistema, sus responsabilidades y cómo se relacionan entre sí utilizando patrones de diseño como *Strategy*.

```mermaid
classDiagram
    class AlarmManager {
        -List~Alarm~ alarms
        -boolean vacationMode
        -SleepStatistics sleepStats
        +createAlarm(time, schedule, profile) Alarm
        +deleteAlarm(alarmId) boolean
        +toggleAlarm(alarmId) boolean
        +toggleVacationMode() void
        +getUpcomingAlarms() List~Alarm~
        +checkAndTriggerAlarms(currentTime) List~Alarm~
    }

    class Alarm {
        -UUID id
        -LocalTime time
        -String label
        -boolean isActive
        -Schedule schedule
        -SoundProfile soundProfile
        -AlarmStrategy alarmStrategy
        +snooze() void
        +stop() void
        +getNextRingTime() LocalDateTime
    }

    class Schedule {
        -EnumSet~DayOfWeek~ activeDays
        -boolean isOneTime
        +isRingingDay(date) boolean
    }

    class SoundProfile {
        -String soundFile
        -int volumeLevel
        +play() void
    }

    class SnoozeManager {
        -int snoozeDurationMinutes
        -int maxSnoozeCount
        +calculateNextSnooze(currentTime) LocalTime
    }

    class SleepStatistics {
        -int totalSnoozes
        -int alarmsMissed
        -int onTimeWakes
        +recordSnooze() void
        +recordWakeUp(delayMinutes) void
        +generateReport() String
    }

    class AlarmStrategy {
        <<interface>>
        +trigger(Alarm) void
    }

    class StandardAlarm {
        +trigger(Alarm) void
    }

    class CircadianAlarm {
        -int warmupDurationMinutes
        +trigger(Alarm) void
        -increaseBrightness() void
        -increaseVolume() void
    }

    AlarmManager "1" *-- "many" Alarm : manages
    AlarmManager "1" *-- "1" SleepStatistics : tracks
    Alarm *-- "1" Schedule : uses
    Alarm *-- "1" SoundProfile : uses
    Alarm *-- "1" SnoozeManager : uses
    Alarm *-- "1" AlarmStrategy : executes
    AlarmStrategy <|.. StandardAlarm : implements
    AlarmStrategy <|.. CircadianAlarm : implements
```

## 2. Diagrama de Casos de Uso UML
Este diagrama modela cómo interactúa el usuario y el sistema con las diferentes funcionalidades de la aplicación.

```mermaid
flowchart LR
    %% Actores
    Usuario((Usuario))
    Sistema((Sistema Reloj))

    %% Casos de Uso
    Crear([Crear Alarma])
    Activar([Activar/Desactivar Alarma])
    Vacaciones([Activar Modo Vacaciones])
    Posponer([Posponer Alarma - Snooze])
    Detener([Detener Alarma])
    Consultar([Consultar Estadísticas de Sueño])
    
    Comprobar([Comprobar Alarmas Activas])
    Circadiano([Activar Despertar Circadiano])
    Registrar([Registrar Estadística])

    %% Relaciones de Usuario
    Usuario --> Crear
    Usuario --> Activar
    Usuario --> Vacaciones
    Usuario --> Posponer
    Usuario --> Detener
    Usuario --> Consultar

    %% Relaciones de Sistema
    Sistema --> Comprobar

    %% Relaciones de Dependencia (Include/Extend)
    Comprobar -. "<<extend>>" .-> Circadiano
    Posponer -. "<<include>>" .-> Registrar
    Detener -. "<<include>>" .-> Registrar
```
