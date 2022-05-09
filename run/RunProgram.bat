@echo off
SET jarDat=Programm.jar
SET in=..\testfaelle\input\
SET out=..\testfaelle\output\


IF NOT EXIST %jarDat% GOTO NOJAR
IF NOT EXIST %in% GOTO NOINPUT

DEL /q %out%
java -jar %jarDat% "%%i"


:NOJAR
ECHO Die .jar wurde nicht gefunden!
GOTO ENDE

:NOINPUT
ECHO Der Input-Ordner wurde nicht gefunden!
GOTO ENDE

:ENDE
PAUSE