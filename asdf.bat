@rem
setlocal
set PATH_FROM = C:\Users\fred8\Documents\GitHub\ExampleJavaMod
@rem
setlocal
set PATH_TO = C:\Users\fred8\AppData\Roaming\Mindustry

if exist C:\Users\fred8\AppData\Roaming\Mindustry\mods\ages.jar del C:\Users\fred8\AppData\Roaming\Mindustry\mods\ages.jar
xcopy C:\Users\fred8\Documents\GitHub\ExampleJavaMod\build\libs\ages.jar C:\Users\fred8\AppData\Roaming\Mindustry\mods\ /k /y


if exist C:\Users\fred8\AppData\Roaming\Mindustry\mods\ages.jar start C:\Users\fred8\Desktop\Mindustry.jar