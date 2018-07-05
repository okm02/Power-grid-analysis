file=main

fast: clean
	pdflatex ${file}.tex
	bibtex ${file}
	pdflatex ${file}.tex
	pdflatex ${file}.tex

all:
	cd ../../bench; sh filter-script
	cd bench/generated; make 
	make fast

.PHONY: clean
clean:
	@rm -rf ${file}.pdf ${file}.aux ${file}.blg ${file}.log ${file}.bbl
