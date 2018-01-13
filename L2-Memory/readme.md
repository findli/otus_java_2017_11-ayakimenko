Написать стенд для определения размера объекта. Определить размер пустой строки и пустых контейнеров. 
Определить рост размера контейнера от количества элементов в нем.
Например
Object — 8 bytes, (x64 16!!!)
Empty String — 40 bytes
Array — from 12 bytes

VM options -Xmx512m -Xms512m
Runtime runtime = Runtime.getRuntime();
long mem = runtime.totalMemory() - runtime.freeMemory();
System.gc()
jconsole, connect to pid