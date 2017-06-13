set terminal postscript dashed color lw 1 "Helvetica" 24
set output "burst.ps"
set key at 10, 2.1  
set size 1.0, 0.8
set xlabel "burst duration (milliseconds)"
set ylabel "frequency"
plot "../../../bench/lossr" using 1 title "random"  with lines, "../../../bench/lossc" using 1 title "cascading"  with lines, "../../../bench/lossd" using 1 title "degree"  with lines, "../../../bench/lossb" using 1 title "betweenness"  with lines 
