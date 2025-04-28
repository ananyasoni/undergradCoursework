# Makefile
CXX = g++
CPPFLAGS = -Wall -g -std=c++17
EXEC = ex9

# default target attempts to build all executables
all: $(EXEC)

ex9: ex9.o Vector.o
	$(CXX) $(CPPFLAGS) -o $@ $^

ex9.o: ex9.cc Vector.h
	$(CXX) $(CPPFLAGS) -c $<

Vector.o: Vector.cc Vector.h
	$(CXX) $(CPPFLAGS) -c $<

val:
	valgrind --leak-check=full ./$(EXEC)

# phony target - remove generated files and backups
clean:
	rm -rf ex9 *.o *~ ex9.dS
