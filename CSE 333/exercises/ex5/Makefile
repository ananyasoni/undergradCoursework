# default target
ex5: ex5.o NthPrime.o
	gcc -Wall -g -std=c17 -o ex5 ex5.o NthPrime.o

# individual source files
NthPrime.o: NthPrime.c NthPrime.h
	gcc -Wall -g -std=c17 -c NthPrime.c

ex5.o: ex5.c NthPrime.c NthPrime.h
	gcc -Wall -g -std=c17 -c ex5.c

# phony target - delete built files (including OS X debug files)
clean:
	rm -rf ex5 *.o *~ ex5.dS