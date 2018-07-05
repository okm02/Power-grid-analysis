file=main.tex

fast: clean
	pdflatex main.tex
	bibtex main
	pdflatex main.tex
	pdflatex main.tex

all:
	cd ../../bench; sh filter-script
	cd bench/generated; make 
	make fast

.PHONY: clean
clean:
	@rm -rf main.pdf main.aux main.blg main.log
