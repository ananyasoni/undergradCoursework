/*
 * CSE 351 Lab 1a (Pointers in C)
 *
 * Name: Ananya Shreya Soni
 * NetID: ananya99
 *
 * WARNING: Do not include the <stdio.h> header; it confuses the dlc compiler.
 * You can still use printf for debugging without including <stdio.h>. The
 * included file, "common.c" contains a function declaration that should
 * prevent a compiler warning. In general, it's not good practice to ignore
 * compiler warnings, but in this case it's OK.
 */

#ifndef COMMON_H
#include "common.h"
#endif

/*
 * Instructions to Students:
 *
 * STEP 1: Read the following instructions carefully.
 */

#if 0
You will provide your solution to this homework by editing the collection of
functions in this source file.

INTEGER CODING RULES:

  Replace the "return" statement in each function with one or more lines of C
  code that implements the function. Your code must conform to the following
  style:

  int Funct(arg1, arg2, ...) {
      /* brief description of how your implementation works */
      int var1 = Expr1;
      ...
      int varM = ExprM;

      varJ = ExprJ;
      ...
      varN = ExprN;
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are not allowed to
     use big constants such as 0xFFFFFFFF. However, you are allowed to combine
     constants to produce values outside this range (e.g., 250 + 250 = 500) so
     long as the operator you are using to combine the constants is listed as
     "legal" at the top of the method you are writing.
  2. Function arguments and local variables (no global variables).
  3. Any operators listed as "ALLOWED" in the function header comment of the
     method you are writing.
  4. Casting.

  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to one
  operator per line.

  You are expressly forbidden from:
  1. Using control constructs such as if, do, while, for, switch (unless
     otherwise stated).
  2. Defining or using macros.
  3. Defining additional functions in this file.
  4. Calling functions (unless otherwise stated).
  5. Using operators listed as "DISALLOWED" in the function header comment of
     the method you are writing.

  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has *undefined* behavior when shifting by a negative amount or an amount
     greater than or equal to the number of bits in the value being shifted.
     (i.e., x >> n is undefined when n < 0 or n >= *size of x*)
     (e.g., if x is a 32-bit int, shifts by >= 32 bits are undefined)
     Undefined behvaior means that the result of the operation may change in
     different environments -- this should be avoided.
#endif

/*
 * STEP 2: Modify the following functions according the coding rules.
 */

/*
 * Return the size of an integer in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int int_size() {
  int int_array[10];
  int* int_ptr1;
  int* int_ptr2;
  // Write code to compute size of an integer.
  int_ptr1 = int_array;
  int_ptr2 = int_ptr1 + 1;
  return (char*) int_ptr2 - (char*) int_ptr1;
}

/*
 * Return the size of a double in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int double_size() {
  double doub_array[10];
  double* doub_ptr1;
  double* doub_ptr2;
  // Write code to compute size of a double.
  doub_ptr1 = doub_array;
  doub_ptr2 = doub_ptr1 + 1;
  return (char*) doub_ptr2 - (char*) doub_ptr1;
}

/*
 * Return the size of a pointer in bytes.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
int pointer_size() {
  double* ptr_array[10];
  double** ptr_ptr1;
  double** ptr_ptr2;
  // Write code to compute size of a pointer.
  ptr_ptr1 = ptr_array;
  ptr_ptr2 = ptr_ptr1 + 1;
  return (char*) ptr_ptr2 - (char*) ptr_ptr1;
}

/*
 * Given pointers to two variables, exchange/swap the values of the variables.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Unary integer operators: !
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
void swap_ints(int* ptr1, int* ptr2) {
  // Your code here
  int temp = *ptr1;
  *ptr1 = *ptr2;
  *ptr2 = temp;
}

/*
 * Modify int_array[5] to be the value 351 using only int_array and pointer
 * arithmetic.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: +=, *=, ++, --, ~= etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int change_value() {
  int int_array[10];
  int* int_ptr1 = int_array;
  // Remember not to use constants greater than 255.
  // Remember to use * to dereference. You cannot use '[<index>]' syntax.
  int* int_ptr2 = int_ptr1 + 5;
  *int_ptr2 = 255 + 96;
  return *int_ptr2;
}

/*
 * Return 1 if the addresses stored in ptr1 and ptr2 are within the *same*
 * 64-byte aligned block of memory or return 0 otherwise. Check the spec for
 * examples if you are unsure of what this means.
 *
 * Note that loops and the operators / and % are NOT allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: <<=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int within_same_block(int* ptr1, int* ptr2) {
  // Your code here
  return ((long) ptr1 >> 6) == ((long) ptr2 >> 6);
}

/*
 * Return 1 if ptr points to a byte within the specified int_array or return 0
 * otherwise. ptr does not have to point to the beginning of an element. Check
 * the spec for examples if you are unsure of what this means. size is the
 * size of int_array in number of ints; assume size != 0.
 *
 * Note that loops and the operators / and % are NOT allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, <<, >>, ==, ^
 *   Unary integer operators: !, ~
 *   Shorthand operators based on the above: <<=, *=, ++, --, etc.
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, !=, /, %
 *   Unary integer operators: -
 */
int within_array(int* int_array, int size, int* ptr) {
  // Your code here
  char* start = (char*) int_array;
  char* end = (char*) start + size * 4 - 1;
  char* target = (char*) ptr;
  long offset_from_start = (long) target - (long) start;
  long offset_from_end = (long) end - (long) target;
  // both offset from start and end should be non-negative otherwise
  // ptr is not within int_array
  return !(offset_from_start >> 31) * !(offset_from_end >> 31); // extracts the msb which is 1 if negative and 0 if positive
}

/*
 * In C, the end of a "string" is indicated by the null terminator ('\0').
 * Given a pointer to the start of a "string," return the string length.
 * The null terminator is not counted as part of the string length.
 *
 * Note that loops ARE allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, ==, !=, <, >
 *   Unary integer operators: !
 *   Shorthand operators based on the above: <<=, *=, ++, --, etc.
 *   Control constructs: for, while
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <<, >>, ^, /, %
 *   Unary integer operators: ~, -
 */
int string_length(char* s) {
  // Your code here
  int len = 0;
  char* ptr = s;
  while (*ptr != '\0') {
    ptr++;
    len++;
  }
  return len;
}

/*
 * Change the value pointed to by ptr byte-by-byte so that when returned as an
 * integer the value is 351351.
 *
 * Hints: Recall that an int is 4 bytes and how little endian works for
 * multibyte memory storage. We suggest starting by converting 351351 into
 * binary/hexadecimal.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *   Unary integer operators: !
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <, >, <<, >>, ==, !=, ^, /, %
 *   Unary integer operators: ~, -
 */
 // 0x00055C77
int endian_experiment(int* ptr) {
  char* byte_ptr = (char*) ptr;
  *byte_ptr = 119;
  byte_ptr = byte_ptr +  1;
  *byte_ptr = 92;
  byte_ptr = byte_ptr +  1;
  *byte_ptr = 5;
  byte_ptr = byte_ptr +  1;
  *byte_ptr = 0;
  return *ptr;
}

/*
 * Selection sort is a sorting algorithm that works by partitioning the array
 * into a sorted section and unsorted section. Then it repeatedly selects the
 * minimum element from the unsorted section and moves it to the end of the
 * sorted section.
 *
 * Pseudo-code for an array (arr) and its length (n) might look like:
 *
 *   for i = 0 to n - 1
 *     minIndex = i
 *     for  j = i + 1 to n
 *       if arr[minIndex] > arr[j]
 *         minIndex = j
 *       end if
 *     end for
 *     Swap(arr[i], arr[minIndex])
 *   end for
 *
 * Note that control constructs and calling swap_ints() ARE allowed.
 *
 * ALLOWED:
 *   Pointer operators: *, &
 *   Binary integer operators: -, +, *, ==, !=, <, >
 *   Unary integer operators: !
 *   Shorthand operators based on the above: +=, *=, ++, --, etc.
 *   Control constructs: for, while, if
 *   Function calls: swap_ints()
 *
 * DISALLOWED:
 *   Pointer operators: [] (Array Indexing Operator)
 *   Binary integer operators: &, &&, |, ||, <<, >>, ^, /
 *   Unary integer operators: ~, -
 */
void selection_sort(int arr[], int arr_length) {
  int i, j, min_index;
  for (i = 0; i < arr_length; i++) {
    min_index = i;
    for (j = i + 1; j < arr_length; j++) {
      if (*(arr + min_index) > *(arr + j)) {
        min_index = j;
      }
      swap_ints(arr + min_index, arr + i);
    }
  }
}
