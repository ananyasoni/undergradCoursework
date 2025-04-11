// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/02/2025
// ex1

#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <stdint.h>

#define ARRAY_SIZE 12

// Behavior:
//   - Copies the int64_t integer elements in the src array to the dst array
//     in sorted order. note: src is NOT modified and src and dst have
//     the same length = len
// Parameters:
//   - int64_t src[]: the array to be sorted
//   - int64_t dst[]: the array where the int64_t elements from
//     src are stored in non-decreasing order
//   - int len: the length of both arrays
void CopyAndSort(int64_t src[], int64_t dst[], int len);

// Behavior:
//   - Prints the provided int64_t array
// Parameters:
//   - int64_t array[]: the array that is printed
//   - int len: the length of array
void printArray(int64_t array[], int len);

// Behavior:
//   - This function copies and sorts the array
//     {3, 2, -5, 7, 17, 42, 6, 333, 7, 8, -8, 6}
//     in non-decreasing order before printing it
// Parameters:
//   - argc: number of arguements
//   - argv: array of pointers to the arguments as strings
// Returns:
//   - int: returns EXIT_SUCCESS
int main(int argc, char* argv[]) {
    int64_t array[] = {3, 2, -5, 7, 17, 42, 6, 333, 7, 8, -8, 6};
    int64_t sortedArray[ARRAY_SIZE];
    CopyAndSort(array, sortedArray, ARRAY_SIZE);
    printArray(sortedArray, ARRAY_SIZE);
    return EXIT_SUCCESS;
}

void CopyAndSort(int64_t src[], int64_t dst[], int len) {
    // use insertion sort to sort the values in src
    // and store in dst
    for (int i = 0; i < len; i++) {
        dst[i] = src[i];
        int l = i - 1;
        int r = i;
        while (l >= 0 && dst[r] < dst[l]) {
            dst[r] = dst[l];
            dst[l] = src[i];
            l--;
            r--;
        }
    }
}

void printArray(int64_t array[], int len) {
    for (int i = 0; i < len; i++) {
        printf("%" PRId64 " ", array[i]);
    }
    printf("\n");
}
