set terminal postscript dashed color lw 3 "Helvetica" 24
set ylabel offset 2,0,0
set output "degree-counter.ps"
set xlabel "Degree"
set xtics font ", 16"
set ytics font ", 16"
set ylabel "Percentage of Nodes"
set boxwidth 0.5
set style fill solid
plot '../../../../bench/outputDegreeCounter.txt' using 1:3:xtic(1) title "" with boxes

reset

set terminal postscript dashed color lw 3 "Helvetica" 24
set ylabel offset 2,0,0
set output "time-bc-1.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph1" using 1:2 title "BC-4"  with lines,\
"time-graph1" using 1:3 title "BC-8"  with lines,\
"time-graph1" using 1:4 title "BC-16"  with lines,\
"time-graph1" using 1:5 title "BC-32"  with lines

set output "time-c-1.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph1" using 1:6 title "C-4"  with lines,\
"time-graph1" using 1:7 title "C-8"  with lines,\
"time-graph1" using 1:8 title "C-16"  with lines,\
"time-graph1" using 1:9 title "C-32"  with lines

set output "time-d-1.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph1" using 1:10 title "D-4"  with lines,\
"time-graph1" using 1:11 title "D-8"  with lines,\
"time-graph1" using 1:12 title "D-16"  with lines,\
"time-graph1" using 1:13 title "D-32"  with lines

set output "time-r-1.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph1" using 1:14 title "R-4"  with lines,\
"time-graph1" using 1:15 title "R-8"  with lines,\
"time-graph1" using 1:16 title "R-16"  with lines,\
"time-graph1" using 1:17 title "R-32"  with lines

set output "time-bc-2.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph2" using 1:2 title "BC-4"  with lines,\
"time-graph2" using 1:3 title "BC-8"  with lines,\
"time-graph2" using 1:4 title "BC-16"  with lines,\
"time-graph2" using 1:5 title "BC-32"  with lines

set output "time-c-2.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph2" using 1:6 title "C-4"  with lines,\
"time-graph2" using 1:7 title "C-8"  with lines,\
"time-graph2" using 1:8 title "C-16"  with lines,\
"time-graph2" using 1:9 title "C-32"  with lines

set output "time-d-2.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph2" using 1:10 title "D-4"  with lines,\
"time-graph2" using 1:11 title "D-8"  with lines,\
"time-graph2" using 1:12 title "D-16"  with lines,\
"time-graph2" using 1:13 title "D-32"  with lines

set output "time-r-2.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph2" using 1:14 title "R-4"  with lines,\
"time-graph2" using 1:15 title "R-8"  with lines,\
"time-graph2" using 1:16 title "R-16"  with lines,\
"time-graph2" using 1:17 title "R-32"  with lines

set output "time-bc-4.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph4" using 1:2 title "BC-4"  with lines,\
"time-graph4" using 1:3 title "BC-8"  with lines,\
"time-graph4" using 1:4 title "BC-16"  with lines,\
"time-graph4" using 1:5 title "BC-32"  with lines

set output "time-c-4.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph4" using 1:6 title "C-4"  with lines,\
"time-graph4" using 1:7 title "C-8"  with lines,\
"time-graph4" using 1:8 title "C-16"  with lines,\
"time-graph4" using 1:9 title "C-32"  with lines

set output "time-d-4.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph4" using 1:10 title "D-4"  with lines,\
"time-graph4" using 1:11 title "D-8"  with lines,\
"time-graph4" using 1:12 title "D-16"  with lines,\
"time-graph4" using 1:13 title "D-32"  with lines

set output "time-r-4.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph4" using 1:14 title "R-4"  with lines,\
"time-graph4" using 1:15 title "R-8"  with lines,\
"time-graph4" using 1:16 title "R-16"  with lines,\
"time-graph4" using 1:17 title "R-32"  with lines


set output "time-all-1.ps"
set xrange [0:64]
set xtics (2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Execution Time (sec)"
plot "time-graph1" using 1:5 title "BC-32"  with lines,\
"time-graph4" using 1:9 title "C-32"  with lines,\
"time-graph1" using 1:13 title "D-32"  with lines,\
"time-graph4" using 1:17 title "R-32"  with lines

unset xrange
set output "loss-all.ps"
#set key at 10, 2.1
#set size 1.0, 0.8
set xtics 200000
set xlabel "Nb of Vertices Attacked"
set ylabel "Percentage Loss"
plot "../../../../bench/lossr" using 1 title "Random"  with lines, "../../../../bench/lossc" using 1 title "Cascading"  with lines, "../../../../bench/lossd" using 1 title "Degree"  with lines, "../../../../bench/lossb" using 1 title "Betweenness"  with lines


#set terminal postscript dashed color lw 2 "Helvetica" 24
set output "loss-100.ps"
#set key at 10, 2.1
#set size 1.0, 0.8
set xtics 10
set xlabel "Nb of Vertices Attacked"
set ylabel "Percentage Loss"
plot "../../../../bench/lossr" every ::0::100 using 1 title "Random"  with lines, "../../../../bench/lossc" every ::0::100 using 1 title "Cascading"  with lines, "../../../../bench/lossd" every ::0::100 using 1 title "Degree"  with lines, "../../../../bench/lossb" every ::0::100 using 1 title "Betweenness"  with lines

#set terminal postscript dashed color lw 2 "Helvetica" 24
set output "loss-1000.ps"
#set key at 10, 2.1
#set size 1.0, 0.8
set xtics 100
set xlabel "Nb of Vertices Attacked"
set ylabel "Percentage Loss"
plot "../../../../bench/lossr" every ::0::1000 using 1 title "Random"  with lines, "../../../../bench/lossc" every ::0::1000 using 1 title "Cascading"  with lines, "../../../../bench/lossd" every ::0::1000 using 1 title "Degree"  with lines, "../../../../bench/lossb" every ::0::1000 using 1 title "Betweenness"  with lines



set output "frequency20.ps"
set xlabel "Nb of Vertices"
set xtics font ", 14"
set ytics font ", 14"
set ylabel "Nb of Components"
#set xtics 2
set boxwidth 0.1
set style fill solid
plot '../../../../bench/Frequency.txt' every ::0::20 using 1:2:xtic(1) title "" with boxes


set output "frequencyall.ps"
set xlabel "Nb of Vertices"
set xtics font ", 14"
set ytics font ", 14"
set ylabel "Nb of Components"
#set xtics 10
set boxwidth 0.1
set style fill solid
plot '../../../../bench/Frequency.txt' every ::0::140 using 1:2:xtic(10) title "" with boxes

set output "frequency-selected.ps"
set xlabel "Nb of Vertices"
set xtics font ", 14"
set ytics font ", 14"
set ylabel "Nb of Components"
#set xtics 10
set boxwidth 0.1
set style fill solid
plot '../../../../bench/Frequency.txt' every ::40::140 using 1:2:xtic(10) title "" with boxes


set output "efficiency-bc-1.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-bc-1" using 1:2 title "BC-4"  with lines,\
"efficiency-bc-1" using 1:3 title "BC-8"  with lines,\
"efficiency-bc-1" using 1:4 title "BC-16"  with lines,\
"efficiency-bc-1" using 1:5 title "BC-32"  with lines

set output "efficiency-bc-2.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-bc-2" using 1:2 title "BC-4"  with lines,\
"efficiency-bc-2" using 1:3 title "BC-8"  with lines,\
"efficiency-bc-2" using 1:4 title "BC-16"  with lines,\
"efficiency-bc-2" using 1:5 title "BC-32"  with lines


set output "efficiency-bc-4.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-bc-4" using 1:2 title "BC-4"  with lines,\
"efficiency-bc-4" using 1:3 title "BC-8"  with lines,\
"efficiency-bc-4" using 1:4 title "BC-16"  with lines,\
"efficiency-bc-4" using 1:5 title "BC-32"  with lines


set output "efficiency-c-1.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-c-1" using 1:2 title "C-4"  with lines,\
"efficiency-c-1" using 1:3 title "C-8"  with lines,\
"efficiency-c-1" using 1:4 title "C-16"  with lines,\
"efficiency-c-1" using 1:5 title "C-32"  with lines

set output "efficiency-c-2.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-c-2" using 1:2 title "C-4"  with lines,\
"efficiency-c-2" using 1:3 title "C-8"  with lines,\
"efficiency-c-2" using 1:4 title "C-16"  with lines,\
"efficiency-c-2" using 1:5 title "C-32"  with lines

set output "efficiency-c-4.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-c-4" using 1:2 title "C-4"  with lines,\
"efficiency-c-4" using 1:3 title "C-8"  with lines,\
"efficiency-c-4" using 1:4 title "C-16"  with lines,\
"efficiency-c-4" using 1:5 title "C-32"  with lines

set output "efficiency-r-1.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-r-1" using 1:2 title "R-4"  with lines,\
"efficiency-r-1" using 1:3 title "R-8"  with lines,\
"efficiency-r-1" using 1:4 title "R-16"  with lines,\
"efficiency-r-1" using 1:5 title "R-32"  with lines

set output "efficiency-r-2.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-r-2" using 1:2 title "R-4"  with lines,\
"efficiency-r-2" using 1:3 title "R-8"  with lines,\
"efficiency-r-2" using 1:4 title "R-16"  with lines,\
"efficiency-r-2" using 1:5 title "R-32"  with lines

set output "efficiency-r-4.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-r-4" using 1:2 title "R-4"  with lines,\
"efficiency-r-4" using 1:3 title "R-8"  with lines,\
"efficiency-r-4" using 1:4 title "R-16"  with lines,\
"efficiency-r-4" using 1:5 title "R-32"  with lines

set output "efficiency-d-1.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-d-1" using 1:2 title "D-4"  with lines,\
"efficiency-d-1" using 1:3 title "D-8"  with lines,\
"efficiency-d-1" using 1:4 title "D-16"  with lines,\
"efficiency-d-1" using 1:5 title "D-32"  with lines

set output "efficiency-d-2.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-d-2" using 1:2 title "D-4"  with lines,\
"efficiency-d-2" using 1:3 title "D-8"  with lines,\
"efficiency-d-2" using 1:4 title "D-16"  with lines,\
"efficiency-d-2" using 1:5 title "D-32"  with lines

set output "efficiency-d-4.ps"
set xrange [0:64]
set xtics (1, 2, 4, 8, 16, 32, 64)
set xlabel "Nb of Threads"
set ylabel "Performance Efficiency"
plot "efficiency-d-4" using 1:2 title "D-4"  with lines,\
"efficiency-d-4" using 1:3 title "D-8"  with lines,\
"efficiency-d-4" using 1:4 title "D-16"  with lines,\
"efficiency-d-4" using 1:5 title "D-32"  with lines
