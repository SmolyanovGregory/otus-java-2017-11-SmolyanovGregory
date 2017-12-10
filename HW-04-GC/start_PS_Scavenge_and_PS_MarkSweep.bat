set memory=-Xms512m -Xmx512m
set gc=-XX:+UseParallelGC -XX:+UseParallelOldGC
set gc_log=-verbose:gc -Xloggc:./logs/gc.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=1M
set stat_file=./logs/PS_Scavenge_and_PS_MarkSweep_statistics.log
java %memory% %gc% %gc_log% -jar HW-04-GC.jar %stat_file%
pause