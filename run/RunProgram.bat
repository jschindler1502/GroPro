@echo off
SET jarDat=Programm.jar
SET in=..\testfaelle\input\
SET out=..\testfaelle\output\


IF NOT EXIST %jarDat% GOTO NOJAR
IF NOT EXIST %in% GOTO NOINPUT

DEL /q %out%
FOR %%i IN (%in%*) DO (
	ECHO Die Datei %%i wurde verarbeitet.
	java -jar %jarDat% "%%i"
	ECHO ---------------------------
)
ECHO Alle Dateien wurden verarbeitet.
GOTO ENDE


:NOJAR
ECHO Die .jar wurde nicht gefunden!
GOTO ENDE

:NOINPUT
ECHO Der Input-Ordner wurde nicht gefunden!
GOTO ENDE

:ENDE
PAUSE