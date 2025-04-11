// Copyright (c) 2025 Ananya Shreya Soni.

#include "NthPrime.h"

// Behavior:
//   -  determines whether an integer, x, is prime or not
// Parameters:
//   - int16_t x: an integer that is >= 2
// Returns:
//  - .0: if x is not prime
//  - 1: if x is prime
int isPrime(int16_t x);

int16_t NthPrime(int16_t n) {
    int primeIndex = 0;
    int num = 2;
    while (primeIndex < n) {
        if (isPrime(num) == 1) {
            primeIndex++;
            if (primeIndex == n) {
                return num;
            }
        }
        num++;
    }
    return num;
}

int isPrime(int16_t x) {
    // a prime number is any number that is
    // not divisible by any number between
    // 2 and itself - 1
    for (int i = x - 1; i > 1; i--) {
        if (x % i == 0) {
            return 0;
        }
    }
    return 1;
}
