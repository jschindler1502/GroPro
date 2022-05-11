@echo off
SET jarDat=SignalauswertungsProgramm.jar
SET in=..\testfaelle\input\
SET out=..\testfaelle\output\


IF NOT EXIST %jarDat% GOTO NOJAR
IF NOT EXIST %in% GOTO NOINPUT

DEL /q %out%
ECHO Die Dateien im Ordner %in% werden verarbeitet.
java -jar %jarDat% %in%
ECHO ------------------------------------------------------
GOTO ENDE

:NOJAR
ECHO Die .jar wurde nicht gefunden!
GOTO ENDE

:NOINPUT
ECHO Der Eingabeordner wurde nicht gefunden!
GOTO ENDE

:ENDE
PAUSE