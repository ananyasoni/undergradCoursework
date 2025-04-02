// Copyright (c) 2025 Ananya Shreya Soni
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/01/2025
// ex0

#include <stdio.h>
#include <stdlib.h>

// Behavior:
//   - This helper method estimates π by adding together terms 0 through n,
//     inclusive, in the Nilakantha series. note: the nth term of the series
//     where n >= 1 is (-1)^(n+1) x (4 / (2n x (2n+1) x (2n+2)))
// Parameters:
//   - int n: non-negative integer that determines up to which last term in
//     the Nilakantha series to add to the estimate returned by this method
// Returns:
//   - double: returns the estimate of π
double estimatePi(int n);

// Behavior:
//   - This method prints the estimate of π to 20 decimal places if the
//     user provides exactly 2 arguements, the integer provided is non-negative,
//     and is a valid integer
// Parameters:
//   - argc: number of arguements
//   - argv: array of pointers to the arguments as strings
// Returns:
//   EXIT_SUCCESS if the method successfully prints the estimate of π and
//   EXIT_FAILURE otherwise
int main(int argc, char* argv[]) {
    if (argc != 2) {
        printf("Wrong Number of Arguements: must have 2 arguements total\n");
        return EXIT_FAILURE;
    }
    int n;
    if (sscanf(argv[1], "%d", &n) != 1) {
        printf("Invalid Input: your 2nd arguement must be an integer\n");
        return EXIT_FAILURE;
    } else if (n < 0) {
        printf("Must enter a postive integer: n must be >= 0\n");
        return EXIT_FAILURE;
    } else {
        double estimateOfPi = estimatePi(n);
        printf("Our estimate of Pi is %.20f\n", estimateOfPi);
        return EXIT_SUCCESS;
    }
}

double estimatePi(int n) {
    double estimate = 3;
    if (n == 0) {
        return estimate;
    } else {
        for (double i = 1; i <= n; i++) {
            if ((int)i % 2 == 1) {
                estimate += (4 / (2 * i * (2 * i + 1) * (2 * i + 2)));
            } else {
                estimate -= (4 / (2 * i * (2 * i + 1) * (2 * i + 2)));
            }
        }
        return estimate;
    }
}
