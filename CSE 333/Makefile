# Makefile
CXX = g++
CPPFLAGS = -Wall -g -std=c++17
EXEC = ex10

# default target attempts to build all executables
all: $(EXEC)

ex10: ex10.o Vector.o
	$(CXX) $(CPPFLAGS) -o $@ $^

ex10.o: ex10.cc Vector.h
	$(CXX) $(CPPFLAGS) -c $<

Vector.o: Vector.cc Vector.h
	$(CXX) $(CPPFLAGS) -c $<

val:
	valgrind --leak-check=full ./$(EXEC)

# phony target - remove generated files and backups
clean:
	rm -rf $(EXEC) *.o *~ $(EXEC).dS
