// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/16/2025
// ex6

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
    FILE *f;
    char readbuf;
    if (argc != 2) {
        fprintf(stderr, "usage: ./ex6 filename\n");
        return EXIT_FAILURE;
    }
    // open the input file
    f = fopen(argv[1], "rb");  // "rb" -> read, binary mode
    if (f == NULL) {
        fprintf(stderr, "%s -- ", argv[1]);
        perror("fopen for read failed");
        return EXIT_FAILURE;
    }
    // seek to the end of the file
    if (fseek(f, 0, SEEK_END) != 0) {
        perror("fseek to end failed");
        fclose(f);
        return EXIT_FAILURE;
    }
    // get the file size
    long size = ftell(f);
    if (size == -1L) {
        perror("ftell failed");
        fclose(f);
        return EXIT_FAILURE;
    }
    // read from the file & print to stdout in reverse order
    for (long i = size - 1; i >= 0; i--) {
        if (fseek(f, i, SEEK_SET) != 0) {
            perror("fseek to position failed");
            fclose(f);
            return EXIT_FAILURE;
        }
        if (fread(&readbuf, 1, 1, f) == 1) {
            printf("%c", readbuf);
        } else {
            perror("fread failed: error reading character");
            fclose(f);
            return EXIT_FAILURE;
        }
    }
    fclose(f);
    return EXIT_SUCCESS;
}
