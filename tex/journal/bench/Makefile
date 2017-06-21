bench: clean
	@gnuplot plot.gnu
	@for file in `ls *.ps`; do ps2pdf $${file}; done
	@for file in `ls *.pdf`; do pdfcrop $${file}; done
	@rm -rf *1.pdf *2.pdf *4.pdf *.ps
.PHONY: clean
clean:
	@rm -rf *.ps *.pdf
