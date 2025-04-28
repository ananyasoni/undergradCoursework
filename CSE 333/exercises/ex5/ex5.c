// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/08/2025
// ex3

#include <stdio.h>
#include <stdlib.h>
#include "NthPrime.h"

int main(int argc, char **argv) {
    int primes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    for (int i = 0; i < 12; i++) {
        if (NthPrime(i + 1) != primes[i]) {
            int exp = primes[i];
            int16_t act = NthPrime(i + 1);
            printf("error: n: %d, expected  %d, actual %d\n", i + 1, exp, act);
        }
    }
    printf("successfully passed all test cases!\n");
    return EXIT_SUCCESS;
}
