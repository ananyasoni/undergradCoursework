// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/05/2025
// ex2

#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

// Behavior:
//   -  This function prints, in hex, the value of num_bytes
//      bytes allocated to some variable with value located at
//      memory address, mem_addr
// Parameters:
//   - void* mem_addr: a pointer to a variable
//   - num_bytes: number of bytes to print
void PrintBytes(void* mem_addr, int num_bytes);

// Behavior:
//   - Creates various variables and prints their addresses
//     and the values of the bytes allocated to those
//     variables
// Parameters:
//   - argc: number of arguements
//   - argv: array of pointers to the arguments as strings
// Returns:
//   - int: returns EXIT_SUCCESS
int main(int argc, char **argv) {
    char     char_val = '0';
    int32_t  int_val = 1;
    float    float_val = 1.0;
    double   double_val  = 1.0;

    typedef struct {
      char     char_val;
      int32_t  int_val;
      float    float_val;
      double   double_val;
    } Ex2Struct;

    Ex2Struct struct_val = { '0', 1, 1.0, 1.0 };

    PrintBytes(&char_val, sizeof(char));
    PrintBytes(&int_val, sizeof(int32_t));
    PrintBytes(&float_val, sizeof(float));
    PrintBytes(&double_val, sizeof(double));
    PrintBytes(&struct_val, sizeof(struct_val));

    return EXIT_SUCCESS;
}

void PrintBytes(void* mem_addr, int num_bytes) {
    uint8_t* bytes = (uint8_t*)mem_addr;
    printf("The %d bytes starting at %p are: ", num_bytes, mem_addr);
    for (int i = 0; i < num_bytes; i++) {
        printf("%02" PRIx8 " ", bytes[i]);
    }
    printf("\n");
}
