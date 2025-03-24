#
# Makefile that builds ptest for 351 lab1a
#
CC = gcc
CFLAGS = -O -Wall -m64 -g
LIBS = -lm

ptest: ptest.c pointer.c common.c common.h
	$(CC) $(CFLAGS) -Wno-unused-variable -o ptest ptest.c pointer.c common.c

clean:
	rm -f *.o ptest *~

test: ptest
	./ptest
