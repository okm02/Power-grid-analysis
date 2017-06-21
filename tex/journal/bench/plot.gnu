
set terminal postscript dashed color lw 1 "Helvetica" 24
set output "loss.ps"
set key at 10, 2.1  
set size 1.0, 0.8
set xlabel "burst duration (milliseconds)"
set ylabel "frequency"
plot "../../../bench/lossr" using 1 title "random"  with lines, "../../../bench/lossc" using 1 title "cascading"  with lines, "../../../bench/lossd" using 1 title "degree"  with lines, "../../../bench/lossb" using 1 title "betweenness"  with lines 

set terminal postscript dashed color lw 1 "Helvetica" 24
set output "loss1000.ps"
set key at 10, 2.1  
set size 1.0, 0.8
set xlabel "burst duration (milliseconds)"
set ylabel "frequency"
plot "../../../bench/lossr" every ::0::1000 using 1 title "random"  with lines, "../../../bench/lossc" every ::0::1000 using 1 title "cascading"  with lines, "../../../bench/lossd" every ::0::1000 using 1 title "degree"  with lines, "../../../bench/lossb" every ::0::1000 using 1 title "betweenness"  with lines 
