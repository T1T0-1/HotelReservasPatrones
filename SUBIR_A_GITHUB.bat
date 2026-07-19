@echo off
chcp 65001 >nul
title Subir HotelReservasPatrones a GitHub

echo ==================================================
echo  SUBIR PROYECTO A GITHUB - CUENTA T1T0-1
echo ==================================================
echo.
echo Antes de continuar, crea un repositorio PUBLICO y VACIO llamado:
echo HotelReservasPatrones
echo en https://github.com/T1T0-1
pause

where git >nul 2>&1
if errorlevel 1 (
  echo.
  echo ERROR: Git no esta instalado en esta computadora.
  echo Usa el metodo del navegador explicado en GUIA_SUBIR_A_GITHUB.txt
  pause
  exit /b 1
)

if not exist .git git init -b main
git add .
git commit -m "Proyecto final sistema de reservas de hotel"
git branch -M main
git remote remove origin >nul 2>&1
git remote add origin https://github.com/T1T0-1/HotelReservasPatrones.git
git push -u origin main

echo.
echo Si no aparecio un error, revisa:
echo https://github.com/T1T0-1/HotelReservasPatrones
pause
