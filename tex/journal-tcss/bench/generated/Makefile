bench: clean
	@gnuplot plot.gnu
	@for file in `ls *.ps`; do ps2pdf $${file}; done
	@for file in `ls *.pdf`; do pdfcrop $${file}; done
	@rm -rf frequency20.pdf frequencyall.pdf loss-100.pdf loss-1000.pdf loss-all.pdf degree-counter.pdf frequency-selected.pdf *1.pdf *2.pdf *4.pdf *.ps
.PHONY: clean
clean:
	@rm -rf *.ps *.pdf
